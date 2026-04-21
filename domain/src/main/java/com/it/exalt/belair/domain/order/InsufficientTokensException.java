package com.it.exalt.belair.domain.order;

public class InsufficientTokensException extends RuntimeException {

    public InsufficientTokensException(String message) {
        super(message);
    }
}
