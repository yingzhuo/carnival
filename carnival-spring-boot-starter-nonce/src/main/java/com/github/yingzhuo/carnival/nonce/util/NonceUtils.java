/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nonce.util;

import com.github.yingzhuo.carnival.nonce.NonceToken;
import com.github.yingzhuo.carnival.nonce.NonceTokenDao;
import com.github.yingzhuo.carnival.nonce.NonceTokenGenerator;
import com.github.yingzhuo.carnival.spring.SpringUtils;

/**
 * @author 应卓
 * @since 1.6.29
 */
public final class NonceUtils {

    private NonceUtils() {
    }

    public static NonceToken generate() {
        return SpringUtils.getBean(NonceTokenGenerator.class).next();
    }

    public static void save(NonceToken nonceToken) {
        SpringUtils.getBean(NonceTokenDao.class).save(nonceToken);
    }

    public static void delete(NonceToken nonceToken) {
        SpringUtils.getBean(NonceTokenDao.class).delete(nonceToken);
    }

    public static boolean exists(NonceToken nonceToken) {
        return SpringUtils.getBean(NonceTokenDao.class).exists(nonceToken);
    }

}
