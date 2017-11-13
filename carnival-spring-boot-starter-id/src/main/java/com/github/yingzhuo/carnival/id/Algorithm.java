/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id;

import java.util.UUID;

/**
 * @author 应卓
 */
public enum Algorithm implements StringIdGenerator {

    UUID32 {
        @Override
        public String nextId() {
            return UUID.randomUUID().toString().replaceAll("-", "");
        }
    },

    UUID36 {
        @Override
        public String nextId() {
            return UUID.randomUUID().toString();
        }
    }

}
