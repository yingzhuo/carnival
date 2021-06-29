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

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.9.5
 */
public final class RowFilterChain implements RowFilter {

    private final List<RowFilter> chain;

    private RowFilterChain() {
        this.chain = new LinkedList<>();
    }

    public static RowFilterChain newInstance() {
        return new RowFilterChain();
    }

    public RowFilterChain add(RowFilter... filters) {
        if (filters != null && filters.length != 0) {
            Collections.addAll(chain, filters);
        }
        return this;
    }

    public RowFilterChain add(Collection<RowFilter> filters) {
        chain.addAll(filters);
        return this;
    }

    @Override
    public boolean doFilter(Object model) {
        if (chain.isEmpty()) {
            return true;
        }
        return chain.stream().allMatch(f -> f.doFilter(model));
    }

    public List<RowFilter> getChain() {
        return Collections.unmodifiableList(chain);
    }

}
