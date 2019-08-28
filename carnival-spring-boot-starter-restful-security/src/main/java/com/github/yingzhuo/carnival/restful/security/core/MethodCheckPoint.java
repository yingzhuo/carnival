/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.annotation.AuthenticationComponent;

import java.lang.annotation.Annotation;

/**
 * @author 应卓
 */
public final class MethodCheckPoint {

    private final Annotation annotation;
    private final AuthenticationComponent authenticationComponent;

    public MethodCheckPoint(Annotation annotation, AuthenticationComponent authenticationComponent) {
        this.annotation = annotation;
        this.authenticationComponent = authenticationComponent;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public AuthenticationComponent getAuthenticationComponent() {
        return authenticationComponent;
    }

}
