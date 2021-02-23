/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.props.FeignProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * @author 应卓
 * @since 1.6.17
 */
@EnableConfigurationProperties(FeignProperties.class)
public class FeignBeanAutoConfig {
}
