/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.filter;

import java.util.function.Predicate;

/**
 * @author 应卓
 * @since 1.9.5
 */
@FunctionalInterface
public interface RowFilter extends Predicate<Object> {

    public boolean doFilter(Object model);

    @Override
    default boolean test(Object model) {
        return doFilter(model);
    }

}
