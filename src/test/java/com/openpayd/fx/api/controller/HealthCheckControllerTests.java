package com.openpayd.fx.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckControllerTests {

    @Autowired
    private MockMvc api;

    @Test
    public void healthCheckReturnsOk() throws Exception {
        // Act && Assert
        api
            .perform(MockMvcRequestBuilders.get("/api/v1/healthcheck"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("{\"IsExternalFXAPIOK\":true,\"IsInMemoryDBOK\":true}")));
    }

}
