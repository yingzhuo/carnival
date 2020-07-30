/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.responsebody.algorithm;

import com.github.yingzhuo.carnival.restful.security.responsebody.ResponseBodyEncryptingAlgorithm;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * @author 应卓
 * @since 1.6.30
 */
public class Base64ResponseBodyEncryptingAlgorithm implements ResponseBodyEncryptingAlgorithm {

    private final int repeat;
    private final Charset charset;

    public Base64ResponseBodyEncryptingAlgorithm(int repeat) {
        this(repeat, StandardCharsets.UTF_8);
    }

    public Base64ResponseBodyEncryptingAlgorithm(int repeat, Charset charset) {
        if (repeat < 1) {
            repeat = 1;
        }
        this.repeat = repeat;
        this.charset = charset;
    }

    @Override
    public String encrypt(String body) {
        String s = body;
        for (int i = 0; i < repeat; i++) {
            s = base64(s);
        }
        return s;
    }

    private String base64(String s) {
        return Base64.getUrlEncoder().encodeToString(s.getBytes(charset));
    }

}
