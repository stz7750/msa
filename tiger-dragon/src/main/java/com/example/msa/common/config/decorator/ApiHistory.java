package com.example.msa.common.config.decorator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * packageName    : msa-com.example.msa.common.config.decorator
 * fileName       : LogHistory
 * author         : 'stz'
 * date           : 25. 12. 21.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 25. 7. 15.          'stz'       최초 생성
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiHistory {
}
