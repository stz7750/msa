package com.example.msa.auth.controller;

import com.example.msa.auth.service.AuthService;
import com.example.msa.common.Token;
import com.example.msa.common.config.decorator.ApiHistory;
import com.example.msa.common.response.ApiResponse;
import com.example.msa.common.util.EgovMap;
import com.example.msa.common.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    
    private final AuthService authService;

    @Autowired
    public AuthController(JwtUtil jwtUtil, AuthService authService) {
        this.jwtUtil = jwtUtil;
        this.authService = authService;
    }

    @ApiHistory
    @PostMapping("/login")
    public EgovMap login(@RequestBody Token token, HttpServletRequest request) {
        EgovMap result = new EgovMap();
        Token accessToken = authService.login(token, request);
        result.put("info", accessToken);
        return result;
    }
    
    @ApiHistory
    @PostMapping("/join")
    public ApiResponse join(@RequestBody EgovMap param, HttpServletRequest request){
        int result = authService.join(param, request);
        if(result > 0) return ApiResponse.builder().code(200).msg("JOIN_SUCCESS").data("JOIN_SUCCESS").build();
        else throw new SecurityException("GET OUT");
    }

    @GetMapping("/health")
    public String health() {
        return "Wecomle";
    }
    
    
}
