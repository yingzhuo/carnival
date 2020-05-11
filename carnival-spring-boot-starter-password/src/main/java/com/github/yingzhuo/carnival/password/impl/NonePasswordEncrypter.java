/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.password.impl;

import java.util.Objects;

/**
 * @author 应卓
 */
public class NonePasswordEncrypter extends AbstractPasswordEncrypter {

    @Override
    protected String doEncrypt(String rawPassword, String leftSalt, String rightSalt) {
        return rawPassword;
    }

    @Override
    protected boolean doMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return Objects.equals(rawPassword, encryptedPassword);
    }

}
