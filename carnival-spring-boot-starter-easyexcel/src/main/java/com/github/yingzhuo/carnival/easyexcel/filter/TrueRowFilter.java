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
public final class TrueRowFilter implements RowFilter {

    public static final TrueRowFilter INSTANCE = new TrueRowFilter();

    private TrueRowFilter() {
    }

    @Override
    public boolean doFilter(Object model) {
        return true;
    }

    @Override
    public boolean test(Object model) {
        return true;
    }

}
