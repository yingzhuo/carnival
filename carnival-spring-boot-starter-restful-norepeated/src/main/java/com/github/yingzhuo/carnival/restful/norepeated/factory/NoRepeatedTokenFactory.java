/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.norepeated.factory;

import java.util.function.Supplier;

/**
 * @author 应卓
 * @since 1.6.7
 */
@FunctionalInterface
public interface NoRepeatedTokenFactory extends Supplier<String> {

    public String create();

    @Override
    default String get() {
        return create();
    }

}
