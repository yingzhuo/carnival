/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt;

import com.github.yingzhuo.carnival.jwt.realm.AbstractJwtUserDetailsRealm;
import com.github.yingzhuo.carnival.jwt.token.JwtTokenParser;
import com.github.yingzhuo.carnival.restful.security.RestfulSecurityNode;
import com.github.yingzhuo.carnival.restful.security.parser.TokenParser;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public abstract class AbstractJwtSecurityNode extends AbstractJwtUserDetailsRealm implements RestfulSecurityNode {

    private static final TokenParser PARSER = new JwtTokenParser();

    @Override
    public Optional<Token> parse(NativeWebRequest webRequest, Locale locale) {
        return PARSER.parse(webRequest, locale);
    }

}
