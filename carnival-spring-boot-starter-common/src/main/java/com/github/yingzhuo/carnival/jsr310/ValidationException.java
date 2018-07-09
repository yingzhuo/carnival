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

import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;

import java.util.Objects;

/**
 * @author 应卓
 */
public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = 6980428941250064462L;

    public static void raiseIfNecessary(BindingResult bindingResult) {
        raiseIfNecessary((Errors) bindingResult);
    }

    public static void raiseIfNecessary(Errors errors) {
        if (errors.hasErrors()) {
            throw from(errors);
        }
    }

    public static ValidationException from(BindingResult bindingResult) {
        return from((Errors) bindingResult);
    }

    public static ValidationException from(Errors errors) {
        Objects.requireNonNull(errors);

        if (!errors.hasErrors()) {
            throw new IllegalArgumentException("BindingResult has NO errors!");
        }
        return new ValidationException(errors);
    }

    private final Errors bindingResult;

    private ValidationException(Errors errors) {
        this.bindingResult = errors;
    }

    public Errors getBindingResult() {
        return bindingResult;
    }

}
