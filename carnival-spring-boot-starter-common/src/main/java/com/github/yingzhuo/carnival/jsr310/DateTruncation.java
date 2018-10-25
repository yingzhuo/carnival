/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr310;

/**
 * @author 应卓
 */
public enum DateTruncation {

//    public static final int YEAR = 1;
//    public static final int MONTH = 2;
//    public static final int DATE = 5;
//    public static final int HOUR = 10;
//    public static final int MINUTE = 12;
//    public static final int SECOND = 13;
//    public static final int MILLISECOND = 14;

    NONE(-1), YEAR(1), MONTH(2), DATE(5), HOUR(10), MINUTE(12), SECOND(13), MILLISECOND(14);

    private final int value;

    DateTruncation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
