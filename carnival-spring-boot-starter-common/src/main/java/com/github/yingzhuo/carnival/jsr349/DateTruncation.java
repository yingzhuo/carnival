/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

/**
 * @author 应卓
 */
public enum DateTruncation {

    NONE(-1), YEAR(1), MONTH(2), DAY(5), HOUR(10), MINUTE(12), SECOND(13), MILLISECOND(14);

    private final int value;

    DateTruncation(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
