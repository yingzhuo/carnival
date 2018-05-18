/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jwt.validating;

import org.springframework.web.context.request.WebRequest;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface TokenParser extends Function<WebRequest, Optional<String>> {

    public Optional<String> parse(WebRequest webRequest);

    @Override
    public default Optional<String> apply(WebRequest webRequest) {
        return parse(webRequest);
    }

}
