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

import com.github.yingzhuo.carnival.password.support.BCrypt;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * @author 应卓
 */
public enum Algorithm implements PasswordEncrypter {

    MD5 {
        @Override
        public String encrypt(String password) {
            return DigestUtils.md5Hex(password);
        }
    },

    SHA1 {
        @Override
        public String encrypt(String password) {
            return DigestUtils.sha1Hex(password);
        }
    },

    SHA256 {
        @Override
        public String encrypt(String password) {
            return DigestUtils.sha256Hex(password);
        }
    },

    BCRYPT {
        @Override
        public String encrypt(String password) {
            return BCrypt.hashpw(password, BCrypt.gensalt());
        }

        @Override
        public boolean matches(String password, String encrypted) {
            return BCrypt.checkpw(password, encrypted);
        }
    }

}
