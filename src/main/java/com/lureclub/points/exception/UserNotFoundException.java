package com.lureclub.points.exception;

/**
 * 用户未找到异常
 *
 * @author system
 * @date 2025-06-19
 */
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(message);
    }

    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

}