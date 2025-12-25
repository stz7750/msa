package com.example.msa.auth.client;

import com.example.msa.common.response.ApiResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

public interface BoardClient {

    @GetMapping("/board/list")
    ApiResponse getBoardList(@RequestParam("userId") String userId);

    @GetMapping("/board/{boardId}")
    ApiResponse getBoardById(@PathVariable("boardId") Long boardId, @RequestParam("userId") String userId);
}