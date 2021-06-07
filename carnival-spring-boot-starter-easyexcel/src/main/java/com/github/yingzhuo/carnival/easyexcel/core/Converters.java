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

import com.alibaba.excel.metadata.CellData;
import com.alibaba.excel.metadata.GlobalConfiguration;
import com.alibaba.excel.metadata.property.ExcelContentProperty;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import java.text.ParseException;

/**
 * @author 应卓
 * @since 1.9.2
 */
public final class Converters {

    private Converters() {
    }

    // ----------------------------------------------------------------------------------------------------------------

    /**
     * ExcelData to java.util.Date
     */
    public final static class Date extends AbstractExcelConverter<java.util.Date> {

        private static java.util.Date ZERO;

        static {
            try {
                ZERO = DateUtils.parseDate("1900-01-01", "yyyy-MM-dd");
            } catch (ParseException e) {
                ZERO = new java.util.Date();
            }
        }

        @Override
        public Class supportJavaTypeKey() {
            return java.util.Date.class;
        }

        @Override
        public java.util.Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {


            System.out.println("adsl;fkjads;lfkjad;lfkj");
            try {
                return DateUtils.parseDate(
                        cellData.toString(),
                        "yyyyMMdd",
                        "yyyy-MM-dd",
                        "yyyy/MM/dd",
                        "yyyy-MM-dd HH:mm:ss",
                        "yyyy/MM/dd HH:mm:ss"
                );
            } catch (ParseException e) {
                val offset = new java.math.BigDecimal(cellData.toString()).intValue() - 1;
                return DateUtils.addDays(ZERO, offset);
            }
        }
    }

    /**
     * ExcelData to java.long.Integer
     */
    public final static class Int extends AbstractExcelConverter<java.lang.Integer> {

        @Override
        public Class supportJavaTypeKey() {
            return java.lang.Integer.class;
        }

        @Override
        public Integer convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .intValue();
        }
    }

    /**
     * ExcelData to java.long.Long
     */
    public final static class Long extends AbstractExcelConverter<java.lang.Long> {

        @Override
        public Class supportJavaTypeKey() {
            return java.lang.Long.class;
        }

        @Override
        public final java.lang.Long convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) throws Exception {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .longValue();
        }
    }

    /**
     * ExcelData to java.math.BigDecimal
     */
    public final static class BigDecimal extends AbstractExcelConverter<java.math.BigDecimal> {

        private static final BigDecimal INSTANCE = new BigDecimal();

        @Override
        public Class supportJavaTypeKey() {
            return java.math.BigDecimal.class;
        }

        @Override
        public java.math.BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            val s = StringUtils.remove(cellData.toString(), ",");
            return new java.math.BigDecimal(s);
        }
    }

}
