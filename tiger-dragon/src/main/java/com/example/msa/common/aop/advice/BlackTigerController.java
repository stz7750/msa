package com.example.msa.common.aop.advice;

import com.example.msa.common.config.exception.ExceptionMessage;
import com.example.msa.common.response.ApiResponse;
import com.example.msa.common.util.EgovMap;
import com.example.msa.common.util.JwtUtil;
import com.example.msa.common.util.RequestUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * packageName    : com.example.msa.common.advice
 * fileName       : BlackTigerController
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : 공통 ResponseBodyAdvice - API 응답 자동 래핑
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 7. 15.          'stz'       최초 생성
 */
@RestControllerAdvice
public class BlackTigerController implements ResponseBodyAdvice<Object> {
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @ModelAttribute("req")
    public EgovMap reqeustUserInfo(HttpServletRequest request) {
        EgovMap params = new EgovMap();
        String accessToken = RequestUtil.getBearerToken(request);

        if (accessToken != null) {
            try {
                String username = jwtUtil.extractUsername(accessToken);
                boolean isValid = jwtUtil.validateToken(accessToken, username);

                if (isValid) {
                    RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
                    if (attributes != null) {
                        params.put("userId", username);
                        params.put("token", accessToken);
                    }
                }
            } catch (Exception e) {
            }
        }

        return params;
    }
    
    @Override
    public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
        String packageName = returnType.getContainingClass().getPackage().getName();
        return packageName.matches("com\\.example\\.msa(\\..*)?.*controller");
    }
    
    @Override
    public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
                                  Class<? extends HttpMessageConverter<?>> selectedConverterType,
                                  ServerHttpRequest request, ServerHttpResponse response) {
        if (body instanceof ApiResponse) {
            return body;
        }
        
        if (body == null) {
            return ApiResponse.builder()
                    .code(ExceptionMessage.EMPTY_DATA.getCode())
                    .msg(ExceptionMessage.EMPTY_DATA.getMessage())
                    .data(null)
                    .build();
        }
        
        
        if (body instanceof ResponseEntity) {
            ResponseEntity<?> responseEntity = (ResponseEntity<?>) body;
            Object actualBody = responseEntity.getBody();
            
            
            if (actualBody instanceof ApiResponse) {
                return actualBody;
            }
            
            return ApiResponse.builder()
                    .code(responseEntity.getStatusCodeValue())
                    .msg(ExceptionMessage.SUCCESS.getMessage())
                    .data(actualBody)
                    .build();
        }
        
        if (body instanceof List) {
            List<?> list = (List<?>) body;
            
            if (list.isEmpty()) {
                return ApiResponse.builder()
                        .code(ExceptionMessage.EMPTY_DATA.getCode())
                        .msg(ExceptionMessage.EMPTY_DATA.getMessage())
                        .data(list)
                        .build();
            } else {
                return ApiResponse.builder()
                        .code(ExceptionMessage.SELECT_DATA.getCode())
                        .msg(ExceptionMessage.SELECT_DATA.getMessage())
                        .data(list)
                        .build();
            }
        }

        return ApiResponse.builder()
                .code(ExceptionMessage.SUCCESS.getCode())
                .msg(ExceptionMessage.SUCCESS.getMessage())
                .data(body)
                .build();
    }
}
