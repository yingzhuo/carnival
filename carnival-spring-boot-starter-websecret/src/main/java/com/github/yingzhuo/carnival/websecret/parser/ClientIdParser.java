/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.parser;

import com.github.yingzhuo.carnival.common.parser.Parser;
import lombok.val;
import org.springframework.web.context.request.WebRequest;

import java.util.Objects;

/**
 * @author 应卓
 */
@FunctionalInterface
@SuppressWarnings("ALL")
public interface ClientIdParser extends Parser<WebRequest, String> {

    public default ClientIdParser or(ClientIdParser that) {
        return (webRequest, locale) -> {
            val thisOp = this.parse(webRequest, locale);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).parse(webRequest, locale);
            }
        };
    }

}
