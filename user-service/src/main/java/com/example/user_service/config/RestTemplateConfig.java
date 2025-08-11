package com.example.user_service.config;

import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    private static final int connectionTimeout = 10;
    private static final int readTimeout = 30;

    @Bean(name = "restTemplateWithUserToken")
    public RestTemplate restTemplateWithUserToken(RestTemplateBuilder builder) {
        return builder
                .additionalInterceptors(
                        (request, body, execution) -> {
                            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                            if (authentication != null && authentication.getCredentials() instanceof Jwt jwt) {
                                request.getHeaders().set(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.getTokenValue());
                            }
                            return execution.execute(request, body);
                        })
                .requestFactory(this::createHttpRequestFactory)
                .build();
    }

    private HttpComponentsClientHttpRequestFactory createHttpRequestFactory() {
        CloseableHttpClient httpClient = HttpClients.custom()
                .build();
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory(httpClient);
        factory.setConnectTimeout(connectionTimeout * 1000);
        factory.setReadTimeout(readTimeout * 1000);
        return factory;
    }
}
