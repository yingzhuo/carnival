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
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
public class NotContainsSpecialCharsValidator implements ConstraintValidator<NotContainsSpecialChars, String> {

    private Set<Character> specialChars;

    @Override
    public void initialize(NotContainsSpecialChars annotation) {
        this.specialChars = CharBuffer.wrap(annotation.specialChars()).chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return specialChars.stream().noneMatch(ch -> value.contains(String.valueOf(ch)));
    }

}
