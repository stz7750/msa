package com.example.msa.auth.service;

import com.example.msa.auth.mapper.AuthMapper;
import com.example.msa.common.Token;
import com.example.msa.common.config.decorator.ApiHistory;
import com.example.msa.common.util.CryptUtil;
import com.example.msa.common.util.EgovMap;
import com.example.msa.common.util.JwtUtil;
import com.example.msa.common.util.RequestUtil;
import com.netflix.discovery.converters.Auto;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

/**
 * packageName    : msa-com.example.msa.auth.service
 * fileName       : authService
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 21.          'stz'       최초 생성
 */
@Slf4j
@Service
public class AuthService {
    private final JwtUtil jwtUtil;
    private final AuthMapper authMapper;
    
    @Autowired
    public AuthService(JwtUtil jwtUtil, AuthMapper authMapper){
        this.jwtUtil = jwtUtil;
        this.authMapper = authMapper;
    }

    @ApiHistory
    public Token login(Token token , HttpServletRequest request) {
        String userId = token.getUserId();
        String password = token.getPassword();

        EgovMap egovMap = new EgovMap();
        
        egovMap.put("userId", userId);
        
        EgovMap user = authMapper.getUser(egovMap);
        
        if(user.isEmpty()){
            throw new SecurityException("YOU ARE NOT OUR USER");
        }
        
        
        String saltKey = user.getString("salt");
        String pw = user.getString("password");
        String encrtPw = CryptUtil.hashSHA256(password, saltKey);
        
        if(encrtPw.equals(pw)){
            String accessToken = jwtUtil.generateToken(userId);
            
            Token result = jwtUtil.makeTokenType(user, accessToken);
            return result;
        }else {
            throw new SecurityException("YOU_ARE_NOT_OUR_USER");
        }
        
    }
    
    public int join(EgovMap param, HttpServletRequest req) {
        String pw = param.getString("password");
        
        String salt = CryptUtil.randomSalt();
        String encrytPw = CryptUtil.hashSHA256(pw, salt);
        EgovMap reqInfo = RequestUtil.getBrowserInfo(req);
        
        param.put("password", encrytPw);
        param.put("salt", salt);
        param.put("ip", reqInfo.getString("ip"));
        param.put("os", reqInfo.getString("os"));
        param.put("browser", reqInfo.getString("browser"));
        int result = authMapper.join(param);
        return result;
    }
}
