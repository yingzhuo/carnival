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
 * @since 1.6.4
 */
public class EmptyBusinessExceptionFactory implements BusinessExceptionFactory {

    @Override
    public BusinessException create(String code, Object... params) {
        throw new UnsupportedOperationException("'" + code + "' is not a valid code");
    }

}
