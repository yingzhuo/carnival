/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr380;

import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.val;

import javax.validation.Validator;

/**
 * @author 应卓
 * @since 1.10.34
 */
public final class ValidatorUtils {

    private ValidatorUtils() {
    }

    public static <T> ValidationResult<T> validate(T obj, Class<?>... groups) {
        val vs = SpringUtils.getBean(Validator.class).validate(obj, groups);
        return new ValidationResult<T>(vs);
    }

}
