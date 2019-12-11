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

import com.github.yingzhuo.carnival.restful.security.exception.RestfulSecurityException;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 * @since 1.3.5
 */
@FunctionalInterface
public interface ExceptionHook {

    public void execute(NativeWebRequest request, Token token, RestfulSecurityException ex);

}
