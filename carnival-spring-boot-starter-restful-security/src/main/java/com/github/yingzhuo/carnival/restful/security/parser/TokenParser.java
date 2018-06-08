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

import com.github.yingzhuo.carnival.common.parser.Parser;
import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * 令牌解析器
 *
 * @see HttpBasicTokenParser
 * @see Token
 */
@FunctionalInterface
public interface TokenParser extends Parser<NativeWebRequest, Token> {

    public Optional<Token> parse(NativeWebRequest webRequest, Locale locale);

}
