package com.example.msa.common.config.exception;

import com.example.msa.common.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

/**
 * packageName    : com.example.msa.common.exception
 * fileName       : GlobalExceptionHandler
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : 전역 예외 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 12. 20.          'stz'       최초 생성
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(ApiException.class)
    public ResponseEntity<ApiResponse> handleApiException(ApiException e) {
        
        ApiResponse response = ApiResponse.builder()
                .code(e.getCode())
                .msg(e.getMessage())
                .data(null)
                .build();
        
        return ResponseEntity
                .status(HttpStatus.valueOf(e.getCode()))
                .body(response);
    }
    
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse> handleException(Exception e) {
        
        ApiResponse response = ApiResponse.builder()
                .code(ExceptionMessage.INTERNAL_SERVER_ERROR.getCode())
                .msg(ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage())
                .data(null)
                .build();
        
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
    
    @ExceptionHandler(SQLException.class)
    public ResponseEntity<ApiResponse> handleSQLException(SQLException e) {
        ApiResponse response = ApiResponse.builder()
                .code(ExceptionMessage.INTERNAL_SERVER_ERROR.getCode())
                .msg(ExceptionMessage.INTERNAL_SERVER_ERROR.getMessage())
                .data(null)
                .build();
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(response);
    }
}
