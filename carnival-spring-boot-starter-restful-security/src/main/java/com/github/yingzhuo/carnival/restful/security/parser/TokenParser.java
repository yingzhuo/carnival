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
import lombok.val;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * 令牌解析器
 *
 * @author 应卓
 * @see HttpBasicTokenParser
 * @see Token
 */
@FunctionalInterface
public interface TokenParser extends Ordered {

    public Optional<Token> parse(NativeWebRequest webRequest);

    public default TokenParser or(TokenParser that) {
        return (webRequest) -> {
            val thisOp = this.parse(webRequest);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).parse(webRequest);
            }
        };
    }

    @Override
    public default int getOrder() {
        return 0;
    }

}
