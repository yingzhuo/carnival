/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.constant;

import org.springframework.beans.factory.FactoryBean;

import java.util.Map;

/**
 * @author 应卓
 * @since 1.6.12
 */
public class ConstantFactory implements FactoryBean<Constant> {

    private final Constant constant;

    public ConstantFactory(Map<String, Map<String, Object>> map) {
        this.constant = new MapConstant(map);
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
