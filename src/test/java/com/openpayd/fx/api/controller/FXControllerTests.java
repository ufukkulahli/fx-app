package com.openpayd.fx.api.controller;

import com.openpayd.fx.core.service.UniqueIdentifier;
import com.openpayd.fx.data.entity.CurrencyConversion;
import com.openpayd.fx.data.repository.CurrencyConversionRepository;
import com.openpayd.fx.external.config.FXAPIClientConfig;
import com.openpayd.fx.external.model.ExternalRateResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class FXControllerTests {

    @Autowired
    private MockMvc api;

    @MockBean
    private UniqueIdentifier uniqueIdentifier;

    @MockBean
    private CurrencyConversionRepository currencyConversionRepository;

    @MockBean
    private FXAPIClientConfig config;

    @MockBean
    private RestTemplate restTemplate;


    @Test
    public void rateEndpointReturnsOk() throws Exception {
        // Arrange
        var expectedResponse = "{\"from\":\"USD\",\"to\":\"USD\",\"rate\":1.0,\"success\":true}";

        when(config.fullUrl()).thenReturn("https://api.freecurrencyapi.com/v1/latest?apikey=1234&base_currency=USD&currencies=EUR");

        var response = new ExternalRateResponse();
        response.data.put("USD", 1.0);
        response.success = true;

        when(restTemplate.getForEntity(anyString(), eq(ExternalRateResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act & Assert
        api
            .perform(MockMvcRequestBuilders.get("/api/v1/fx/rate"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expectedResponse)));
    }

    @Test
    public void convertEndpointReturnsOk() throws Exception {
        // Arrange
        given(uniqueIdentifier.getRandom()).willReturn(UUID.fromString("47d192a9-6db8-4a80-b7dc-6423fd83b893"));

        var expectedResponse = "{\"from\":\"USD\",\"to\":\"USD\",\"rate\":1.0,\"initialAmount\":10.0,\"convertedAmount\":10.0,\"transactionID\":\"47d192a9-6db8-4a80-b7dc-6423fd83b893\",\"date\":\"2024-07-25\",\"success\":true}";

        var response = new ExternalRateResponse();
        response.data.put("USD", 1.0);
        response.success = true;

        when(restTemplate.getForEntity(anyString(), eq(ExternalRateResponse.class)))
                .thenReturn(ResponseEntity.ok(response));

        // Act & Assert
        api
            .perform(MockMvcRequestBuilders.get("/api/v1/fx/convert?from=USD&to=USD&amount=10"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expectedResponse)));
    }

    @Test
    public void historyEndpointReturnsOk() throws Exception {
        // Arrange
        given(uniqueIdentifier.getRandom()).willReturn(UUID.fromString("e2eef383-38f1-4638-96bd-4b5aa7c6b3de"));
        given(uniqueIdentifier.getRandom()).willReturn(UUID.fromString("47d192a9-6db8-4a80-b7dc-6423fd83b893"));

        List<CurrencyConversion> conversions = new ArrayList<>();
        var currencyConversion = new CurrencyConversion();
        currencyConversion.fromCurrency = "USD";
        currencyConversion.toCurrency = "USD";
        currencyConversion.rate = 1.0;
        currencyConversion.initialAmount = 1.0;
        currencyConversion.convertedAmount = 1.0;
        currencyConversion.uuid = UUID.fromString("e2eef383-38f1-4638-96bd-4b5aa7c6b3de");
        currencyConversion.date = LocalDate.of(2024, 7,25);
        currencyConversion.success = true;
        conversions.add(currencyConversion);

        var page = new PageImpl<>(conversions);

        given(currencyConversionRepository.findByUuidOrDate(UUID.fromString("e2eef383-38f1-4638-96bd-4b5aa7c6b3de"), null, PageRequest.of(0, 10)))
                .willReturn(page);

        var expectedResponse =
                "[{\"from\":\"USD\",\"to\":\"USD\",\"rate\":1.0,\"initialAmount\":1.0,\"convertedAmount\":1.0,\"transactionID\":\"e2eef383-38f1-4638-96bd-4b5aa7c6b3de\",\"date\":\"2024-07-25\",\"success\":true}]";

        // Act & Assert
        api
            .perform(MockMvcRequestBuilders.get("/api/v1/fx/history?transactionID=e2eef383-38f1-4638-96bd-4b5aa7c6b3de"))
            .andExpect(status().isOk())
            .andExpect(content().string(equalTo(expectedResponse)));
    }

}
