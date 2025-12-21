package com.example.msa.common.util;

import org.apache.commons.collections.map.ListOrderedMap;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Camel Case 표기법 변환 처리를 포함하는 Map 확장 클래스
 * <p>
 * <b>NOTE</b>: commons Collections 의 ListOrderedMap 을
 * extends 하고 있으며 Map 의 key 를 입력 시 Camel Case 표기법으로
 * 변경하여 처리하는 Map 의 구현체이다.
 * @author 실행환경 개발팀 우병훈
 * @since 2009.02.06
 * @version 1.0
 */
public class EgovMap extends ListOrderedMap {

    private static final long serialVersionUID = 6723434363565852261L;

    /**
     * key 에 대하여 Camel Case 변환하여 super.put
     * (ListOrderedMap) 을 호출한다.
     * @param key
     *        - '_' 가 포함된 변수명
     * @param value
     *        - 명시된 key 에 대한 값 (변경 없음)
     * @return previous value associated with specified
     *         key, or null if there was no mapping for
     *         key
     */
    @Override
    public Object put(Object key, Object value) {
        return super.put(CamelUtil.convert2CamelCase((String) key), value);
    }
    
    
    public String getString(Object key) {
		Object obj = super.get(key);
		
		if (obj != null) {
			if (obj instanceof String) {
				return (String) obj;
			} else if (obj instanceof BigDecimal) {
				return ((BigDecimal) obj).stripTrailingZeros().toPlainString();
			} else if (obj instanceof Integer) {
				return String.valueOf(obj);
			} else if (obj instanceof Long) {
				return String.valueOf(obj);
			} else if (obj instanceof Float) {
				return String.valueOf(obj);
			} else if (obj instanceof Double) {
				if (!Double.isNaN((Double) obj) && !Double.isInfinite((Double) obj)) {
					return BigDecimal.valueOf((Double) obj).stripTrailingZeros().toPlainString();
				} else {
					return String.valueOf(obj);
				}
			} else {
				return String.valueOf(obj);
			}
		}
		
		return "";
	}

	public static EgovMap fromMap(Map<String , Object> map) {
		EgovMap egovMap = new EgovMap();
		if(map == null) {
			return egovMap;
		}
		for(Map.Entry<String,Object> entry : map.entrySet()) {
			egovMap.put(entry.getKey(), entry.getValue());
		}
		return egovMap;
	}
}
