/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc;

import com.github.yingzhuo.carnival.mvc.autoconfig.MvcDebugAutoConfig;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @author 应卓
 */
@Inherited
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(MvcDebugAutoConfig.class)
public @interface EnableMvcDebugLogging {
}