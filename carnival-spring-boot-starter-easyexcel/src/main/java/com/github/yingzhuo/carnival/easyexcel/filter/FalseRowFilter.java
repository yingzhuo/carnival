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

/**
 * @author 应卓
 * @since 1.9.5
 */
public final class FalseRowFilter implements RowFilter {

    public static final FalseRowFilter INSTANCE = new FalseRowFilter();

    private FalseRowFilter() {
    }

    @Override
    public boolean doFilter(Object model) {
        return false;
    }

    @Override
    public boolean test(Object model) {
        return false;
    }

}
