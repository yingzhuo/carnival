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

/**
 * @author 应卓
 * @since 1.9.2
 */
public class FalseSkipStrategy implements RowSkipStrategy {

    public static final RowSkipStrategy INSTANCE = new FalseSkipStrategy();

    private FalseSkipStrategy() {
    }

    @Override
    public boolean skip(Object model, AnalysisContext context, Exception ex) {
        return false;
    }

}
