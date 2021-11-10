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

import org.springframework.batch.item.ItemWriter;
import org.springframework.lang.NonNull;

import java.util.List;

/**
 * @author 应卓
 * @since 1.10.36
 */
public class ExcelItemWriter implements ItemWriter<ExcelData> {

    @Override
    public void write(@NonNull List<? extends ExcelData> items) {
        items.forEach(this::write);
    }

    protected void write(ExcelData item) {
        // nop
    }

}
