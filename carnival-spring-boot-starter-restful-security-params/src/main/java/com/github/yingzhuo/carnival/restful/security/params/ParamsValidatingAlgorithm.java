/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.params;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 应卓
 * @since 1.6.30
 */
public interface ParamsValidatingAlgorithm {

    public static ParamsValidatingAlgorithm getDefault() {
        return new ParamsValidatingAlgorithm() {
        };
    }

    public default String encode(String parametersAsString) {
        return DigestUtils.sha256Hex(DigestUtils.md5Hex(parametersAsString));
    }

    public default boolean matches(String hashedParameters, String sign) {
        return StringUtils.equals(hashedParameters, sign);
    }

}
