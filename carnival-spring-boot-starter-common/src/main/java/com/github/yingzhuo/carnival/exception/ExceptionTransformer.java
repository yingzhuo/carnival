/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import java.util.Optional;
import java.util.function.Function;

/**
 * @author 应卓
 * @since 1.6.5
 */
@FunctionalInterface
public interface ExceptionTransformer extends Function<Exception, Exception> {

    public Optional<Exception> transform(Exception from);

    @Override
    default Exception apply(Exception e) {
        return transform(e).orElse(null);
    }

}
