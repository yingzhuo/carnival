/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr310;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.CharBuffer;

/**
 * @author 应卓
 */
public class NotContainsWhitespaceValidator implements ConstraintValidator<NotContainsWhitespace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return CharBuffer.wrap(value).chars().mapToObj(ch -> (char) ch).noneMatch((Character::isWhitespace));
    }

}
