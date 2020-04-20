/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.autoconfig;

import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author 应卓
 * @since 1.5.1
 */
@ComponentScan(
        basePackages = {
                "com.github.yingzhuo.carnival"
        })
@ConfigurationPropertiesScan(
        basePackages = {
                "com.github.yingzhuo.carnival"
        }
)
public class ScanAutoConfig {
}
