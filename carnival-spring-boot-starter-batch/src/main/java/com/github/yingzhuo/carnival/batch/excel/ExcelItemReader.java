/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.batch.excel;

import org.springframework.batch.extensions.excel.poi.PoiItemReader;
import org.springframework.batch.item.ItemReader;

/**
 * @author 应卓
 * @since 1.10.36
 */
public class ExcelItemReader extends PoiItemReader<ExcelData> implements ItemReader<ExcelData> {

    public ExcelItemReader() {
        super.setRowMapper(new ExcelDataRowMapper());
    }

}
