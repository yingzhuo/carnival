/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.flow.parser;

import org.springframework.http.HttpHeaders;

/**
 * @author 应卓
 * @since 1.3.6
 */
public class DefaultStepTokenParser extends HttpHeaderStepTokenParser {

    private static final String BEARER = "Bearer ";

    public DefaultStepTokenParser() {
        super(HttpHeaders.AUTHORIZATION, BEARER);
    }

}
