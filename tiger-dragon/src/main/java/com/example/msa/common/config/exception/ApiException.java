package com.example.msa.common.config.exception;

import lombok.Getter;

/**
 * packageName    : com.example.msa.common.exception
 * fileName       : ApiException
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : API 예외 처리
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 12. 20.          'stz'       최초 생성
 */
@Getter
public class ApiException extends RuntimeException {
    
    private final ExceptionMessage exceptionMessage;
    private final int code;
    private final String message;
    
    public ApiException(ExceptionMessage exceptionMessage) {
        super(exceptionMessage.getMessage());
        this.exceptionMessage = exceptionMessage;
        this.code = exceptionMessage.getCode();
        this.message = exceptionMessage.getMessage();
    }
    
    public ApiException(ExceptionMessage exceptionMessage, String customMessage) {
        super(customMessage);
        this.exceptionMessage = exceptionMessage;
        this.code = exceptionMessage.getCode();
        this.message = customMessage;
    }
}
