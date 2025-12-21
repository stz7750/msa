package com.example.msa.auth.mapper;

import com.example.msa.common.util.EgovMap;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : com.example.msa.auth.mapper
 * fileName       : HistMapper
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    : API 실행 이력 저장 매퍼
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 21.          'stz'       최초 생성
 */
@Mapper
public interface HistMapper {
    void logging(EgovMap param);
}