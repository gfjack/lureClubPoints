package com.lureclub.points.exception;

import com.lureclub.points.entity.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * 全局异常处理器
 *
 * @author system
 * @date 2025-06-19
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 业务异常处理
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Object>> handleBusinessException(BusinessException e) {
        ApiResponse<Object> response = ApiResponse.error(e.getCode(), e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 参数校验异常处理 - @Valid注解
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = ApiResponse.error(400, "参数校验失败: " + message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 参数绑定异常处理
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<ApiResponse<Object>> handleBindException(BindException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining(", "));

        ApiResponse<Object> response = ApiResponse.error(400, "参数绑定失败: " + message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 非法参数异常处理
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponse<Object>> handleIllegalArgumentException(IllegalArgumentException e) {
        ApiResponse<Object> response = ApiResponse.error(400, "参数错误: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 用户未找到异常处理
     */
    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ApiResponse<Object>> handleUserNotFoundException(UserNotFoundException e) {
        ApiResponse<Object> response = ApiResponse.error(404, e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }

    /**
     * 积分不足异常处理
     */
    @ExceptionHandler(InsufficientPointsException.class)
    public ResponseEntity<ApiResponse<Object>> handleInsufficientPointsException(InsufficientPointsException e) {
        ApiResponse<Object> response = ApiResponse.error(400, e.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    /**
     * 未授权异常处理
     */
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ApiResponse<Object>> handleUnauthorizedException(UnauthorizedException e) {
        ApiResponse<Object> response = ApiResponse.error(401, e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    /**
     * 通用异常处理
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception e) {
        e.printStackTrace(); // 打印异常信息用于调试
        ApiResponse<Object> response = ApiResponse.error(500, "系统内部错误: " + e.getMessage());
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }

}