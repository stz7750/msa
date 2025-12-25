/**
 * packageName    : com.example.msa.common.util.feign
 * fileName       : FeignClientFactory
 * author         : stz
 * date           : 2025. 12. 25.
 * description    : Feign 클라이언트 동적 생성 및 유틸리티 기능 제공
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 12. 25.     stz           최초 생성
 */
package com.example.msa.common.util.feign;

import feign.Feign;
import feign.Request;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringDecoder;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

@Component
@RequiredArgsConstructor
public class FeignUtil {

    private final ObjectFactory<HttpMessageConverters> messageConverters;

    private static final int DEFAULT_CONNECT_TIMEOUT = 10;
    private static final int DEFAULT_READ_TIMEOUT = 60;

    /**
     * 동적 URL로 Feign 클라이언트 생성 (기본 타임아웃)
     *
     * @param clientClass Feign 클라이언트 인터페이스
     * @param url 대상 서비스 URL
     * @param <T> 클라이언트 타입
     * @return 생성된 Feign 클라이언트
     */
    public <T> T createClient(Class<T> clientClass, String url) {
        validateUrl(url);
        return Feign.builder()
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .contract(new SpringMvcContract())
                .target(clientClass, url);
    }

    /**
     * 타임아웃 설정을 포함한 Feign 클라이언트 생성
     *
     * @param clientClass Feign 클라이언트 인터페이스
     * @param url 대상 서비스 URL
     * @param connectTimeoutSeconds 연결 타임아웃 (초)
     * @param readTimeoutSeconds 읽기 타임아웃 (초)
     * @param <T> 클라이언트 타입
     * @return 생성된 Feign 클라이언트
     */
    public <T> T createClientWithTimeout(Class<T> clientClass, String url,
                                         int connectTimeoutSeconds, int readTimeoutSeconds) {
        validateUrl(url);
        return Feign.builder()
                .encoder(new SpringEncoder(messageConverters))
                .decoder(new SpringDecoder(messageConverters))
                .contract(new SpringMvcContract())
                .options(new Request.Options(connectTimeoutSeconds, TimeUnit.SECONDS,
                                            readTimeoutSeconds, TimeUnit.SECONDS, true))
                .target(clientClass, url);
    }

    /**
     * 기본 타임아웃(연결 10초, 읽기 60초)으로 Feign 클라이언트 생성
     *
     * @param clientClass Feign 클라이언트 인터페이스
     * @param url 대상 서비스 URL
     * @param <T> 클라이언트 타입
     * @return 생성된 Feign 클라이언트
     */
    public <T> T createClientWithDefaultTimeout(Class<T> clientClass, String url) {
        return createClientWithTimeout(clientClass, url, DEFAULT_CONNECT_TIMEOUT, DEFAULT_READ_TIMEOUT);
    }

    /**
     * URL 유효성 검증
     *
     * @param url 검증할 URL
     * @throws IllegalArgumentException URL이 유효하지 않은 경우
     */
    private void validateUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            throw new IllegalArgumentException("URL cannot be null or empty");
        }

        try {
            new URL(url);
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException("Invalid URL format: " + url, e);
        }
    }

    /**
     * URL이 유효한지 확인
     *
     * @param url 확인할 URL
     * @return 유효하면 true, 그렇지 않으면 false
     */
    public static boolean isValidUrl(String url) {
        if (url == null || url.trim().isEmpty()) {
            return false;
        }

        try {
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            return false;
        }
    }

    /**
     * URL에서 호스트명 추출
     *
     * @param url URL 문자열
     * @return 호스트명 (추출 실패 시 null)
     */
    public static String extractHost(String url) {
        try {
            return new URL(url).getHost();
        } catch (MalformedURLException e) {
            return null;
        }
    }

    /**
     * URL에서 포트 추출
     *
     * @param url URL 문자열
     * @return 포트 번호 (기본 포트인 경우 -1)
     */
    public static int extractPort(String url) {
        try {
            return new URL(url).getPort();
        } catch (MalformedURLException e) {
            return -1;
        }
    }

    /**
     * user_id와 URL 필드명으로 서비스 URL 조회
     *
     * @param userConnect 사용자 연결 정보
     * @param urlFieldName URL 필드명 (예: "url1", "url2")
     * @param userId 사용자 ID (에러 메시지용)
     * @return 서비스 URL
     * @throws RuntimeException URL을 찾을 수 없는 경우
     */
    public String getServiceUrl(Object userConnect, String urlFieldName, String userId) {
        if (userConnect == null) {
            throw new RuntimeException("User connection not found for userId: " + userId);
        }

        String url = null;
        if (userConnect instanceof java.util.Map) {
            url = (String) ((java.util.Map<?, ?>) userConnect).get(urlFieldName);
        }

        if (url == null || url.trim().isEmpty()) {
            throw new RuntimeException(
                    String.format("Service URL (%s) not found for userId: %s", urlFieldName, userId));
        }

        validateUrl(url);
        return url;
    }
}