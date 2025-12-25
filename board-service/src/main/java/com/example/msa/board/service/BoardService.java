package com.example.msa.board.service;

import com.example.msa.board.mapper.BoardMapper;
import com.example.msa.common.util.EgovMap;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class BoardService {

    private final BoardMapper boardMapper;

    @Autowired
    public BoardService(BoardMapper boardMapper) {
        this.boardMapper = boardMapper;
    }

    public List<EgovMap> getBoardList(EgovMap param) {
        return boardMapper.getBoardList(param);
    }

    public EgovMap getBoardById(Long boardId, String userId) {
        EgovMap param = new EgovMap();
        param.put("boardId", boardId);
        param.put("userId", userId);
        return boardMapper.getBoardById(param);
    }
}