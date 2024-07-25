package com.openpayd.fx.api.controller;

import com.openpayd.fx.api.model.response.HealthCheckResponse;
import com.openpayd.fx.data.repository.HealthCheckRepository;
import com.openpayd.fx.external.service.ExternalFXAPIRestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    private static final Logger logger = LoggerFactory.getLogger(HealthCheckController.class);

    private final HealthCheckRepository healthCheckRepository;
    private final ExternalFXAPIRestClient externalFXAPIRestClient;

    public HealthCheckController(HealthCheckRepository healthCheckRepository,
                                 ExternalFXAPIRestClient externalFXAPIRestClient) {
        this.healthCheckRepository = healthCheckRepository;
        this.externalFXAPIRestClient = externalFXAPIRestClient;
    }

    @GetMapping("/api/v1/healthcheck")
    public HealthCheckResponse Check() {
        var response = new HealthCheckResponse();
        response.IsExternalFXAPIOK = externalFXAPIRestClient.getRate("USD", "USD").success;
        response.IsInMemoryDBOK = isInMemoryDBHealthy();
        logger.info(response.toString());
        return response;
    }

    private boolean isInMemoryDBHealthy() {
        try {
            Integer result = healthCheckRepository.checkDBHealth();
            return result != null && result == 1;
        } catch (Exception e) {
            logger.error("Error while checking DB health", e);
            return false;
        }
    }

}
