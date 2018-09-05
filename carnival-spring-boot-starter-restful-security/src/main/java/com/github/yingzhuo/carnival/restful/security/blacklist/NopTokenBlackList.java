/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.blacklist;

import com.github.yingzhuo.carnival.restful.security.token.Token;

/**
 * @author 应卓
 */
public class NopTokenBlackList implements TokenBlackList {

    @Override
    public boolean isBlacklisted(Token token) {
        return false;
    }

    @Override
    public void blacklist(Token token) {
        // NOP
    }

}
