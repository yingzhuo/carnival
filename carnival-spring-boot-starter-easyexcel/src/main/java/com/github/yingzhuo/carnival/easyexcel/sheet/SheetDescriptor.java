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
import java.util.List;

/**
 * @author 应卓
 * @since 1.9.2
 */
public interface SheetDescriptor extends Serializable {

    public static SheetDescriptorBuilder builder() {
        return SheetDescriptorBuilder.builder();
    }

    int sheetNumber();

    int headerRowNumber();

    public Class<?> modelClass();

    public List<RowSkipStrategy> rowSkipStrategies();

    public default String description() {
        return null;
    }

}
