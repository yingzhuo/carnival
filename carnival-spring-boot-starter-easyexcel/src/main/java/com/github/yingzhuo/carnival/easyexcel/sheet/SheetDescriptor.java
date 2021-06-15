/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel.sheet;

import com.github.yingzhuo.carnival.easyexcel.rowskip.RowSkipStrategy;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.9.2
 */
public interface SheetDescriptor extends Serializable {

    public static SheetDescriptorBuilder builder() {
        return SheetDescriptorBuilder.builder();
    }

    /**
     * Number of sheet. Begin from 1
     */
    int sheetNumber();

    /**
     * Number of header row. Begin from 1
     */
    int headerRowNumber();

    /**
     * Class of Model
     */
    public Class<?> modelClass();

    public RowSkipStrategy rowSkipStrategy();

    /**
     * @since 1.9.3
     */
    public default ErrorHandler errorHandler() {
        return ErrorHandler.LIST;
    }

    public default String description() {
        return null;
    }

    // ------------------------------------------------------------------------------------------------------------

    /**
     * @author 应卓
     * @since 1.9.3
     */
    public enum ErrorHandler {
        LIST,
        SKIP;
    }

}
