/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception.business;

import com.github.yingzhuo.carnival.common.util.MessageFormatter;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public class BusinessException extends RuntimeException {

    private String code;

    public BusinessException(String code, String message, Object... args) {
        super(MessageFormatter.format(message, args));
        this.code = code;
    }

    public static BusinessException of(String code, Object... args) {
        return SpringUtils.getBean(BusinessExceptionFactory.class).create(code, args);
    }

    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> asMap() {
        return asMap(true);
    }

    public Map<String, Object> asMap(boolean includeType) {
        final Map<String, Object> map = new HashMap<>();

        if (includeType) {
            map.put("type", getClass().getName());
        }

        map.put("code", getCode());
        map.put("message", getMessage());
        return Collections.unmodifiableMap(map);
    }

}
