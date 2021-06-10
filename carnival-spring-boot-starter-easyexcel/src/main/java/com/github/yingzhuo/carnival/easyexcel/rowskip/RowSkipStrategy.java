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
@FunctionalInterface
public interface RowSkipStrategy {

    public static RowSkipStrategiesBuilder builder() {
        return RowSkipStrategiesBuilder.newInstance();
    }

    public boolean skip(Object model, AnalysisContext context, Exception ex);

}
