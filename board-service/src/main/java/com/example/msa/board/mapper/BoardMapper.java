package com.example.msa.board.mapper;

import com.example.msa.common.util.EgovMap;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BoardMapper {
    List<EgovMap> getBoardList(EgovMap param);
    EgovMap getBoardById(EgovMap param);
}