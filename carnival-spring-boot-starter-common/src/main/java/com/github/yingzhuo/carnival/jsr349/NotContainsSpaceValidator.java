/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jsr349;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.stream.Stream;

/**
 * @author 应卓
 */
public class NotContainsSpaceValidator implements ConstraintValidator<NotContainsSpace, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        return toCharStream(value).noneMatch(ch -> ' ' == ch);
    }

    private Stream<Character> toCharStream(String string) {
        if (string == null) {
            return Stream.empty();
        }
        return string.chars().mapToObj(ch -> (char) ch);
    }

}
