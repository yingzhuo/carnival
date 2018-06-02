/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.websecret.parser;

import org.springframework.web.context.request.NativeWebRequest;

/**
 * @author 应卓
 */
public interface AppIdParser {

    public static final AppIdParser DEFAULT = new DefaultAppIdParser();

    public String parse(NativeWebRequest nativeWebRequest);

    public static class DefaultAppIdParser extends AbstractHttpHeaderParser implements AppIdParser {
        public DefaultAppIdParser() {
            super.setHeaderName("App-Id");
        }
    }

}
