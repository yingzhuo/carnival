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

import com.github.yingzhuo.carnival.password.support.BCrypt;
import lombok.extern.slf4j.Slf4j;

/**
 * @author 应卓
 */
@Slf4j
public class BCryptPasswordEncrypter extends AbstractPasswordEncrypter {

    private final String bcryptSalt = BCrypt.gensalt();

    @Override
    protected String doEncrypt(String rawPassword, String leftSalt, String rightSalt) {
        return BCrypt.hashpw(contact(rawPassword, leftSalt, rightSalt), bcryptSalt);
    }

    @Override
    protected boolean doMatches(String rawPassword, String leftSalt, String rightSalt, String encryptedPassword) {
        return BCrypt.checkpw(contact(rawPassword, leftSalt, rightSalt), encryptedPassword);
    }

}
