/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.model;

import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author 应卓
 */
public interface JsonResult extends java.io.Serializable {

    public static JsonResult of(HttpStatus httpStatus) {
        return of(Integer.toString(httpStatus.value()));
    }

    public static JsonResult of(String code) {
        return of(code, null, null);
    }

    public static JsonResult of(HttpStatus httpStatus, Map<String, Object> payload) {
        return of(Integer.toString(httpStatus.value()), payload);
    }

    public static JsonResult of(String code, Map<String, Object> payload) {
        return of(code, null, payload);
    }

    public static JsonResult of(HttpStatus httpStatus, String... errorMessages) {
        return of(Integer.toString(httpStatus.value()), errorMessages);
    }

    public static JsonResult of(String code, String... errorMessages) {
        return of(code, Arrays.asList(errorMessages), null);
    }

    public static JsonResult of(HttpStatus httpStatus, List<String> errorMessages, Map<String, Object> payload) {
        return of(Integer.toString(httpStatus.value()), errorMessages, payload);
    }

    public static JsonResult of(String code, List<String> errorMessages, Map<String, Object> payload) {
        if (code == null) {
            throw new NullPointerException();
        }

        if (errorMessages == null) {
            errorMessages = Collections.emptyList();
        }

        if (payload == null) {
            payload = Collections.emptyMap();
        }

        SimpleJsonResult json = new SimpleJsonResult();
        json.setCode(code);
        json.setErrorMessages(Collections.unmodifiableList(errorMessages));
        json.setPayload(Collections.unmodifiableMap(payload));
        return json;
    }

    public String getCode();

    public List<String> getErrorMessages();

    public Map<String, Object> getPayload();

    public static final class SimpleJsonResult implements JsonResult {

        private static final long serialVersionUID = 3048496136385946658L;
        private String code;
        private List<String> errorMessages;
        private Map<String, Object> payload;

        @Override
        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        @Override
        public List<String> getErrorMessages() {
            return errorMessages;
        }

        public void setErrorMessages(List<String> errorMessages) {
            this.errorMessages = errorMessages;
        }

        @Override
        public Map<String, Object> getPayload() {
            return payload;
        }

        public void setPayload(Map<String, Object> payload) {
            this.payload = payload;
        }
    }
}
