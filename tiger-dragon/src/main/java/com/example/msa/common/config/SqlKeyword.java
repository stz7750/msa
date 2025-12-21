package com.example.msa.common.config;

import lombok.Getter;

import java.util.Arrays;

/**
 * packageName    : com.example.msa.common.config
 * fileName       : SqlKeyword
 * author         : 'stz'
 * date           : 25. 12. 20.
 * description    : SQL 주입 방지를 위한 금지 키워드
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 24. 7. 26.          'stz'       최초 생성
 */
@Getter
public enum SqlKeyword {
    INSERT,
    UPDATE,
    DELETE,
    CREATE,
    DROP,
    EXEC,
    EXECUTE,
    UNION,
    UNION_ALL("UNION ALL"),
    TRUNCATE,
    DECLARE,
    ALTER,
    GRANT,
    REVOKE,
    SELECT;
    
    private final String keyword;
    
    SqlKeyword() {
        this.keyword = this.name();
    }
    
    SqlKeyword(String keyword) {
        this.keyword = keyword;
    }
    
    /**
     * String[] 배열로 변환
     */
    public static String[] toArray() {
        return Arrays.stream(SqlKeyword.values())
                .map(SqlKeyword::getKeyword)
                .toArray(String[]::new);
    }
    
    /**
     * 특정 키워드 포함 여부 확인
     */
    public static boolean contains(String text) {
        if (text == null) {
            return false;
        }
        
        String upperText = text.toUpperCase();
        return Arrays.stream(SqlKeyword.values())
                .anyMatch(keyword -> upperText.contains(keyword.getKeyword()));
    }
}
