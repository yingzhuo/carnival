/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password;

import java.io.Serializable;

/**
 * (immutable)
 *
 * @author 应卓
 * @since 1.1.12
 */
public class SaltPair implements Serializable {

    public final String left;
    public final String right;

    public SaltPair(String left, String right) {
        this.left = left;
        this.right = right;
    }

}
