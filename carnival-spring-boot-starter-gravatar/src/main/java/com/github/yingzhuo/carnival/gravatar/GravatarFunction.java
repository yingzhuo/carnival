/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.gravatar;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author 应卓
 */
public class GravatarFunction implements GravatarFactory {

    public GravatarFunction() {
        super();
    }

    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";
    private final static String FILE_TYPE_EXTENSION = ".jpg";

    private DefaultImage defaultImage = DefaultImage.IDENTICON;
    private Rating rating = Rating.GENERAL_AUDIENCE;
    private String prefix = GRAVATAR_URL;
    private String suffix = FILE_TYPE_EXTENSION;

    @Override
    public String create(final String email) {
        return create(email, 120);
    }

    @Override
    public String create(final String email, int size) {
        validate(email);
        return prefix + md5(email.toLowerCase()) + suffix +
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

    public void setDefaultImage(DefaultImage defaultImage) {
        this.defaultImage = defaultImage;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }
}
