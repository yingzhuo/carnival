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

import org.apache.commons.beanutils.BeanMap;
import org.springframework.http.HttpStatus;

import java.io.Serializable;
import java.util.Map;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * @author 应卓
 */
@SuppressWarnings("unchecked")
public class Json implements Serializable {

    public static Json newInstance() {
        return new Json();
    }

    private String code = String.valueOf(HttpStatus.OK.value());
    private String errorMessage = null;
    private Payload payload = Payload.newInstance();

    private Json() {
        super();
    }

    public Json code(String code) {
        this.code = Objects.requireNonNull(code);
        return this;
    }

    public Json code(Supplier<String> supplier) {
        return code(Objects.requireNonNull(supplier).get());
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
        if (value.getClass() == boolean.class) {
            boolean b = (boolean) value;
            value = Boolean.valueOf(b);
        }

        payload.put(key, value);
        return this;
    }

    public Json payload(Map<String, ?> map) {
        payload.putAll(map);
        return this;
    }

    public Json payload(Object vo, boolean includeClassProperty) {
        payload((Map) new BeanMap(vo));

        if (!includeClassProperty) {
            payload.remove("class");
        }

        return this;
    }

    public Json payload(Object vo) {
        return payload(vo, false);
    }

    public Json clear() {
        payload.clear();
        return this;
    }

    public String getCode() {
        return code;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public Payload getPayload() {
        return payload;
    }

    public int size() {
        return payload.size();
    }

}
