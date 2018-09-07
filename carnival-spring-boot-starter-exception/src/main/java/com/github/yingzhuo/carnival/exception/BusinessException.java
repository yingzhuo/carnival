/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.exception;

import com.github.yingzhuo.carnival.common.StringCoded;
import com.github.yingzhuo.carnival.spring.SpringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public class BusinessException extends RuntimeException implements StringCoded {

    private static final long serialVersionUID = 9128655481919572535L;
    private String code;

    public static BusinessException of(String code) {
        return SpringUtils.getBean(BusinessExceptionFactory.class).create(code);
    }

    public BusinessException() {
        super();
    }

    public BusinessException(String code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

    @Override
    public String getCode() {
        return this.code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Map<String, Object> asMap() {
        return asMap(false);
    }

    public Map<String, Object> asMap(boolean includeType) {
        Map<String, Object> map = new HashMap<>();

        if (includeType) {
            map.put("type", BusinessException.class.getName());
        }

        map.put("code", getCode());
        map.put("message", getMessage());
        return Collections.unmodifiableMap(map);
    }

}
