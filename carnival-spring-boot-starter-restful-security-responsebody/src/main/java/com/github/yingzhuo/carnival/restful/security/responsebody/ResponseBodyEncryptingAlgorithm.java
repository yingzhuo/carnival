/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody;

import com.github.yingzhuo.carnival.restful.security.responsebody.algorithm.Base64ResponseBodyEncryptingAlgorithm;

import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.6.30
 */
@FunctionalInterface
public interface ResponseBodyEncryptingAlgorithm {

    public static ResponseBodyEncryptingAlgorithm getDefault() {
        return new Base64ResponseBodyEncryptingAlgorithm(1, StandardCharsets.UTF_8);
    }

    public String encrypt(String body);

}
