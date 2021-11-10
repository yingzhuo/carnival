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

import lombok.Data;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.10.36
 */
@Data
public class ExcelData implements Serializable {

    private String sheetName;
    private int rowIndex;
    private String[] row;

    public int size() {
        return row != null ? row.length : 0;
    }

    public boolean isEmpty() {
        return size() != 0;
    }

}
