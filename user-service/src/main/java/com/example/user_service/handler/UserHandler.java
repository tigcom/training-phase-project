package com.example.user_service.handler;

import com.example.user_service.Interface.DemoRestClientService;
import com.example.user_service.Interface.Impl.kafkaservice;
import com.example.user_service.config.JwtConfig;
import com.example.user_service.entity.User;
import com.example.user_service.Interface.UserService;
import com.fasterxml.jackson.databind.JsonNode;
import io.github.resilience4j.bulkhead.annotation.Bulkhead;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.timelimiter.TimeLimiter;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import io.github.resilience4j.timelimiter.TimeLimiterRegistry;
import io.vavr.CheckedRunnable;
import io.vavr.control.Try;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.productDTO;
import org.hibernate.envers.AuditReader;
import org.hibernate.envers.AuditReaderFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import java.time.Duration;
import java.util.List;
import io.github.resilience4j.retry.Retry;
import io.github.resilience4j.retry.RetryConfig;
import io.github.resilience4j.retry.RetryRegistry;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import static org.springframework.web.servlet.function.ServerResponse.status;

@Component
@RequiredArgsConstructor
public class UserHandler {
    private final AuthenticationManager am;
    private final UserService userService;
    private final DemoRestClientService demoRestClientService;
    private final JwtConfig jwt;
    private final kafkaservice f;
    private final EntityManager em;

    private static final CircuitBreakerRegistry registry;
    private static final CircuitBreaker circuitBreaker;

    static {
        CircuitBreakerConfig config = CircuitBreakerConfig.custom()
                .failureRateThreshold(50)
                .slidingWindowSize(4)
                .waitDurationInOpenState(Duration.ofMillis(10000))
                .recordExceptions(Exception.class)
                .build();
        registry = CircuitBreakerRegistry.of(config);
        circuitBreaker = registry.circuitBreaker("restProducts");
    }

    public ResponseEntity<List<User>> getAllUsers() {
        TimeLimiterConfig config = TimeLimiterConfig.custom()
                .timeoutDuration(Duration.ofMillis(2000))
                .build();
        TimeLimiterRegistry registry = TimeLimiterRegistry.of(config);
        TimeLimiter timeLimiter = registry.timeLimiter("getAllUsers");
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(10);
        Supplier<CompletionStage<List<User>>> supplier = () -> CompletableFuture.supplyAsync(() -> userService.getAllUsers());
        Supplier<CompletionStage<List<User>>> decoratedSupplier = timeLimiter.decorateCompletionStage(
                scheduler, supplier
        );
        try {
            List<User> users = decoratedSupplier.get().toCompletableFuture().get();
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.REQUEST_TIMEOUT);
        }
    }

    @RateLimiter(name = "getUserById", fallbackMethod = "fallbackMethod")
    public ResponseEntity<User> getUserById(String id) {
            User user = userService.getUserByUserName(id);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @Bulkhead(name = "createUser", fallbackMethod = "fallbackMethod")
    public ResponseEntity<User> createUser(User user) {
        try {
            Thread.sleep(20000);
        if(userService.getUserByUserName(user.getUsername())  == null) {
            userService.saveUser(user);
        }else {
            throw new DataIntegrityViolationException(user.getUsername());
        }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    public ResponseEntity<User> updateUser(String id, User user) {
        User existingUser = userService.getUserByUserName(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.saveUser(user);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    public ResponseEntity<Void> deleteUser(String id) {
        User existingUser = userService.getUserByUserName(id);
        if (existingUser == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        userService.removeUser(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    public ResponseEntity<?> login(JsonNode user) {
        String username = user.get("username").asText();
        String password = user.get("password").asText();

        try {
            Authentication auth = am.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            if (auth.isAuthenticated()) {
                UserDetails userDetails = (UserDetails) auth.getPrincipal();
                String token = jwt.createToken(userDetails, 20 * 60);
                return ResponseEntity.ok(token);
            }
        } catch (BadCredentialsException ex) {
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public ResponseEntity<?> restProducts() {
        Supplier<List<productDTO>> supplier =
                CircuitBreaker.decorateSupplier(circuitBreaker, demoRestClientService::products);
        Try<List<productDTO>> result = Try.ofSupplier(supplier);

        if (result.isFailure()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(circuitBreaker.getState());
        }
        return ResponseEntity.ok(result.get());
    }

    public ResponseEntity<?> createProduct(productDTO product) {
        f.send(product);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    public ResponseEntity<Map<String, User>> getHistory(String id) {
        AuditReader reader = AuditReaderFactory.get(em);
        List<Number> revisions = reader.getRevisions(User.class, id);
        Map<String, User> map = revisions.stream().collect(Collectors.toMap(
                rev -> "Revision" + rev,
                rev -> reader.find(User.class, id, rev)
        ));
        return ResponseEntity.ok(map);
    }
    public ResponseEntity<?> fallbackMethod(String configName, Throwable t) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Transaction " + configName + " failed. Error: " + t.getMessage());
    }

    public ResponseEntity<OrderDTO> shopping(productDTO product) {
        return ResponseEntity.ok(userService.shopping(product));
    }
}
