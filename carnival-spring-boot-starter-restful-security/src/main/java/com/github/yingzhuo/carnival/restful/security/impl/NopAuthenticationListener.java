/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.impl;

import com.github.yingzhuo.carnival.restful.security.AuthenticationListener;
import com.github.yingzhuo.carnival.restful.security.UserDetails;
import org.springframework.web.context.request.WebRequest;

import java.lang.reflect.Method;

/**
 * @author 应卓
 */
public class NopAuthenticationListener implements AuthenticationListener {

    @Override
    public void onAuthenticated(WebRequest request, UserDetails userDetails, Method method) {
        // NOP
    }

}
