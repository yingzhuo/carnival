/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.spring;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.net.URL;

/**
 * @author 应卓
 * @see SpringUtils
 */
public final class JacksonUtils {

    public static final ObjectMapper DEFAULT_OBJECT_MAPPER;

    static {
        DEFAULT_OBJECT_MAPPER = new ObjectMapper()

                // 序列化
                .configure(SerializationFeature.INDENT_OUTPUT, false)
                .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                //.configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true)
                //.configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true)
                .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)

                // 反序列化
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtils() {
    }

    public static String writeValueAsString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, File dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, Writer dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, OutputStream dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, DataOutput dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(File src, Class<T> type) {
        try {
            return getObjectMapper().readValue(src, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static <T> T readValue(InputStream src, Class<T> type) {
        try {
            return getObjectMapper().readValue(src, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(Reader src, Class<T> type) {
        try {
            return getObjectMapper().readValue(src, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(String src, Class<T> type) {
        try {
            return getObjectMapper().readValue(src, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(URL src, Class<T> type) {
        try {
            return getObjectMapper().readValue(src, type);
        } catch (IOException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

    private static ObjectMapper getObjectMapper() {
        return SpringUtils.getBean(ObjectMapper.class, DEFAULT_OBJECT_MAPPER);
    }

    // -----------------------------------------------------------------------------------------------------------------

    public byte[] writeValueAsBytes(Object value) {
        try {
            return getObjectMapper().writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new UncheckedIOException(e.getMessage(), e);
        }
    }

}
