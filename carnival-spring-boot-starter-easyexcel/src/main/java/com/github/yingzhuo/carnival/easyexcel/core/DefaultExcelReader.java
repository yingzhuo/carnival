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

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.read.metadata.ReadSheet;
import com.github.yingzhuo.carnival.easyexcel.ExcelReader;
import com.github.yingzhuo.carnival.easyexcel.ReadingError;
import com.github.yingzhuo.carnival.easyexcel.ReadingResult;
import com.github.yingzhuo.carnival.easyexcel.sheet.SheetDescriptor;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;

import java.util.List;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.9.2
 */
public class DefaultExcelReader implements ExcelReader {

    @Override
    @SneakyThrows
    public <M> ReadingResult<M> read(Resource excelFile, SheetDescriptor descriptor) {
        Objects.requireNonNull(excelFile);
        Objects.requireNonNull(descriptor);

        InMemoryListener<M> listener = new InMemoryListener<>(descriptor.rowSkipStrategies());

        com.alibaba.excel.ExcelReader excelReader = EasyExcel.read(excelFile.getInputStream(), descriptor.modelClass(), listener)
//                .excelType(ExcelTypeEnum.XLSX)
                .build();

        ReadSheet readSheet = EasyExcel.readSheet(descriptor.sheetNumber())
                .headRowNumber(descriptor.headerRowNumber())
                .build();

        try {
            excelReader.read(readSheet);

            return new ReadingResult<M>() {
                @Override
                public List<M> getModels() {
                    return listener.getResult();
                }

                @Override
                public List<ReadingError> getErrors() {
                    return listener.getErrors();
                }
            };
        } finally {
            if (excelReader != null) {
                excelReader.finish();
            }
        }

    }


}
