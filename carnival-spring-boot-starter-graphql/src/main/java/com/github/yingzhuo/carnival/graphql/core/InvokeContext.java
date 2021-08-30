/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.core;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.graphql.servlet.InvokeContextSettingResolver
 * @since 1.10.14
 */
public final class InvokeContext {

    public static final InvokeContext INSTANCE = new InvokeContext();

    public static final ThreadLocal<MethodParameter> METHOD_PARAMETER_HOLDER = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<ModelAndViewContainer> MAV_CONTAINER_HOLDER = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<NativeWebRequest> REQUEST_HOLDER = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<WebDataBinderFactory> WEB_DATA_BINDER_FACTORY_HOLDER = ThreadLocal.withInitial(() -> null);

    private InvokeContext() {
    }

}
