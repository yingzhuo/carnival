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

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.TreeMap;

/**
 * @author 应卓
 * @since 1.6.13
 */
@ConfigurationProperties(prefix = "business.exception")
public class BusinessExceptionMap extends TreeMap<String, String> {

    // 从 conf/toml/yaml/properties 映射到此类
    // 而本类自身就是工厂的实现

}
