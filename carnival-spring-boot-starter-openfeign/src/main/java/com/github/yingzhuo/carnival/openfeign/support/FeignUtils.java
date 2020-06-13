/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.support;

import org.springframework.http.HttpHeaders;

import java.util.*;

import static java.util.Optional.ofNullable;

/**
 * 内部工具
 */
final class FeignUtils {

    static HttpHeaders getHttpHeaders(Map<String, Collection<String>> headers) {
        HttpHeaders httpHeaders = new HttpHeaders();
        for (Map.Entry<String, Collection<String>> entry : headers.entrySet()) {
            httpHeaders.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }
        return httpHeaders;
    }

    static Map<String, Collection<String>> getHeaders(HttpHeaders httpHeaders) {
        LinkedHashMap<String, Collection<String>> headers = new LinkedHashMap<>();

        for (Map.Entry<String, List<String>> entry : httpHeaders.entrySet()) {
            headers.put(entry.getKey(), entry.getValue());
        }

        return headers;
    }

    static Collection<String> addTemplateParameter(Collection<String> possiblyNull,
                                                   String paramName) {
        Collection<String> params = ofNullable(possiblyNull).map(ArrayList::new)
                .orElse(new ArrayList<>());
        params.add(String.format("{%s}", paramName));
        return params;
    }

}
