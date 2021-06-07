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

import java.util.ArrayList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.9.2
 */
public final class SheetDescriptorBuilder {

    private final List<RowSkipStrategy> rowSkipStrategies = new ArrayList<>();
    private int sheetNumber = 0;
    private int headerRowNumber = 1;
    private Class<?> modelClass;
    private String description;

    private SheetDescriptorBuilder() {
    }

    public static SheetDescriptorBuilder builder() {
        return new SheetDescriptorBuilder();
    }

    public SheetDescriptorBuilder sheetNumber(int sheetNumber) {
        this.sheetNumber = sheetNumber;
        return this;
    }

    public SheetDescriptorBuilder headerRowNumber(int headerRowNumber) {
        this.headerRowNumber = headerRowNumber;
        return this;
    }

    public SheetDescriptorBuilder modelClass(Class<?> modelClass) {
        this.modelClass = modelClass;
        return this;
    }

    public SheetDescriptorBuilder rowSkipStrategies(RowSkipStrategy strategy) {
        this.rowSkipStrategies.add(strategy);
        return this;
    }

    public SheetDescriptorBuilder description(String description) {
        this.description = description;
        return this;
    }

    public SheetDescriptor build() {
        return new SheetDescriptor() {
            @Override
            public int sheetNumber() {
                return sheetNumber;
            }

            @Override
            public int headerRowNumber() {
                return headerRowNumber;
            }

            @Override
            public Class<?> modelClass() {
                return modelClass;
            }

            @Override
            public List<RowSkipStrategy> rowSkipStrategies() {
                return rowSkipStrategies;
            }

            @Override
            public String description() {
                return description;
            }
        };
    }
}
