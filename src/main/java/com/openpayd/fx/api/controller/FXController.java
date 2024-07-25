package com.openpayd.fx.api.controller;

import com.openpayd.fx.api.model.response.RateResponse;
import com.openpayd.fx.api.validation.RequestParameterValidationException;
import com.openpayd.fx.api.validation.ErrorResponse;
import com.openpayd.fx.core.service.FXService;
import com.openpayd.fx.api.model.response.ConversionResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping("/api/v1/fx")
public class FXController {

    private final FXService fxService;
    private static final Logger logger = LoggerFactory.getLogger(FXController.class);


    public FXController(FXService fxService) {
        this.fxService = fxService;
    }

    @GetMapping("/rate")
    public RateResponse Rate(
            @RequestParam(value = "from", defaultValue = "USD") String from,
            @RequestParam(value = "to", defaultValue = "USD") String to) {

        return fxService.Rate(from, to);
    }

    @GetMapping("/convert")
    public ConversionResponse Convert(
            @RequestParam(value = "from", defaultValue = "USD") String from,
            @RequestParam(value = "to", defaultValue = "USD") String to,
            @RequestParam(value = "amount") double amount) {

        return fxService.Convert(from, to, amount);
    }

    // TODO: MOVE PARAMETERS TO A CLASS
    @GetMapping("/history")
    public ResponseEntity<List<ConversionResponse>> History(@RequestParam(value = "transactionID", required = false) UUID transactionID,
                                                            @RequestParam(value = "transactionDate", required = false) LocalDate transactionDate,
                                                            @RequestParam(defaultValue = "0") int page,
                                                            @RequestParam(defaultValue = "10") int size) throws RequestParameterValidationException {
        // TODO: UTILISE CUSTOM VALIDATION
        if (transactionID == null && transactionDate == null) {
            logger.warn("Missing required parameter 'transactionID' or 'transactionDate'");
            throw new RequestParameterValidationException("transactionID (like 6ca586ef-0a96-442c-9dec-35de89dfa8ba) or transactionDate (like 2024-07-25) is required");
        }

        logger.info("History with transactionID {} and transactionDate {}", transactionID, transactionDate);

        return new ResponseEntity<>(fxService.History(transactionID, transactionDate, page, size), HttpStatus.OK);
    }

    @ExceptionHandler(RequestParameterValidationException.class)
    public ResponseEntity<ErrorResponse> handleCustomException(RequestParameterValidationException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

}
