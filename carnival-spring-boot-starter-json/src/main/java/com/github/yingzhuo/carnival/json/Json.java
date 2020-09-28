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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.http.HttpStatus;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author 应卓
 */
@JsonIgnoreProperties("empty")
@JsonView(Views.Normal.class)
@Deprecated
public class Json implements ApiResult<Payload> {

    private String code = String.valueOf(HttpStatus.OK.value());
    private String errorMessage = null;
    private String warnningMessage = null;
    private boolean deprecated = false;
    private Payload payload = new Payload();

    public Json() {
    }

    public static Json newInstance() {
        return new Json();
    }

    public Json code(String code) {
        this.code = Objects.requireNonNull(code);
        return this;
    }

    public Json code(HttpStatus httpStatus) {
        return code(String.valueOf(Objects.requireNonNull(httpStatus).value()));
    }

    public Json errorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
        return this;
    }

    public Json errorMessage(Supplier<String> supplier) {
        return errorMessage(Objects.requireNonNull(supplier).get());
    }

    public Json payload(String key, Object value) {
        payload.put(key, value);
        return this;
    }

    @Deprecated
    public Json deprecated(boolean deprecated) {
        this.deprecated = deprecated;
        return this;
    }

    public Json deprecated() {
        this.deprecated = true;
        return this;
    }

    public Json warnningMessage(String warnningMessage) {
        this.warnningMessage = warnningMessage;
        return this;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isDeprecated() {
        return deprecated;
    }

    public String getWarnningMessage() {
        return warnningMessage;
    }

    @Override
    public Payload getPayload() {
        return payload;
    }

    public int size() {
        return payload.size();
    }

    public boolean isEmpty() {
        return payload.isEmpty();
    }

}
