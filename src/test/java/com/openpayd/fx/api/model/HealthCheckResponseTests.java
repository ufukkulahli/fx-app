package com.openpayd.fx.api.model;

import com.openpayd.fx.api.model.response.HealthCheckResponse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HealthCheckResponseTests {

    @Test
    public void healthCheckResponseToStringTest() {
        // Arrange
        var response = new HealthCheckResponse();
        response.IsExternalFXAPIOK = true;
        response.IsInMemoryDBOK = false;

        var expectedString = "HealthCheckResponse{" +
                "IsExternalFXAPIOK=true" +
                ", IsInMemoryDBOK=false" +
                '}';

        // Act && Assert
        assertEquals(expectedString, response.toString());
    }
}
