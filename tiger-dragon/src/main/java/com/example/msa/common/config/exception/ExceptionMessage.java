package com.example.msa.common.config.exception;

import lombok.Getter;

/**
 * packageName    : com.example.msa.common.exception
 * fileName       : ExceptionMessage
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : API 응답 메시지 및 코드 정의
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 12. 20.          'stz'       최초 생성
 */
@Getter
public enum ExceptionMessage {
    
    // 2xx Success
    SUCCESS(200, "SUCCESS"),
    SELECT_DATA(200, "SELECT_DATA"),
    INSERT_SUCCESS(201, "INSERT_SUCCESS"),
    UPDATE_SUCCESS(200, "UPDATE_SUCCESS"),
    DELETE_SUCCESS(200, "DELETE_SUCCESS"),
    
    // 2xx No Content
    EMPTY_DATA(203, "EMPTY_DATA"),
    NO_CONTENT(204, "NO_CONTENT"),
    
    // 4xx Client Error
    BAD_REQUEST(400, "BAD_REQUEST"),
    INVALID_PARAMETER(400, "INVALID_PARAMETER"),
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    INVALID_TOKEN(401, "INVALID_TOKEN"),
    TOKEN_EXPIRED(401, "TOKEN_EXPIRED"),
    FORBIDDEN(403, "FORBIDDEN"),
    NOT_FOUND(404, "NOT_FOUND"),
    USER_NOT_FOUND(404, "USER_NOT_FOUND"),
    DATA_NOT_FOUND(404, "DATA_NOT_FOUND"),
    METHOD_NOT_ALLOWED(405, "METHOD_NOT_ALLOWED"),
    CONFLICT(409, "CONFLICT"),
    DUPLICATE_DATA(409, "DUPLICATE_DATA"),
    
    // 5xx Server Error
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR"),
    DATABASE_ERROR(500, "DATABASE_ERROR"),
    EXTERNAL_API_ERROR(502, "EXTERNAL_API_ERROR"),
    SERVICE_UNAVAILABLE(503, "SERVICE_UNAVAILABLE");
    
    private final int code;
    private final String message;
    
    ExceptionMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
