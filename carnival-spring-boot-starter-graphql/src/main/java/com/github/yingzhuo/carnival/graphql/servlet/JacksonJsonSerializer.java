/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 */
public class JacksonJsonSerializer implements JsonSerializer {

    private final ObjectMapper objectMapper;

    public JacksonJsonSerializer() {
        this(null);
    }

    public JacksonJsonSerializer(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper != null ? objectMapper : new ObjectMapper();
    }

    @Override
    public String serialize(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing object to JSON: " + e.getMessage(), e);
        }
    }

    @Override
    public <T> T deserialize(String json, Class<T> requiredType) {
        try {
            return objectMapper.readValue(json, requiredType);
        } catch (IOException e) {
            throw new UncheckedIOException("Error deserializing object from JSON: " + e.getMessage(), e);
        }
    }

}
