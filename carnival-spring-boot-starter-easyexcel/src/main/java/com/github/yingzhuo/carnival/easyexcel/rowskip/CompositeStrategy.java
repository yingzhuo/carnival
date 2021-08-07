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

import java.util.Arrays;
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

    /**
     * @since 1.9.3
     */
    public static RowSkipStrategy of(RowSkipStrategy... strategies) {
        if (strategies == null || strategies.length == 0) {
            return FalseSkipStrategy.INSTANCE;
        }
        return new CompositeStrategy(Arrays.asList(strategies));
    }

    @Override
    public boolean skip(Object model, AnalysisContext context, Exception ex) {
        if (strategies == null || strategies.isEmpty()) return false;

        for (RowSkipStrategy strategy : strategies) {
            if (strategy.skip(model, context, ex)) return true;
        }

        return false;
    }

}
