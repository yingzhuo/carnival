/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator;

import org.apache.curator.framework.CuratorFrameworkFactory;

/**
 * @author 应卓
 * @since 1.3.0
 */
public interface CuratorFrameworkFactoryBuilderConfigurer {

    public void config(CuratorFrameworkFactory.Builder builder);

}
