/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.yingzhuo.carnival.nsq.exception.NsqdException;
import com.github.yingzhuo.carnival.nsq.exception.NsqdResourceNotFoundException;
import lombok.var;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 */
abstract class AbstractClient {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    protected void checkResponse(ResponseEntity<String> response) {
        final int code = response.getStatusCodeValue();
        String msg = response.getBody();
        msg = getInnerMessage(msg, response);

        if (code == 404) {
            throw new NsqdResourceNotFoundException(msg);
        }

        if (code < 200 || code >= 300) {
            throw new NsqdException(msg);
        }
    }

    @SuppressWarnings("unchecked")
    protected String getInnerMessage(String rawMsg, ResponseEntity<String> response) {
        MediaType contentType = response.getHeaders().getContentType();
        if (contentType != null && contentType.isCompatibleWith(MediaType.parseMediaType("application/json"))) {

            Map<String, Object> map;

            try {
                map = OBJECT_MAPPER.readValue(rawMsg, Map.class);
            } catch (JsonProcessingException e) {
                map = new HashMap<>();
            }

            var m = map.get("message");
            if (m != null) {
                return m.toString();
            }

            m = map.get("msg");
            if (m != null) {
                return m.toString();
            }
        }

        return rawMsg;
    }

}
