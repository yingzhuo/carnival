/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 * @since 1.4.6
 */
public final class BytesBuilder {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream(64);

    private BytesBuilder() {
    }

    public static BytesBuilder newInstance() {
        return new BytesBuilder();
    }

    public BytesBuilder append(Object object) {
        if (object == null) {
            throw new NullPointerException();
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(outputStream)) {
            oos.writeObject(object);
            oos.flush();
        } catch (IOException ex) {
            throw new UncheckedIOException("Failed to serialize object of type: " + object.getClass(), ex);
        }
        return this;
    }

    public byte[] build() {
        return outputStream.toByteArray();
    }

}
