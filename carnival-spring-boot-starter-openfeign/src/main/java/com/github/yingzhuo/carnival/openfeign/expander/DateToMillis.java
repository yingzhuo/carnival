/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.expander;

import java.util.Date;

import static feign.Param.Expander;

/**
 * @author 应卓
 * @since 1.6.17
 */
public class DateToMillis implements Expander {

    @Override
    public String expand(Object value) {
        if (value instanceof Date) {
            return String.valueOf(((Date) value).getTime());
        }
        return value.toString();
    }

}
