/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common;

import java.io.Serializable;

/**
 * @author 应卓
 */
public final class Null implements Serializable, DisplayNamed {

    public static Null INSTANCE = new Null();

    private Null() {
    }

    public String toString() {
        return "Null";
    }

    @Override
    public String getDisplayName() {
        return toString();
    }

}
