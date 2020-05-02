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

import java.io.Serializable;

/**
 * @author Piotr Piastucki
 * @author 应卓
 * @since 1.6.2
 */
public class EncodedCaptcha extends Captcha implements Serializable {

    private String encodeImage;

    public EncodedCaptcha() {
    }

    public EncodedCaptcha(Captcha captcha, String encodedImage) {
        super(captcha.getAccessKey(), captcha.getCaptcha(), captcha.getImage());
        this.encodeImage = encodedImage;
    }

    public String getEncodeImage() {
        return encodeImage;
    }

    public void setEncodeImage(String encodeImage) {
        this.encodeImage = encodeImage;
    }

}
