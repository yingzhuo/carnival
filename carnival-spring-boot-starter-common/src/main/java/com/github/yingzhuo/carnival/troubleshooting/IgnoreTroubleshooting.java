package com.github.yingzhuo.carnival.troubleshooting;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.6.27
 */
@Documented
@Inherited
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreTroubleshooting {
}
