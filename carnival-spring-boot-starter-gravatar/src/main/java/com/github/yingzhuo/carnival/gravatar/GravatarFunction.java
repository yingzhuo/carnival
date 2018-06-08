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

public class GravatarFunction {

    public static GravatarFunction create() {
        return new GravatarFunction();
    }

    public GravatarFunction() {
        super();
    }

    public GravatarFunction(DefaultImage defaultImage, Rating rating) {
        this.defaultImage = defaultImage;
        this.rating = rating;
    }

    private final static String GRAVATAR_URL = "http://www.gravatar.com/avatar/";
    private final static String FILE_TYPE_EXTENSION = ".jpg";

    private DefaultImage defaultImage = DefaultImage.IDENTICON;
    private Rating rating = Rating.GENERAL_AUDIENCE;

    /**
     * create avatar url.
     *
     * @param email user's email address
     * @return the avatar url
     */
    public String create(final String email) {
        return create(email, 120);
    }

    /**
     * create avatar url.
     *
     * @param email user's email address
     * @param size  size of avatar
     * @return the avatar url
     */
    public String create(final String email, int size) {

        validate(email);

        return GRAVATAR_URL + _md5(email.toLowerCase()) + FILE_TYPE_EXTENSION +
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


    private String _md5(String string) {
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException ignored) {
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
}
