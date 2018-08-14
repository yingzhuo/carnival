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

import com.github.yingzhuo.carnival.common.parser.Parser;
import lombok.val;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.WebRequest;

import java.util.Locale;
import java.util.Optional;

/**
 * @author 应卓
 */
public class AbstractHttpHeaderParser implements Parser<WebRequest, String> {

    private String headerName;

    public String parse(NativeWebRequest request) {
        val value = request.getHeader(headerName);
        return "".equals(value) ? null : value;
    }

    @Override
    public Optional<String> parse(WebRequest request, Locale locale) {
        val value = request.getHeader(headerName);
        return Optional.ofNullable("".equals(value) ? null : value);
    }

    public void setHeaderName(String headerName) {
        this.headerName = headerName;
    }

}
