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
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.MessageSourceAccessor;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
public class BusinessException extends RuntimeException implements MessageSourceResolvable {

    private String code;
    private Object[] args;

    BusinessException(String code, String message, Object... args) {
        super(MessageFormatter.format(message, args));
        this.code = code;
        this.args = args;
    }

    public static BusinessException of(String code, Object... params) {
        return SpringUtils.getBean(BusinessExceptionFactory.class).create(code, params);
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

    @Override
    public String[] getCodes() {
        return new String[]{code};
    }

    @Override
    public Object[] getArguments() {
        return args;
    }

    @Override
    public String getDefaultMessage() {
        return super.getMessage();
    }

    @Override
    public String getLocalizedMessage() {
        return SpringUtils.getBean(MessageSourceAccessor.class).getMessage(this);
    }

}
