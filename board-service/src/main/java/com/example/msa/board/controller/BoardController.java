package com.example.msa.board.controller;

import com.example.msa.board.service.BoardService;
import com.example.msa.common.response.ApiResponse;
import com.example.msa.common.util.EgovMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    private static final Logger log = LoggerFactory.getLogger(BoardController.class);

    private final BoardService boardService;

    @Autowired
    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/list")
    public ApiResponse getBoardList(@RequestParam String userId) {
        log.info("Received getBoardList request for userId: {}", userId);
        EgovMap param = new EgovMap();
        param.put("userId", userId);
        try {
            List<EgovMap> result = boardService.getBoardList(param);
            log.info("Successfully retrieved {} boards for userId: {}", result.size(), userId);
            return ApiResponse.builder()
                    .code(200)
                    .msg("SUCCESS")
                    .data(result)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while fetching board list for userId: {}", userId, e);
            return ApiResponse.builder()
                    .code(500)
                    .msg("ERROR: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/{boardId}")
    public ApiResponse getBoardById(@PathVariable Long boardId, @RequestParam String userId) {
        log.info("Received getBoardById request for boardId: {}, userId: {}", boardId, userId);
        try {
            EgovMap result = boardService.getBoardById(boardId, userId);
            log.info("Successfully retrieved board: {}", boardId);
            return ApiResponse.builder()
                    .code(200)
                    .msg("SUCCESS")
                    .data(result)
                    .build();
        } catch (Exception e) {
            log.error("Error occurred while fetching board: {}", boardId, e);
            return ApiResponse.builder()
                    .code(500)
                    .msg("ERROR: " + e.getMessage())
                    .data(null)
                    .build();
        }
    }

    @GetMapping("/health")
    public String health() {
        return "Board Service is running";
    }
}