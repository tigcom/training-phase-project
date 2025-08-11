package com.example.user_service;

import com.github.tomakehurst.wiremock.WireMockServer;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "dubbo.registry.address=N/A",
        "dubbo.application.qos-enable=false",
        "product.service.api.url=http://localhost:4115/api/products"
})
public class UserRestControllerTest {
    @Autowired
    private MockMvc mockMvc;
    private WireMockServer wireMockServer;
    @BeforeEach
    void setup() {
        wireMockServer = new WireMockServer(4115);
        wireMockServer.start();
        com.github.tomakehurst.wiremock.client.WireMock.
                configureFor("localhost", 4115);
    }
    @AfterEach
    void teardown() {
        wireMockServer.stop();
    }
    @Test
    void testRestProducts_success() throws Exception {
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(urlEqualTo("/api/products"))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("""
                    {
                      "status": 200,
                      "message": "Find the product list successfully",
                      "data": [
                        { "id": 1, "name": "iPhone 15", "description": "Điện thoại Apple mới nhất", "price": 24990000.00, "stock": 50 },
                        { "id": 2, "name": "MacBook Air M2", "description": "Laptop Apple M2 13 inch", "price": 29990000.00, "stock": 20 }
                      ]
                    }
                """)));
        mockMvc.perform(get("/api/users/restProducts"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("iPhone 15"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("MacBook Air M2"));
    }
    @Test
    void testRestProducts_failure() throws Exception {
        stubFor(com.github.tomakehurst.wiremock.client.WireMock.get(
                        urlEqualTo("/api/products"))
                .willReturn(aResponse()
                        .withStatus(500)
                        .withHeader("Content-Type", "application/json")
                        .withBody("{ \"message\": \"Error loading product data\" }")));

        mockMvc.perform(get("/api/users/restProducts"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(containsString("CLOSED")));
    }
}
