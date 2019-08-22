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
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    @Override
    public String toString() {
        return "ByteArrayToken{" +
                "data=" + Arrays.toString(data) +
                '}';
    }

}
