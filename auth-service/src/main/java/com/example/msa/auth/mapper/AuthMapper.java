package com.example.msa.auth.mapper;

import com.example.msa.common.util.EgovMap;
import org.apache.ibatis.annotations.Mapper;

/**
 * packageName    : msa-com.example.msa.auth.mapper
 * fileName       : userMapper
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 21.          'stz'       최초 생성
 */
@Mapper
public interface AuthMapper {
    EgovMap getUser(EgovMap param);
    int join(EgovMap param);
}
