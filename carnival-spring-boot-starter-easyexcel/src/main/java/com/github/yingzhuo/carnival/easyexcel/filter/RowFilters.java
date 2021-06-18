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

/**
 * @author 应卓
 * @since 1.9.5
 */
public final class RowFilters {

    public static final RowFilter TRUE = m -> true;
    public static final RowFilter FALSE = m -> false;

    public static RowFilter reverse(RowFilter filter) {
        return ReverserRowFilter.of(filter);
    }

    public static RowFilter chain(RowFilter... filters) {
        return RowFilterChain.newInstance().add(filters);
    }

    public static RowFilter chain(Collection<RowFilter> filters) {
        return RowFilterChain.newInstance().add(filters);
    }

    // ----------------------------------------------------------------------------------------------------------------

    private RowFilters() {
    }

}
