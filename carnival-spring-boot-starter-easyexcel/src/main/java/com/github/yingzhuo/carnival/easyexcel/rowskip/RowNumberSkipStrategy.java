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

import java.util.Objects;
import java.util.Set;

/**
 * @author 应卓
 * @since 1.9.2
 */
public class RowNumberSkipStrategy implements RowSkipStrategy {

    private final Set<Integer> lineNumbers;

    public RowNumberSkipStrategy(Set<Integer> lineNumbers) {
        this.lineNumbers = Objects.requireNonNull(lineNumbers);
    }

    @Override
    public boolean skip(Object model, AnalysisContext context, Exception ex) {
        return lineNumbers.contains(context.readRowHolder().getRowIndex());
    }

}
