/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author 应卓
 * @see SpringUtils
 * @since 1.1.4
 */
public final class RequestMappingUtils {

    private RequestMappingUtils() {
    }

    public static RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return SpringUtils.getBean(RequestMappingHandlerMapping.class);
    }

}
