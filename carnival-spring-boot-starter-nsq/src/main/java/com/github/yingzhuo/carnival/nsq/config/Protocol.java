/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.config;

/**
 * @author 应卓
 * @since 1.3.1
 */
public enum Protocol {

    HTTP {
        @Override
        public String toString() {
            return "http";
        }
    },

    HTTPS {
        @Override
        public String toString() {
            return "https";
        }
    };

}
