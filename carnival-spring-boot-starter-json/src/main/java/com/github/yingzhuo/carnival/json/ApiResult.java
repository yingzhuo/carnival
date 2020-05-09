/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.json;

import com.fasterxml.jackson.annotation.JsonView;
import com.github.yingzhuo.carnival.common.StringCoded;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author 应卓
 * @since 1.0.4
 */
@JsonView(Views.Normal.class)
public interface ApiResult<T> extends Serializable, StringCoded {

    public static <T> ApiResult<T> newInstance(final T payload) {
        return newInstance(HttpStatus.OK, null, payload);
    }

    public static <T> ApiResult<T> newInstance(final HttpStatus httpStatus, final T payload) {
        return newInstance(String.valueOf(Objects.requireNonNull(httpStatus).value()), null, payload);
    }

    public static <T> ApiResult<T> newInstance(final String code, final T payload) {
        return newInstance(code, null, payload);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static <T> ApiResult<T> newInstance(final HttpStatus httpStatus, final String errorMessage, final T payload) {
        return newInstance(String.valueOf(Objects.requireNonNull(httpStatus).value()), errorMessage, payload);
    }

    public static <T> ApiResult<T> newInstance(final String code, final String errorMessage, final T payload) {
        Objects.requireNonNull(code);

        return new ApiResult<T>() {
            @Override
            public String getCode() {
                return code;
            }

            @Override
            public String getErrorMessage() {
                return errorMessage;
            }

            @Override
            public T getPayload() {
                return payload;
            }
        };
    }

    public String getCode();

    public String getErrorMessage();

    public T getPayload();

}
