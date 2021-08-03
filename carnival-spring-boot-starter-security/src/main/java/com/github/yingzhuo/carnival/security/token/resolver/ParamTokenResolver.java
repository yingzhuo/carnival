/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.security.token.resolver;

import com.github.yingzhuo.carnival.security.token.StringToken;
import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.2
 */
public class ParamTokenResolver implements TokenResolver {

    private final String paramName;
    private final String prefix;
    private final int prefixLen;

    public ParamTokenResolver(String paramName, String prefix) {
        if (prefix == null) prefix = "";
        this.paramName = paramName;
        this.prefix = prefix;
        this.prefixLen = prefix.length();
    }

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {
        String paramValue = request.getParameter(paramName);

        if (paramValue == null || !paramValue.startsWith(prefix)) {
            return Optional.empty();
        }

        paramValue = paramValue.substring(prefixLen);

        if (paramValue.split("\\.").length == 2 && !paramValue.endsWith(".")) {
            paramValue += ".";
        }

        return Optional.of(new StringToken(paramValue));
    }

}
