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

import com.github.yingzhuo.carnival.security.token.FixedUserToken;
import com.github.yingzhuo.carnival.security.token.Token;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.10.17
 */
public class FixedUserTokenParser implements TokenResolver {

    private final UserDetails userDetails;

    public FixedUserTokenParser(UserDetails userDetails) {
        this.userDetails = Objects.requireNonNull(userDetails);
    }

    @Override
    public Optional<Token> resolve(NativeWebRequest request) {
        return Optional.of(new FixedUserToken(userDetails));
    }

}
