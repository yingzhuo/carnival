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

import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 应卓
 */
public class SHA1PasswordEncrypter extends AbstractPasswordEncrypter {

    @Override
    protected String doEncrypt(String rawPassword, String leftSalt, String rightSalt) {
        return DigestUtils.sha1Hex(contact(rawPassword, leftSalt, rightSalt));
    }

}
