package com.example.user_service.restcontroller;

import com.example.user_service.handler.UserHandler;
import com.example.user_service.entity.User;
import com.fasterxml.jackson.databind.JsonNode;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.common_lirbary.dto.OrderDTO;
import org.example.common_lirbary.dto.productDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {
    private final UserHandler userHandler;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return userHandler.getAllUsers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable("id") String id) {
        return userHandler.getUserById(id);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        return userHandler.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@Valid @PathVariable("id") String id, @RequestBody User user) {
        return userHandler.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") String id) {
        return userHandler.deleteUser(id);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JsonNode user) {
        return userHandler.login(user);
    }

    @GetMapping("/restProducts")
    public ResponseEntity<?> restProducts() {
        return userHandler.restProducts();
    }

    @PostMapping("/createProduct")
    public ResponseEntity<?> createProduct(@RequestBody productDTO product) {
        return userHandler.createProduct(product);
    }

    @GetMapping("/getHistory/{id}")
    public ResponseEntity<Map<String, User>> getHistory(@PathVariable("id") String id) {
        return userHandler.getHistory(id);
    }
    @PostMapping("/shopping")
    public ResponseEntity<OrderDTO> shopping(@RequestBody productDTO product) {
        return userHandler.shopping(product);
    }
}
