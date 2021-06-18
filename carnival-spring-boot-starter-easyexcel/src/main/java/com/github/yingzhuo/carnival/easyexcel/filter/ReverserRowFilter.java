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

import java.util.Objects;

/**
 * @author 应卓
 * @since 1.9.5
 */
public final class ReverserRowFilter implements RowFilter {

    public static ReverserRowFilter of(RowFilter delegate) {
        return new ReverserRowFilter(delegate);
    }

    private final RowFilter delegate;

    public ReverserRowFilter(RowFilter delegate) {
        this.delegate = Objects.requireNonNull(delegate);
    }

    @Override
    public boolean doFilter(Object model) {
        return !delegate.doFilter(model);
    }

    public RowFilter getDelegate() {
        return delegate;
    }

}
