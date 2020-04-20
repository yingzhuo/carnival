/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.httpbasic.parser;

import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import com.github.yingzhuo.carnival.restful.security.token.UsernamePasswordToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Optional;

/**
 * @author 应卓
 * @since 1.5.1
 */
@Slf4j
public class FixedHttpBasicTokenParser implements TokenParser, InitializingBean {

    private final String username;
    private final String password;
    private final int order;

    public FixedHttpBasicTokenParser(String username, String password) {
        this(username, password, 0);
    }

    public FixedHttpBasicTokenParser(String username, String password, int order) {
        this.username = username;
        this.password = password;
        this.order = order;
    }

    @Override
    public int getOrder() {
        return this.order;
    }

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest) {
        return Optional.of(new UsernamePasswordToken(username, password));
    }

    @Override
    public void afterPropertiesSet() {
        log.warn("");
        log.warn("DO NOT use {} in your production environment.", getClass().getName());
        log.warn("");
    }
}
