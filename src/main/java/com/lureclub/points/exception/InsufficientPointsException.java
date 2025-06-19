package com.lureclub.points.exception;

/**
 * 积分不足异常
 *
 * @author system
 * @date 2025-06-19
 */
public class InsufficientPointsException extends RuntimeException {

    public InsufficientPointsException(String message) {
        super(message);
    }

    public InsufficientPointsException(String message, Throwable cause) {
        super(message, cause);
    }

}