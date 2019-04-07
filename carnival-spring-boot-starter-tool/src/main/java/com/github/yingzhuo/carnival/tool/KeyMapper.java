/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.tool;

import java.util.function.Function;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface KeyMapper extends Function<CharSequence, String> {

    public String map(CharSequence key);

    @Override
    public default String apply(CharSequence key) {
        return map(key);
    }

}
