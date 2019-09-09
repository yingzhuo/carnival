/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache;

/**
 * @author 应卓
 * @since 1.1.8
 */
public interface MustacheCompiler {

    public String compile(String templateResourceLocation, String templateName, Object data);

    public default String compile(String templateResourceLocation, String templateName) {
        return compile(templateResourceLocation, templateName, null);
    }

}
