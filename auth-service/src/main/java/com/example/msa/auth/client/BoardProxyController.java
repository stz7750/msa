package com.example.msa.auth.client;

import com.example.msa.auth.mapper.UserConnectMapper;
import com.example.msa.common.response.ApiResponse;
import com.example.msa.common.util.EgovMap;
import com.example.msa.common.util.feign.FeignUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/proxy/board")
@RequiredArgsConstructor
public class BoardProxyController {

    private final UserConnectMapper userConnectMapper;
    private final FeignUtil feignClientFactory;

    @GetMapping("/list")
    public ApiResponse getBoardList(@RequestParam String userId) {
        EgovMap userConnect = userConnectMapper.getUserConnect(userId);
        String boardServiceUrl = feignClientFactory.getServiceUrl(userConnect, "url2", userId);
        BoardClient boardClient = feignClientFactory.createClient(BoardClient.class, boardServiceUrl);
        return boardClient.getBoardList(userId);
    }

    @GetMapping("/{boardId}")
    public ApiResponse getBoardById(@RequestParam String userId, @PathVariable Long boardId) {
        EgovMap userConnect = userConnectMapper.getUserConnect(userId);
        String boardServiceUrl = feignClientFactory.getServiceUrl(userConnect, "url2", userId);
        BoardClient boardClient = feignClientFactory.createClient(BoardClient.class, boardServiceUrl);
        return boardClient.getBoardById(boardId, userId);
    }
}