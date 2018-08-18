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
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private Password.Complexity complexity;
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
        if (password == null) return true;

        final int len = password.length();
        if (len < minLength || len > maxLength) return false;

        if (complexity == Password.Complexity.ANY) {
            return true;
        }

        if (complexity == Password.Complexity.NUMERIC) {
            return password.matches(".*[0-9]+.*");
        }

        if (complexity == Password.Complexity.ALPHABETIC) {
            return password.matches(".*[a-zA-Z]+.*");
        }

        if (complexity == Password.Complexity.ALPHABETIC_AND_NUMERIC) {
            return password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*");
        }

        if (complexity == Password.Complexity.ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS) {
            return password.matches(".*[a-zA-Z]+.*") && password.matches(".*[0-9]+.*") && specialChars.stream().anyMatch(password::contains);
        }

        if (complexity == Password.Complexity.LOWER_AND_UPPER_AND_NUMERIC_AND_SPECIAL_CHARS) {
            return password.matches(".*[a-z]+.*") &&
                    password.matches(".*[A-Z]+.*") &&
                    password.matches(".*[0-9]+.*") &&
                    specialChars.stream().anyMatch(password::contains);
        }

        return true;
    }

}
