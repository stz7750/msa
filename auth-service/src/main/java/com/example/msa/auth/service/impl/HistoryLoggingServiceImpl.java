package com.example.msa.auth.service.impl;

import com.example.msa.auth.mapper.HistMapper;
import com.example.msa.common.service.HistoryLoggingService;
import com.example.msa.common.util.EgovMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * packageName    : com.example.msa.auth.service.impl
 * fileName       : HistoryLoggingServiceImpl
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    : HistMapper를 사용한 이력 로깅 구현체
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 21.          'stz'       최초 생성
 */
@Service
public class HistoryLoggingServiceImpl implements HistoryLoggingService {

    private final HistMapper histMapper;

    @Autowired
    public HistoryLoggingServiceImpl(HistMapper histMapper) {
        this.histMapper = histMapper;
    }

    @Override
    public String logging(EgovMap param) {
        histMapper.logging(param);
        return param.getString("histId");
    }
}