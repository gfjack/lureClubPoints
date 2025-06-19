package com.lureclub.points.exception;

/**
 * 未授权异常
 *
 * @author system
 * @date 2025-06-19
 */
public class UnauthorizedException extends RuntimeException {

    public UnauthorizedException(String message) {
        super(message);
    }

    public UnauthorizedException(String message, Throwable cause) {
        super(message, cause);
    }

}