package com.example.msa.common.util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * packageName    : com.example.msa.common.util
 * fileName       : RequestUtil
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : HttpServletRequest 관련 유틸리티 (클라이언트 정보 추출)
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 12. 20.          'stz'       최초 생성
 */
public class RequestUtil {

    /**
     * 클라이언트의 브라우저 정보를 Map으로 반환
     * @param request HttpServletRequest
     * @return Map (ip, header, os, browser)
     */
    public static EgovMap getBrowserInfo(HttpServletRequest request) {
        String agent = request.getHeader("USER-AGENT");
        
        String os = getClientOS(agent);
        String browser = getClientBrowser(agent);
        String ip = getClientIp(request);
        
        EgovMap map = new EgovMap();
        map.put("ip", ip);
        map.put("header", agent);
        map.put("os", os);
        map.put("browser", browser);
        
        return map;
    }
    
    /**
     * 클라이언트의 실제 IP 주소를 추출
     * Proxy, Load Balancer 환경 대응
     * @param request HttpServletRequest
     * @return IP 주소
     */
    public static String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || ip.equalsIgnoreCase("unknown")) {
            ip = request.getRemoteAddr();
        }
        
        return ip;
    }

    /**
     * User-Agent로부터 클라이언트 OS 감지
     * @param userAgent User-Agent 문자열
     * @return OS 이름
     */
    public static String getClientOS(String userAgent) {
        if (userAgent == null) {
            return "Unknown";
        }
        
        String os = "";
        userAgent = userAgent.toLowerCase();
        
        if (userAgent.contains("windows nt 10.0")) {
            os = "Windows10";
        } else if (userAgent.contains("windows nt 6.1")) {
            os = "Windows7";
        } else if (userAgent.contains("windows nt 6.2") || userAgent.contains("windows nt 6.3")) {
            os = "Windows8";
        } else if (userAgent.contains("windows nt 6.0")) {
            os = "WindowsVista";
        } else if (userAgent.contains("windows nt 5.1")) {
            os = "WindowsXP";
        } else if (userAgent.contains("windows nt 5.0")) {
            os = "Windows2000";
        } else if (userAgent.contains("windows nt 4.0")) {
            os = "WindowsNT";
        } else if (userAgent.contains("windows 98")) {
            os = "Windows98";
        } else if (userAgent.contains("windows 95")) {
            os = "Windows95";
        } else if (userAgent.contains("iphone")) {
            os = "iPhone";
        } else if (userAgent.contains("ipad")) {
            os = "iPad";
        } else if (userAgent.contains("android")) {
            os = "Android";
        } else if (userAgent.contains("mac")) {
            os = "Mac";
        } else if (userAgent.contains("linux")) {
            os = "Linux";
        } else {
            os = "Other";
        }
        
        return os;
    }

    /**
     * User-Agent로부터 브라우저 종류 감지
     * @param userAgent User-Agent 문자열
     * @return 브라우저 이름
     */
    public static String getClientBrowser(String userAgent) {
        if (userAgent == null) {
            return "Unknown";
        }

        String browser = "";

        if (userAgent.contains("Edg/")) {
            browser = "Edge";
        } else if (userAgent.contains("Trident/7.0")) {
            browser = "IE11";
        } else if (userAgent.contains("MSIE 10")) {
            browser = "IE10";
        } else if (userAgent.contains("MSIE 9")) {
            browser = "IE9";
        } else if (userAgent.contains("MSIE 8")) {
            browser = "IE8";
        } else if (userAgent.contains("Chrome/")) {
            browser = "Chrome";
        } else if (userAgent.contains("Safari/")) {
            browser = "Safari";
        } else if (userAgent.contains("Firefox/")) {
            browser = "Firefox";
        } else {
            browser = "Other";
        }

        return browser;
    }

    /**
     * Authorization 헤더에서 Bearer 토큰 추출
     * @param request HttpServletRequest
     * @return Bearer 토큰 (접두사 제거됨), 없거나 형식이 잘못된 경우 null
     */
    public static String getBearerToken(HttpServletRequest request) {
        String authHeader = request.getHeader("Authorization");

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.replace("Bearer ", "");
        }

        return null;
    }

    /**
     * 모바일 기기에서의 요청인지 확인
     * @param request HttpServletRequest
     * @return 모바일 요청이면 true, 아니면 false
     */
    public static boolean isMobileRequest(HttpServletRequest request) {
        String userAgent = request.getHeader("USER-AGENT");

        if (userAgent == null) {
            return false;
        }

        userAgent = userAgent.toLowerCase();

        return userAgent.contains("mobile") ||
               userAgent.contains("iphone") ||
               userAgent.contains("ipad") ||
               userAgent.contains("ipod") ||
               userAgent.contains("android") ||
               userAgent.contains("blackberry") ||
               userAgent.contains("windows phone") ||
               userAgent.contains("webos") ||
               userAgent.contains("opera mini") ||
               userAgent.contains("iemobile");
    }
}