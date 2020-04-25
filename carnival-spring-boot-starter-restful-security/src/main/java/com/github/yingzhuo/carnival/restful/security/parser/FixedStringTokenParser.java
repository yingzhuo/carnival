/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.parser;

import com.github.yingzhuo.carnival.restful.security.token.StringToken;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.4.10
 */
@Slf4j
public class FixedStringTokenParser implements TokenParser, InitializingBean {

    private final Token token;
    private final int order;

    public FixedStringTokenParser(String token) {
        this(token, 0);
    }

    public FixedStringTokenParser(String token, int order) {
        this.token = StringToken.of(Objects.requireNonNull(token));
        this.order = order;
    }

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest) {
        return Optional.of(token);
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("~~~~~~");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("~~~~~~");
    }

}
