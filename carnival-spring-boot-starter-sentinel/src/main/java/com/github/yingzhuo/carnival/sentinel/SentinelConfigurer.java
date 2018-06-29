/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.sentinel;

import com.github.yingzhuo.carnival.sentinel.config.SentinelConfig;

import java.util.List;

/**
 * @author 应卓
 */
public interface SentinelConfigurer {

    public void addSentinelConfigs(List<SentinelConfig> configs);

}
