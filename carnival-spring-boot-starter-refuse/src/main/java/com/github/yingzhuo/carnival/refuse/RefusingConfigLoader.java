/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse;

import com.github.yingzhuo.carnival.refuse.impl.AlawaysEmptyRefusingConfigLoader;

/**
 * @author 应卓
 * @see AlawaysEmptyRefusingConfigLoader
 */
public interface RefusingConfigLoader {

    public RefusingConfig load();

}
