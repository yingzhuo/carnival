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

import lombok.val;
import lombok.var;

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
    private Set<Character> specialChars;
    private int minLength;
    private int maxLength;

    @Override
    public void initialize(Password annotation) {
        this.complexity = annotation.complexity();
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();
        this.specialChars = CharBuffer.wrap(annotation.specialChars()).chars().mapToObj(ch -> (char) ch).collect(Collectors.toSet());
    }

    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (password == null) {
            return true;
        }

        final int len = password.length();
        if (len < minLength || len > maxLength) {
            return false;
        }

        if (complexity == Password.Complexity.ANY) {
            return true;
        }

        val chars = CharBuffer.wrap(password).chars().toArray();
        var hasNumeric = false;
        var hasAlphabetic = false;
        var hasUpper = false;
        var hasLower = false;
        var hasSpecial = false;

        for (int ch : chars) {

            if ('a' <= ch && ch <= 'z') {
                hasAlphabetic = true;
                hasLower = true;
            }

            if ('A' <= ch && ch <= 'Z') {
                hasAlphabetic = true;
                hasUpper = true;
            }

            if ('0' <= ch && ch <= '9') {
                hasNumeric = true;
            }

            if (specialChars.stream().anyMatch(i -> i == ch)) {
                hasSpecial = true;
            }
        }

        if (complexity == Password.Complexity.NUMERIC) {
            return hasNumeric;
        }

        if (complexity == Password.Complexity.ALPHABETIC) {
            return hasAlphabetic;
        }

        if (complexity == Password.Complexity.ALPHABETIC_AND_NUMERIC) {
            return hasAlphabetic && hasNumeric;
        }

        if (complexity == Password.Complexity.ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS) {
            return hasAlphabetic && hasNumeric && hasSpecial;
        }

        if (complexity == Password.Complexity.LOWER_AND_UPPER_AND_NUMERIC_AND_SPECIAL_CHARS) {
            return hasLower && hasUpper && hasNumeric && hasSpecial;
        }

        return true;
    }

}
