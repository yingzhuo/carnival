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

import org.springframework.batch.extensions.excel.RowMapper;
import org.springframework.batch.extensions.excel.support.rowset.RowSet;

/**
 * @author 应卓
 * @since 1.10.36
 */
public final class ExcelDataRowMapper implements RowMapper<ExcelData> {

    @Override
    public ExcelData mapRow(RowSet rs) {
        final ExcelData ed = new ExcelData();
        ed.setRow(rs.getCurrentRow());
        ed.setRowIndex(rs.getCurrentRowIndex());
        ed.setSheetName(rs.getMetaData().getSheetName());
        return ed;
    }

}
