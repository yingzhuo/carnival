/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.gravatar.impl;

import com.github.yingzhuo.carnival.gravatar.DefaultImage;
import com.github.yingzhuo.carnival.gravatar.GravatarFactory;
import com.github.yingzhuo.carnival.gravatar.Rating;
import com.github.yingzhuo.carnival.gravatar.Type;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 应卓
 */
public class DefaultGravatarFactory implements GravatarFactory {

    private DefaultImage defaultImage;
    private Rating rating;
    private Type type;

    public DefaultGravatarFactory(DefaultImage defaultImage, Rating rating, Type type) {
        this.defaultImage = defaultImage;
        this.rating = rating;
        this.type = type;
    }

    @Override
    public String create(String email, int size) {
        validate(email);
        return type.getPrefix() + md5(email.toLowerCase()) +
                "?s=" + size +
                "&r=" + rating.getKey() +
                "&d=" + defaultImage.getKey();
    }

    private void validate(String email) {
        if (email == null) {
            throw new IllegalArgumentException("'email' should NOT be null.");
        }
        int length = email.trim().length();
        if (length == 0) {
            throw new IllegalArgumentException("'email' should NOT be blank.");
        }
    }

    private String md5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
            // 不会抛出此异常
        }
        md.update(string.getBytes());
        byte byteData[] = md.digest();
        StringBuilder sb = new StringBuilder();
        for (byte aByteData : byteData) {
            sb.append(Integer.toString((aByteData & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
