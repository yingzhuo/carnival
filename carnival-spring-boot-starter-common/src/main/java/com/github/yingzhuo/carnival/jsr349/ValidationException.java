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

import org.springframework.validation.Errors;

import java.util.Objects;

/**
 * @author 应卓
 */
public class ValidationException extends RuntimeException {

    private final Errors errors;

    private ValidationException(Errors errors) {
        this.errors = errors;
    }

    public static ValidationException create() {
        return new ValidationException(null);
    }

    public static void raiseIfNecessary(Errors errors) {
        if (errors.hasErrors()) {
            throw from(errors);
        }
    }

    public static ValidationException from(Errors errors) {
        if (!Objects.requireNonNull(errors).hasErrors()) {
            throw new IllegalArgumentException();
        }
        return new ValidationException(errors);
    }

    public Errors getErrors() {
        return errors;
    }

}
