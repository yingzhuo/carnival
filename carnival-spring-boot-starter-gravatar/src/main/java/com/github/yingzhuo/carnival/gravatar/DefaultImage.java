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
 * A default avatar can be received in case there is no avatar for an email
 * address, when the avatar is not appropriate for the audience (due to the
 * rating) or when you force default avatar retrieval.
 * <p>
 * Default image descriptions were taken from the Gravatar website.
 */
public enum DefaultImage {

    /**
     * Return an HTTP 404 error.
     */
    HTTP_404("404"),

    /**
     * A simple, cartoon-style silhouetted outline of a person (does not vary by
     * email hash).
     */
    MYSTERY_MAN("mm"),

    /**
     * A geometric pattern based on an email hash.
     */
    IDENTICON("identicon"),

    /**
     * A generated 'monster' with different colors, faces, etc.
     */
    MONSTER("monsterid"),

    /**
     * Generated faces with differing features and backgrounds
     */
    WAVATAR("wavatar"),

    /**
     * Awesome generated, 8-bit arcade-style pixelated faces.
     */
    RETRO("retro");

    private final String key;

    private DefaultImage(String key) {
        this.key = key;
    }

    /**
     * Retrieve the query parameter value which indicates the desired default
     * image.
     *
     * @return Gravatar default image query parameter value
     */
    String getKey() {
        return key;
    }
}
