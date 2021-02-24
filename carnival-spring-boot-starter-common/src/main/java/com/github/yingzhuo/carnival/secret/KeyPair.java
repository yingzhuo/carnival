/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.secret;

import java.io.Serializable;

/**
 * @author 应卓
 * @since 1.6.1
 */
public interface KeyPair<PUBLIC, PRIVATE> extends Serializable {

    public static final int KEY_SIZE_1024 = 1024;
    public static final int KEY_SIZE_2048 = 2048;
    public static final int KEY_SIZE_4096 = 4096;

    public PUBLIC getBase64PublicKey();

    public PRIVATE getBase64PrivateKey();

}
