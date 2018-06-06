/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datamodel.account;

import com.github.yingzhuo.carnival.datamodel.Coded;
import com.github.yingzhuo.carnival.datamodel.DisplayNamed;

/**
 * 性别
 *
 * @author 应卓
 */
public enum Gender implements Coded<Integer>, DisplayNamed {

    UNKOWN(-1, "未知"),
    FEMALE(0, "女"),
    MALE(1, "男");

    private int code;
    private String displayName;

    private Gender(int code, String displayName) {
        this.code = code;
        this.displayName = displayName;
    }

    @Override
    public Integer getCode() {
        return this.code;
    }

    @Override
    public String getDisplayName() {
        return this.displayName;
    }

    @Override
    public String toString() {
        return getDisplayName();
    }

}
