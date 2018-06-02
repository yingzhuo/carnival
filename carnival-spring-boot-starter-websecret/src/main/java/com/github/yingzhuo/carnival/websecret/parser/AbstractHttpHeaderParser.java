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
public abstract class AbstractHttpHeaderParser {

    private String headerName;

    public String parse(NativeWebRequest request) {
        String value = request.getHeader(headerName);
        return "".equals(value) ? null : value;
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

}
