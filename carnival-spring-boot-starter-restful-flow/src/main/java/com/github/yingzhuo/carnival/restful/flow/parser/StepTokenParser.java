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

import lombok.val;
import org.springframework.core.Ordered;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.Objects;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.3.6
 */
@FunctionalInterface
public interface StepTokenParser extends Ordered {

    public Optional<String> parse(NativeWebRequest request);

    @Override
    public default int getOrder() {
        return 0;
    }

    public default StepTokenParser or(StepTokenParser that) {
        return (webRequest) -> {
            val thisOp = this.parse(webRequest);

            if (thisOp.isPresent()) {
                return thisOp;
            } else {
                return Objects.requireNonNull(that).parse(webRequest);
            }
        };
    }
}
