/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.hook;

import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.userdetails.UserDetails;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 应卓
 * @since 1.2.1
 */
@Slf4j
public class LoggingAfterHook implements AfterHook {

    @Override
    public void execute(NativeWebRequest webRequest, Token token, UserDetails userDetails) {
        if (!log.isDebugEnabled()) {
            return;
        }

        log.debug("[restful-security] path: {}", ((HttpServletRequest) webRequest.getNativeRequest()).getRequestURI());
        log.debug("[restful-security] token-type: {}", token.getClass());
        log.debug("[restful-security] token: {}", token);
        log.debug("[restful-security] user-details: {}", userDetails);
    }

}
