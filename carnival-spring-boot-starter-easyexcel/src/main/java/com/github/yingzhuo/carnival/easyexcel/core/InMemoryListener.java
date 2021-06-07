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
import org.springframework.core.io.Resource;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.9.2
 */
class InMemoryListener<M> extends AnalysisEventListener<M> {

    private final List<M> result = new LinkedList<M>();
    private final List<ReadingError> errors = new LinkedList<>();

    private final List<RowSkipStrategy> rowSkipStrategies;
    private final String filename;

    public InMemoryListener(List<RowSkipStrategy> rowSkipStrategies, Resource resource) {
        this.rowSkipStrategies = rowSkipStrategies != null ? Collections.emptyList() : Collections.emptyList();
        this.filename = Optional.ofNullable(resource).map(Resource::getFilename).orElse(null);
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
            error.setFilename(filename);
            error.setSheetNumber(context.readSheetHolder().getSheetNo());
            error.setSheetName(context.readSheetHolder().getSheetName());
            error.setRowNumber(((ExcelDataConvertException) ex).getRowIndex() + 1);
            error.setColNumber(((ExcelDataConvertException) ex).getColumnIndex() + 1);
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

    // 注意，这个方法并不是public
    final List<ReadingError> getErrors() {
        return Collections.unmodifiableList(errors);
    }

}
