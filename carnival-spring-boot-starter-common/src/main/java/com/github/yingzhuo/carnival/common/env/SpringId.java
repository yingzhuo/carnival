/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.env;

import org.springframework.beans.factory.annotation.Value;

import java.lang.annotation.*;

/**
 * @author 应卓
 * @since 1.10.36
 */
@Documented
@Inherited
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Value("#{environment.getProperty('spring.id')}")
public @interface SpringId {
}
