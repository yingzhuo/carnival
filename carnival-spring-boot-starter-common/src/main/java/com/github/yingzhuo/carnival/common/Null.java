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
public final class Null implements Serializable {

    private static final long serialVersionUID = -4469168606541063401L;

    public static final Null INSTANCE = new Null();

    private Null() {
        super();
    }

    @Override
    public String toString() {
        return "null";
    }

}
