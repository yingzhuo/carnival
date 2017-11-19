/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model;

/**
 * @author 应卓
 */
public enum Gender {

    /**
     * 未知
     */
    UNKNOWN(-1, "未知"),

    /**
     * 女性
     */
    FEMALE(0, "女性"),

    /**
     * 男性
     */
    MALE(1, "男性");

    private int value;
    private String description;

    Gender(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }
}
