/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha;

import java.util.UUID;

/**
 * @author 应卓
 * @since 1.10.8
 */
@FunctionalInterface
public interface AccessKeyGenerator {

    public static AccessKeyGenerator getDefault() {
        return () -> UUID.randomUUID().toString();
    }

    public String generate();

}
