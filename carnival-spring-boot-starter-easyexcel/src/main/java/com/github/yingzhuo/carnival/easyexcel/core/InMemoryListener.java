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
import com.github.yingzhuo.carnival.easyexcel.rowskip.FalseSkipStrategy;
import com.github.yingzhuo.carnival.easyexcel.rowskip.RowSkipStrategy;
import com.github.yingzhuo.carnival.easyexcel.sheet.SheetDescriptor;
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

    private final RowSkipStrategy skipStrategy;
    private final SheetDescriptor.ErrorHandler errorHandler;
    private final String filename;

    public InMemoryListener(RowSkipStrategy skipStrategy, SheetDescriptor.ErrorHandler errorHandler, Resource resource) {
        this.skipStrategy = Optional.ofNullable(skipStrategy).orElse(FalseSkipStrategy.INSTANCE);
        this.errorHandler = Optional.ofNullable(errorHandler).orElse(SheetDescriptor.ErrorHandler.LIST);
        this.filename = Optional.ofNullable(resource).map(Resource::getFilename).orElse(null);
    }

    @Override
    public final void invoke(M model, AnalysisContext context) {
        if (!skipStrategy.skip(model, context, null)) {
            result.add(model);
        }
    }

    @Override
    public final void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // NOP
    }

    @Override
    public void onException(Exception exception, AnalysisContext context) throws Exception {

        if (!(exception instanceof ExcelDataConvertException)) {
            throw exception;
        }

        if (skipStrategy.skip(exception, context, exception)) {
            return;
        }

        if (errorHandler == SheetDescriptor.ErrorHandler.SKIP) {
            return;
        }

        final ExcelDataConvertException ex = (ExcelDataConvertException) exception;
        ReadingError error = new ReadingError();
        error.setFilename(filename);
        error.setSheetNumber(context.readSheetHolder().getSheetNo());
        error.setSheetName(context.readSheetHolder().getSheetName());
        error.setRowNumber(ex.getRowIndex() + 1);
        error.setColNumber(ex.getColumnIndex() + 1);
        error.setExceptionMessage(exception.getMessage());
        error.setExceptionType(exception.getClass().getName());
        errors.add(error);

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
