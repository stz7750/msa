package com.example.msa.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * packageName    : msa-com.example.msa.common
 * fileName       : Token
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 12. 2.          'stz'       최초 생성
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String userId;
    private String roleCd;
    private String userNm;
    private String ip;
    private String accessToken;
    private String joinDt;
    private String password;
    private String email;
}
