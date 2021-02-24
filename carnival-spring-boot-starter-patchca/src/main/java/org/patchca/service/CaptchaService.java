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

/**
 * @author Piotr Piastucki
 */
@FunctionalInterface
public interface CaptchaService {

    public Captcha getCaptcha();

    public default EncodedCaptcha getEncodedCaptcha() {
        return new EncodedCaptcha(getCaptcha());
    }

}
