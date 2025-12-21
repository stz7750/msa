package com.example.msa.common.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * packageName    : com.example.msa.common.response
 * fileName       : ApiResponse
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : 공통 API 응답 DTO
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 09. 20.          'stz'       최초 생성
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
    private String msg;
    private int code;
    private Object data;
}
