package com.noser.vending.error;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * Used for returning error details from endpoints.
 */
@Data
@AllArgsConstructor
public class ErrorDetails {
    /**
     * The message.
     */
    private String message;

    /**
     * HTTP status code.
     */
    private HttpStatus status;
}
