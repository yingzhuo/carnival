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
@SuppressWarnings("rawtypes")
public interface Converters {

    // -----------------------------------------------------------------------------------------------------------------

    /**
     * ExcelData to java.util.Date
     */
    public final static class Date extends AbstractExcelConverter<java.util.Date> {

        private final static java.util.Date ZERO;

        static {
            try {
                ZERO = DateUtils.parseDate("1900-01-01", "yyyy-MM-dd");
            } catch (ParseException e) {
                throw new RuntimeException(e); // Never
            }
        }

        @Override
        public Class supportJavaTypeKey() {
            return java.util.Date.class;
        }

        @Override
        public java.util.Date convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
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
        public final java.lang.Long convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .longValue();
        }
    }

    /**
     * ExcelData to java.long.Double
     */
    public final static class Double extends AbstractExcelConverter<java.lang.Double> {
        @Override
        public Class supportJavaTypeKey() {
            return java.lang.Double.class;
        }

        @Override
        public java.lang.Double convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .doubleValue();
        }
    }

    /**
     * ExcelData to java.math.BigInteger
     */
    public final static class BigInt extends AbstractExcelConverter<java.math.BigInteger> {
        @Override
        public Class supportJavaTypeKey() {
            return java.lang.Float.class;
        }

        @Override
        public java.math.BigInteger convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .toBigInteger();
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

    /**
     * ExcelData to java.math.BigDecimal
     *
     * @since 1.9.4
     */
    public final static class BigDecimal2 extends AbstractExcelConverter<java.math.BigDecimal> {

        @Override
        public Class supportJavaTypeKey() {
            return java.math.BigDecimal.class;
        }

        @Override
        public java.math.BigDecimal convertToJavaData(CellData cellData, ExcelContentProperty contentProperty, GlobalConfiguration globalConfiguration) {
            return BigDecimal.INSTANCE
                    .convertToJavaData(cellData, contentProperty, globalConfiguration)
                    .divide(java.math.BigDecimal.ONE, 2, java.math.BigDecimal.ROUND_HALF_UP);
        }
    }

}
