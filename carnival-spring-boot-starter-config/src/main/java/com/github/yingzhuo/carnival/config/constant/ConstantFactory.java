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

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.15
 */
public class ConstantFactory implements FactoryBean<Constant>, ApplicationContextAware {

    static ApplicationContext APPLICATION_CONTEXT;
    private final MapConstant constant;

    public ConstantFactory(Map<String, Map<String, Object>> map) {
        this.constant = new MapConstant(map);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ConstantFactory.APPLICATION_CONTEXT = applicationContext;
    }

    @Override
    public Constant getObject() {
        return constant;
    }

    @Override
    public Class<?> getObjectType() {
        return MapConstant.class;
    }

    // --------------------------------------------------------------------------------------------------------------

    static class MapConstant implements Constant {
        private final Map<String, Map<String, Object>> map;

        MapConstant(Map<String, Map<String, Object>> map) {
            this.map = map;
        }

        public Map<String, Map<String, Object>> getMap() {
            return map;
        }
    }

}
