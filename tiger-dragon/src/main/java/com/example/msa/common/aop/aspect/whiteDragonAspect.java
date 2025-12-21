package com.example.msa.common.aop.aspect;

import com.example.msa.common.service.HistoryLoggingService;
import com.example.msa.common.util.EgovMap;
import com.example.msa.common.util.RequestUtil;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Base64;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * packageName    : msa-com.example.msa.common.aop.aspect
 * fileName       : whiteDragonAspect
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    : API 호출 이력 기록 Aspect (HistoryLoggingService 사용)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 9. 21.          'stz'       최초 생성
 * 25. 12. 21.          'stz'       HistoryLoggingService 인터페이스 사용
 */
@Component
@Aspect
public class whiteDragonAspect {

    @Autowired(required = false)
    private HistoryLoggingService historyLoggingService;

    @Pointcut("@annotation(com.example.msa.common.config.decorator.ApiHistory)")
    public void apiHistory(){}


    @Around("apiHistory()")
    public Object apiHistoryAround(ProceedingJoinPoint joinPoint) throws Throwable {
        EgovMap param = new EgovMap();
        Object result = null;
        String histId = null;

        try {
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpServletRequest request = requestAttributes.getRequest();
            String url = request.getRequestURI();
            String method = request.getMethod();

            Object[] args = joinPoint.getArgs();
            if(args.length > 0) {
                for (Object arg : args) {
                    if(arg instanceof EgovMap) param.putAll((EgovMap) arg);
                    else if(arg instanceof Map) param.putAll(EgovMap.fromMap((Map) arg));
                }
            }

            String execId = extractUserIdFromToken(request);

            param.put("execId", execId);
            param.put("url", url);
            param.put("method", method);
            param.put("status", "RUNNING");
            param.put("execStartDt", Timestamp.valueOf(LocalDateTime.now()));

            if (historyLoggingService != null) {
                histId = historyLoggingService.logging(param);
            } else {

            }

        } catch (Exception e) {
        }

        try {
            result = joinPoint.proceed();

            if (historyLoggingService != null) {
                param.put("histId", histId);
                param.put("status", "SUCCESS");
                param.put("execEndDt", Timestamp.valueOf(LocalDateTime.now()));
                historyLoggingService.logging(param);
            }

        } catch (Exception e) {
            if (historyLoggingService != null) {
                param.put("histId", histId);
                param.put("status", "FAILED");
                param.put("execEndDt", Timestamp.valueOf(LocalDateTime.now()));
                param.put("msg", e.getMessage());
                historyLoggingService.logging(param);
            }
            throw e;
        }

        return result;
    }

    /**
     * JWT 토큰에서 사용자 ID 추출
     * @param request HTTP 요청
     * @return 사용자 ID (없으면 "admin")
     */
    private String extractUserIdFromToken(HttpServletRequest request) {
        try {
            String token = RequestUtil.getBearerToken(request);
            if (token != null) {
                String[] parts = token.split("\\.");
                if (parts.length >= 2) {
                    String payload = new String(Base64.getUrlDecoder().decode(parts[1]));

                    ObjectMapper mapper = new ObjectMapper();
                    Map<String, Object> claims = mapper.readValue(payload, Map.class);

                    if (claims.containsKey("sub")) {
                        return claims.get("sub").toString();
                    } else if (claims.containsKey("username")) {
                        return claims.get("username").toString();
                    }
                }
            }
        } catch (Exception e) {
        }

        return "admin";
    }
}
