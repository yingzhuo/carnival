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
 * @since 1.0.0
 */
public final class Null implements Serializable, Cloneable {

    public static final Null INSTANCE = new Null();

    private Null() {
    }

    @Override
    public String toString() {
        return "NullObject";
    }

    public Null clone() {
        return this;
    }

}
