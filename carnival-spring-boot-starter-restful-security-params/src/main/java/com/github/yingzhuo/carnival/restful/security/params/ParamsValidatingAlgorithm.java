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

/**
 * @author 应卓
 * @since 1.6.30
 */
public interface ParamsValidatingAlgorithm {

    public static ParamsValidatingAlgorithm getDefault() {
        return new ParamsValidatingAlgorithmImpl();
    }

    public String encode(String parametersAsString);

    public boolean matches(String hashedParameters, String sign);

    public default boolean notMatches(String hashedParameters, String sign) {
        return !matches(hashedParameters, sign);
    }

}
