package com.github.yingzhuo.carnival.restful.security.exception;

/**
 * @author 应卓
 */
public class RestfulSecurityException extends RuntimeException {

    private static final long serialVersionUID = -3491211983113582542L;

    public RestfulSecurityException() {
    }

    public RestfulSecurityException(String message) {
        super(message);
    }

    public RestfulSecurityException(String message, Throwable cause) {
        super(message, cause);
    }

    public RestfulSecurityException(Throwable cause) {
        super(cause);
    }

}
