/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package org.patchca.service;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author Piotr Piastucki
 * @author 应卓
 */
public class Captcha implements Serializable {

    private final String accessKey;
    private final String challenge;
    private final BufferedImage image;

    public Captcha(String accessKey, String challenge, BufferedImage image) {
        this.accessKey = accessKey;
        this.challenge = challenge;
        this.image = image;
    }

    public String getAccessKey() {
        return this.accessKey;
    }

    public String getChallenge() {
        return this.challenge;
    }

    public BufferedImage getImage() {
        return this.image;
    }

}
