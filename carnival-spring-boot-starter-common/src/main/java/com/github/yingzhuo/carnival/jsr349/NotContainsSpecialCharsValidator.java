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

import com.github.yingzhuo.carnival.common.util.Strings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

/**
 * @author 应卓
 */
public class NotContainsSpecialCharsValidator implements ConstraintValidator<NotContainsSpecialChars, String> {

    private Set<Character> specialChars;

    @Override
    public void initialize(NotContainsSpecialChars annotation) {
        this.specialChars = Strings.toCharSet(annotation.specialChars());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return true;

        return specialChars.stream().noneMatch(ch -> value.contains(String.valueOf(ch)));
    }

}
