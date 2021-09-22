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

import com.github.yingzhuo.carnival.security.token.Token;
import com.github.yingzhuo.carnival.security.token.UsernamePasswordTokenAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.web.authentication.www.BasicAuthenticationConverter;
import org.springframework.web.context.request.NativeWebRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.22
 */
public final class BasicTokenResolver implements TokenResolver {

    public static final BasicTokenResolver INSTANCE = new BasicTokenResolver();

    private final BasicAuthenticationConverter converter = new BasicAuthenticationConverter();

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {
        try {
            UsernamePasswordAuthenticationToken token = converter.convert(request.getNativeRequest(HttpServletRequest.class));

            if (token == null) {
                return Optional.empty();
            }

            return Optional.of(new UsernamePasswordTokenAuthenticationToken(
                    (String) token.getPrincipal(),
                    (String) token.getCredentials()
            ));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
