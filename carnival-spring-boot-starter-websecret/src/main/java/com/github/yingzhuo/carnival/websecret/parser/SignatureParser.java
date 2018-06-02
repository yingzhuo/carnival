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
public interface SignatureParser {

    public static final SignatureParser DEFAULT = new DefaultSignatureParser();

    public String parse(NativeWebRequest request);

    public static class DefaultSignatureParser extends AbstractHttpHeaderParser implements SignatureParser {
        public DefaultSignatureParser() {
            super.setHeaderName("Signature");
        }
    }
}
