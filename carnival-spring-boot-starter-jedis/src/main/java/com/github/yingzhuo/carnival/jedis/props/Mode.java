/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.jedis.props;

/**
 * @author 应卓
 * @since 1.6.7
 */
public enum Mode {

    /**
     * 单节点模式
     */
    SINGLE,

    /**
     * 哨兵模式
     */
    SENTINEL,

    /**
     * 集群模式
     */
    CLUSTER

}
