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

import com.github.yingzhuo.carnival.graphql.request.Variables;
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

    public static final ThreadLocal<MethodParameter> MethodParameterHolder = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<ModelAndViewContainer> ModelAndViewContainerHolder = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<NativeWebRequest> NativeWebRequestHolder = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<WebDataBinderFactory> WebDataBinderFactoryHolder = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<String> OperationNameHolder = ThreadLocal.withInitial(() -> null);
    public static final ThreadLocal<Variables> VariablesHolder = ThreadLocal.withInitial(() -> null);

    private InvokeContext() {
    }

}
