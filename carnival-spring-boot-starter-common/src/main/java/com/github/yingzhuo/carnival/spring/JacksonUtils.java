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
import org.springframework.core.io.Resource;

import java.io.*;
import java.net.URL;

/**
 * @author 应卓
 */
public final class JacksonUtils {

    private static final ObjectMapper DEFAULT_OBJECT_MAPPER;

    static {
        DEFAULT_OBJECT_MAPPER = new ObjectMapper()

                // 序列化
                .configure(SerializationFeature.INDENT_OUTPUT, false)
                .configure(SerializationFeature.FAIL_ON_SELF_REFERENCES, true)
                .configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
                .configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false)
                .configure(SerializationFeature.WRITE_NULL_MAP_VALUES, true)
                .configure(SerializationFeature.WRITE_EMPTY_JSON_ARRAYS, true)
                .configure(SerializationFeature.WRITE_SINGLE_ELEM_ARRAYS_UNWRAPPED, false)
                .configure(SerializationFeature.WRITE_ENUMS_USING_TO_STRING, true)

                // 反序列化
                .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtils() {
        super();
    }

    public static String writeValueAsString(Object value) {
        try {
            return getObjectMapper().writeValueAsString(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public byte[] writeValueAsBytes(Object value) {
        try {
            return getObjectMapper().writeValueAsBytes(value);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, File dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, Writer dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, OutputStream dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static void writeValue(Object value, DataOutput dest) {
        try {
            getObjectMapper().writeValue(dest, value);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    public static <T> T readValue(File src, Class<T> type) {
        try {
            return SpringUtils.getBean(ObjectMapper.class).readValue(src, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(InputStream src, Class<T> type) {
        try {
            return SpringUtils.getBean(ObjectMapper.class).readValue(src, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(Reader src, Class<T> type) {
        try {
            return SpringUtils.getBean(ObjectMapper.class).readValue(src, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(String src, Class<T> type) {
        try {
            return SpringUtils.getBean(ObjectMapper.class).readValue(src, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T readValue(URL src, Class<T> type) {
        try {
            return SpringUtils.getBean(ObjectMapper.class).readValue(src, type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        }
    }

    public static <T> T readResource(String location, Class<T> type) {
        Resource resource = null;
        try {
            resource = SpringUtils.getResourceLoader().getResource(location);
            return readValue(resource.getInputStream(), type);
        } catch (IOException e) {
            throw new IllegalArgumentException(e.getMessage(), e);
        } finally {
            if (resource != null) {
                try {
                    resource.getInputStream().close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // -----------------------------------------------------------------------------------------------------------------

    private static ObjectMapper getObjectMapper() {
        try {
            ObjectMapper om = SpringUtils.getBean(ObjectMapper.class);
            return om != null ? om : DEFAULT_OBJECT_MAPPER;
        } catch (Exception e) {
            return DEFAULT_OBJECT_MAPPER;
        }
    }

}
