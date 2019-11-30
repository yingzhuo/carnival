/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.selector;

import com.github.yingzhuo.carnival.nsq.node.NsqdNode;

/**
 * @author 应卓
 * @since 1.3.1
 */
public class RandomNsqdNodeSelector extends AbstractRandomNodeSelector<NsqdNode> implements NsqdNodeSelector {
}