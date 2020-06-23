/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shield.core;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.6.21
 */
final class DecryptionRequest extends HttpServletRequestWrapper {

    private byte[] requestBody;

    public DecryptionRequest(HttpServletRequest request) {
        super(request);
        try {
            requestBody = IOUtils.toByteArray(request.getInputStream());
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    @Override
    public ServletInputStream getInputStream() {
        final ByteArrayInputStream in = new ByteArrayInputStream(requestBody);
        return new ServletInputStream() {
            @Override
            public int read() {
                return in.read();
            }

            @Override
            public boolean isFinished() {
                return false;
            }

            @Override
            public boolean isReady() {
                return true;
            }

            @Override
            public void setReadListener(ReadListener listener) {
            }
        };
    }

    public String getRequestBodyAsString() {
        return new String(requestBody);
    }

    public void setRequestBody(byte[] requestBody) {
        this.requestBody = requestBody;
    }

}
