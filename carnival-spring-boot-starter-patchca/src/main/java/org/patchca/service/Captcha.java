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

    private String accessKey;
    private String captcha;
    private BufferedImage image;

    public Captcha() {
    }

    public Captcha(String accessKey, String captcha, BufferedImage image) {
        this.accessKey = accessKey;
        this.captcha = captcha;
        this.image = image;
    }

    public String getAccessKey() {
        return accessKey;
    }

    public void setAccessKey(String accessKey) {
        this.accessKey = accessKey;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
