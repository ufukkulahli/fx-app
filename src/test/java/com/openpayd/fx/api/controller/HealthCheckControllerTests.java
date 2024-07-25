package com.openpayd.fx.api.controller;

import com.openpayd.fx.external.model.ExternalRateResponse;
import com.openpayd.fx.external.service.ExternalFXAPIRestClient;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@SpringBootTest
@AutoConfigureMockMvc
public class HealthCheckControllerTests {

    @Autowired
    private MockMvc api;

    @MockBean
    private ExternalFXAPIRestClient externalFXAPIRestClient;

    @Test
    public void healthCheckReturnsOk() throws Exception {
        // Arrange
        var response = new ExternalRateResponse();
        response.success = true;

        when(externalFXAPIRestClient.getRate("USD", "USD")).thenReturn(response);

        // Act && Assert
        api
            .perform(MockMvcRequestBuilders.get("/api/v1/healthcheck"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo("{\"IsExternalFXAPIOK\":true,\"IsInMemoryDBOK\":true}")));
    }

}
