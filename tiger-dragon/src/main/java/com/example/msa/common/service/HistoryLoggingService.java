package com.example.msa.common.service;

import com.example.msa.common.util.EgovMap;

/**
 * packageName    : com.example.msa.common.service
 * fileName       : HistoryLoggingService
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    : API 실행 이력 로깅 인터페이스
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 21.          'stz'       최초 생성
 */
public interface HistoryLoggingService {
    /**
     * API 실행 이력 저장
     * @param param 이력 정보
     * @return histId
     */
    String logging(EgovMap param);
}