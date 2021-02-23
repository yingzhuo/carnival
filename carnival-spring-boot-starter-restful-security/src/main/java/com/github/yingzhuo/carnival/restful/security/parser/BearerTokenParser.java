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

import com.github.yingzhuo.carnival.restful.security.token.Token;
import org.springframework.http.HttpHeaders;

/**
 * @author 应卓
 * @see Token
 * @see TokenParser
 */
public class BearerTokenParser extends HttpHeaderTokenParser {

    public BearerTokenParser() {
        super(HttpHeaders.AUTHORIZATION, "Bearer ");
    }

}
