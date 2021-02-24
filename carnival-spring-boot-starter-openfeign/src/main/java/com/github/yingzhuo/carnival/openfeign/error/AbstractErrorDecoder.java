/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.error;

import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.extern.slf4j.Slf4j;

import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.20
 */
@Slf4j
public abstract class AbstractErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder DEFAULT = new ErrorDecoder.Default();
    private final Charset bodyCharset;

    public AbstractErrorDecoder() {
        this(null);
    }

    public AbstractErrorDecoder(Charset bodyCharset) {
        this.bodyCharset = bodyCharset != null ? bodyCharset : StandardCharsets.UTF_8;
    }

    @Override
    public final Exception decode(String methodKey, Response response) {
        try {
            final int status = response.status();
            final Reader body = response.body().asReader(bodyCharset);
            final Map<String, Collection<String>> headers = response.headers();

            try {
                return decode(methodKey, status, body, headers)
                        .orElseGet(() -> DEFAULT.decode(methodKey, response));

            } finally {
                close(body);
                close(response);
            }

        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return DEFAULT.decode(methodKey, response);
        }
    }

    private void close(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }

    protected abstract Optional<Exception> decode(
            String methodKey,
            int status,
            Reader body,
            Map<String, Collection<String>> headers
    ) throws IOException;

}
