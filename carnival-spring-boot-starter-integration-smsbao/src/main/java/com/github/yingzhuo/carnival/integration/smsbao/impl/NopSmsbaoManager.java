/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.integration.smsbao.impl;

import com.github.yingzhuo.carnival.integration.smsbao.SmsbaoManager;

/**
 * @author 应卓
 */
public final class NopSmsbaoManager implements SmsbaoManager {

    @Override
    public boolean send(String phoneNumber, String content) {
        return true;
    }

}
