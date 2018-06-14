/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.validation.jsr310;

import com.github.yingzhuo.carnival.validation.jsr310.password.Complexity;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.nio.CharBuffer;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Complexity complexity;
    private Set<String> specialChars;
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(Password annotation) {
        this.complexity = annotation.complexity();
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();
        this.specialChars = CharBuffer.wrap(annotation.specialChars()).chars().mapToObj(ch -> String.valueOf((char) ch)).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {
        final int len = password.length();
        if (len < minLength || len > maxLength) return false;

        switch (complexity) {
            case NONE: {
                return true;
            }

            case NUMERIC: {
                return password.matches(".*[0-9]+.*");
            }

            case ALPHABETIC: {
                return password.matches(".*[a-zA-Z]+.*");
            }

            case ALPHABETIC_AND_NUMERIC: {
                return password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*");
            }

            case ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS: {
                return password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*") && specialChars.stream().anyMatch(password::contains);
            }
        }

        return true;
    }

}
