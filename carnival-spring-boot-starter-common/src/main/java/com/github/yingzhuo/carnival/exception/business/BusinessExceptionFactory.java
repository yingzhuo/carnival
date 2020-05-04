/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

/**
 * @author 应卓
 */
@FunctionalInterface
public interface BusinessExceptionFactory {

    public static BusinessExceptionFactory newEmptyFactory() {
        return (code, params) -> {
            throw new UnsupportedOperationException("BusinessExceptionFactory instance is NOT configured.");
        };
    }

    public BusinessException create(String code, Object... params);

}
