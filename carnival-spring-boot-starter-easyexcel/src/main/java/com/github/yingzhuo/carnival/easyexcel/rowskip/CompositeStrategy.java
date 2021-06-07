/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.rowskip;

import com.alibaba.excel.context.AnalysisContext;

import java.util.List;

/**
 * @author 应卓
 * @since 1.9.2
 */
public final class CompositeStrategy implements RowSkipStrategy {

    private final List<RowSkipStrategy> strategies;

    public CompositeStrategy(List<RowSkipStrategy> strategies) {
        this.strategies = strategies;
    }

    @Override
    public boolean skip(Object model, AnalysisContext context) {
        if (strategies == null || strategies.isEmpty()) return false;

        for (RowSkipStrategy strategy : strategies) {
            if (strategy.skip(model, context)) return true;
        }

        return false;
    }
}
