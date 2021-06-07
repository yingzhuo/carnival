/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.core;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.exception.ExcelDataConvertException;
import com.github.yingzhuo.carnival.easyexcel.ReadingError;
import com.github.yingzhuo.carnival.easyexcel.rowskip.RowSkipStrategy;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.9.2
 */
public class InMemoryListener<M> extends AnalysisEventListener<M> {

    private final List<M> result = new LinkedList<M>();
    private final List<ReadingError> errors = new LinkedList<>();
    private final List<RowSkipStrategy> rowSkipStrategies;

    public InMemoryListener(List<RowSkipStrategy> rowSkipStrategies) {
        this.rowSkipStrategies = rowSkipStrategies != null ? Collections.emptyList() : Collections.emptyList();
    }

    @Override
    public final void invoke(M model, AnalysisContext context) {
        boolean skip = false;

        for (RowSkipStrategy s : this.rowSkipStrategies) {
            if (s.isSkip(model, context)) {
                skip = true;
                break;
            }
        }

        if (!skip) {
            result.add(model);
        }
    }

    @Override
    public final void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // NOP
    }

    @Override
    public void onException(Exception ex, AnalysisContext context) throws Exception {

        boolean skip = false;

        for (RowSkipStrategy s : this.rowSkipStrategies) {
            if (s.isSkip(null, context)) {
                skip = true;
                break;
            }
        }

        if (skip) return;

        if (ex instanceof ExcelDataConvertException) {
            ReadingError error = new ReadingError();
            error.setFilename(null);
            error.setSheetNumber(context.readSheetHolder().getRowIndex());
            error.setRowNumber(context.readRowHolder().getRowIndex());
            error.setExceptionMessage(ex.getMessage());
            errors.add(error);
        } else {
            throw ex;
        }
    }

    // 注意，这个方法并不是public
    final List<M> getResult() {
        return Collections.unmodifiableList(result);
    }

    final List<ReadingError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
