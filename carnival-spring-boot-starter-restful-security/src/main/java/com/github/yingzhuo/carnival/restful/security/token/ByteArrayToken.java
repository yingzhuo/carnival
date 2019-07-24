/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.token;

import java.util.Arrays;

/**
 * @author 应卓
 */
public class ByteArrayToken implements Token {

    private final byte[] data;

    public ByteArrayToken(byte[] data) {
        if (data != null) {
            this.data = data;
        } else {
            this.data = new byte[0];
        }
    }

    @Override
    public String asString() {
        return javax.xml.bind.DatatypeConverter.printHexBinary(data);
    }

    public byte[] getData() {
        return data;
    }

    public long size() {
        return data.length;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ByteArrayToken that = (ByteArrayToken) o;
        return Arrays.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(data);
    }

}
