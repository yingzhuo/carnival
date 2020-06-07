/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.config.constant;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * @author 应卓
 * @since 1.6.15
 */
@SuppressWarnings("unchecked")
public interface Constant {

    public static final String DEFAULT_GROUP = "default";

    public static <T> T get(String group, String name) {
        T constant = get(group, name, null);
        if (constant == null) {
            throw new NoSuchConstantException();
        }
        return constant;
    }

    public static <T> T get(String group, String name, T defaultIfNull) {
        try {
            T obj = (T) ConstantFactory.APPLICATION_CONTEXT.getBean(ConstantFactory.MapConstant.class).getMap().get(group).get(name);
            return obj != null ? obj : defaultIfNull;
        } catch (NullPointerException | NoSuchBeanDefinitionException e) {
            return defaultIfNull;
        }
    }

}
