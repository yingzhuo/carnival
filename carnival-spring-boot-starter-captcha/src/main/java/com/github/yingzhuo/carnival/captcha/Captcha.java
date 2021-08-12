/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.captcha;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * @author Piotr Piastucki
 * @author 应卓
 * @since 1.10.6
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Captcha implements Serializable {

    private String accessKey;
    private String captcha;
    private BufferedImage image;

}
