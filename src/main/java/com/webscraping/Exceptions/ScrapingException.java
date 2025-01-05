package com.webscraping.Exceptions;

public class ScrapingException extends RuntimeException {
    public ScrapingException(String message, Throwable cause) {
        super(message, cause);
    }
}
