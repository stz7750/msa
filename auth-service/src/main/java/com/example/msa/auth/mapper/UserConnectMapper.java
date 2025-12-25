package com.example.msa.auth.mapper;

import com.example.msa.common.util.EgovMap;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserConnectMapper {
    EgovMap getUserConnect(String userId);
}