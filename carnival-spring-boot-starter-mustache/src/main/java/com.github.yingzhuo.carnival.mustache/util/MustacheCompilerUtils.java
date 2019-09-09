/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache.util;

import com.github.yingzhuo.carnival.mustache.MustacheCompiler;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.1.8
 */
public final class MustacheCompilerUtils {

    private MustacheCompilerUtils() {
    }

    public static String compile(String templateResourceLocation, String templateName, Object data) {
        return SpringUtils.getBean(MustacheCompiler.class).compile(templateResourceLocation, templateName, data);
    }

    public static String compile(String templateResourceLocation, String templateName) {
        return SpringUtils.getBean(MustacheCompiler.class).compile(templateResourceLocation, templateName, null);
    }

}
