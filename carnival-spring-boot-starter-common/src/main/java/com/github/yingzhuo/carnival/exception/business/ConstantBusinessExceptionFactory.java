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

import com.github.yingzhuo.carnival.constant.Constant;
import com.github.yingzhuo.carnival.constant.NoSuchConstantException;
import org.springframework.util.StringUtils;

import static com.github.yingzhuo.carnival.common.util.StringFormatter.format;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class ConstantBusinessExceptionFactory implements BusinessExceptionFactory {

    @Override
    public BusinessException create(String code, Object... args) {

        if (!StringUtils.hasText(code)) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }

        final String[] parts = code.split("\\.");

        if (parts.length == 2) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }

        final String group = parts[0];
        final String name = parts[1];

        try {
            final String messageTemplate = Constant.get(group, name);
            return new BusinessException(code, format(messageTemplate, args));
        } catch (NoSuchConstantException | ClassCastException e) {
            throw new IllegalArgumentException(format("'{}' is invalid code", code));
        }
    }

}
