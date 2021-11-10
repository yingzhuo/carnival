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

import org.springframework.batch.item.ItemProcessor;
import org.springframework.lang.Nullable;

/**
 * @author 应卓
 * @since 1.10.36
 */
public class ExcelItemProcess implements ItemProcessor<ExcelData, ExcelData> {

    @Override
    public ExcelData process(@Nullable ExcelData item) {
        return item;
    }

}
