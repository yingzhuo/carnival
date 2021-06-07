/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.easyexcel;

import com.github.yingzhuo.carnival.easyexcel.sheet.SheetDescriptor;
import org.springframework.core.io.Resource;

/**
 * @author 应卓
 * @since 1.9.2
 */
@FunctionalInterface
public interface ExcelReader {

    public <M> ReadingResult<M> read(Resource excelFile, SheetDescriptor descriptor);

}
