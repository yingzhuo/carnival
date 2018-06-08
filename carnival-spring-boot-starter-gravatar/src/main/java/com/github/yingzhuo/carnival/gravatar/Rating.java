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

/**
 * Through ratings the retrieved avatars can be reduced to the ones
 * appropriate for the targeted audience.
 * <p>
 * These ratings are based on the
 * <i>Motion Picture Association of America film rating system</i>. For more
 * information read the
 * <a href="http://en.wikipedia.org/wiki/Motion_Picture_Association_of_America_film_rating_system">
 * Wikipedia article</a>.
 * <p>
 * The rating descriptions are taken from the Gravatar website.
 */
public enum Rating {
    /**
     * Allow images for:
     * Suitable for display on all websites with any audience type
     */
    GENERAL_AUDIENCE("g"),

    /**
     * Allow images for:
     * May contain rude gestures, provocatively dressed individuals,
     * the lesser swear words, or mild violence.
     */
    PARENTAL_GUIDANCE_SUGGESTED("pg"),

    /**
     * Allow images for:
     * May contain such things as harsh profanity, intense violence, nudity,
     * or hard drug use.
     */
    RESTRICTED("r"),

    /**
     * Allow images for:
     * May contain hardcore sexual imagery or extremely disturbing violence.
     */
    ADULT_ONLY("x");

    private String key;

    private Rating(String key) {
        this.key = key;
    }

    /**
     * Retrieve the query parameter value which indicates the desired rating.
     *
     * @return Gravatar rating query parameter value
     */
    public String getKey() {
        return key;
    }

}