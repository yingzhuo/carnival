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

import com.github.yingzhuo.carnival.common.util.StringUtils;
import lombok.val;
import lombok.var;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Set;

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
        this.specialChars = StringUtils.toCharSet(annotation.specialChars());
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

        val chars = StringUtils.toCharSet(password);
        var hasNumeric = false;
        var hasAlphabetic = false;
        var hasUpper = false;
        var hasLower = false;
        var hasSpecial = false;

        int point = 0;

        for (int ch : chars) {

            if ('a' <= ch && ch <= 'z') {
                hasAlphabetic = true;
                hasLower = true;
                point += 1;
                continue;
            }

            if ('A' <= ch && ch <= 'Z') {
                hasAlphabetic = true;
                hasUpper = true;
                point += 1;
                continue;
            }

            if ('0' <= ch && ch <= '9') {
                hasNumeric = true;
                point += 1;
                continue;
            }

            if (specialChars.stream().anyMatch(i -> i == ch)) {
                hasSpecial = true;
                point += 1;
                continue;
            }
        }

        switch (complexity) {
            case NUMERIC:
                return hasNumeric;
            case ALPHABETIC:
                return hasAlphabetic;
            case ALPHABETIC_AND_NUMERIC:
                return hasAlphabetic && hasNumeric;
            case ALPHABETIC_AND_NUMERIC_AND_SPECIAL_CHARS:
                return hasAlphabetic && hasNumeric && hasSpecial;
            case LOWER_AND_UPPER_AND_NUMERIC:
                return hasLower && hasUpper && hasNumeric;
            case LOWER_AND_UPPER_AND_NUMERIC_AND_SPECIAL_CHARS:
                return hasLower && hasUpper && hasNumeric && hasSpecial;
            case AT_LEAST_TWO_KIND_OF_ALPHABETIC_AND__NUMERIC_AND_SPECIAL_CHARS:
                return point >= 2;
            default:
                return true;
        }
    }

}
