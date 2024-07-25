package com.openpayd.fx.external.service;

import com.openpayd.fx.external.config.FXAPIClientConfig;
import com.openpayd.fx.external.model.ExternalRateResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ExternalFXAPIRestClient {

    private static final Logger logger = LoggerFactory.getLogger(ExternalFXAPIRestClient.class);

    private final FXAPIClientConfig config;
    private final RestTemplate client;

    public ExternalFXAPIRestClient(FXAPIClientConfig config, RestTemplate client) {
        this.config = config;
        this.client = client;
    }

    public ExternalRateResponse getRate(String from, String to) {
        config.setFrom(from);
        config.setTo(to);

        try {
            return client
                    .getForEntity(config.fullUrl(), ExternalRateResponse.class)
                    .getBody();
        }
        catch (Exception e) {
            logger.error("Error getting external rate, from {} to {}, message {}, exception {}", from, to, e.getMessage(), e);
            return ExternalRateResponse.FailResponse();
        }
    }
}
