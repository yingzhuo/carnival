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
import org.apache.commons.beanutils.BeanMap;
import org.springframework.http.HttpStatus;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;

/**
 * @author 应卓
 */
@JsonIgnoreProperties("empty")
public class Json implements ApiResult<Payload> {

    public static Json newInstance() {
        return new Json();
    }

    private String code = String.valueOf(HttpStatus.OK.value());
    private String errorMessage = null;
    private Payload payload = new Payload();

    public Json() {
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

    public Json setPayload(Map<String, Object> map) {
        Objects.requireNonNull(map);
        payload.clear();
        payload.putAll(map);
        return this;
    }

    public Json addPayload(Map<String, Object> map) {
        Objects.requireNonNull(map);
        payload.putAll(map);
        return this;
    }

    public Json setBeanToPayload(Object bean) {
        Objects.requireNonNull(bean);
        payload.clear();
        payload.putAll(new BeanMap(bean));
        payload.remove("class");
        return this;
    }

    public Json addBeanToPayload(Object bean) {
        Objects.requireNonNull(bean);
        payload.putAll(new BeanMap(bean));
        payload.remove("class");
        return this;
    }

    public Json clear() {
        payload.clear();
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

    public Set<Object> payloadKeySet() {
        return payload.keySet();
    }

}
