/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.refuse.impl;

import com.github.yingzhuo.carnival.refuse.RefuseContext;
import com.github.yingzhuo.carnival.refuse.RefuseListener;

/**
 * @author 应卓
 * @see com.github.yingzhuo.carnival.refuse.RefuseListener
 */
public class NopRefuseListener implements RefuseListener {

    @Override
    public void execute(RefuseContext refuseContext) {
        // 无动作
    }

}
