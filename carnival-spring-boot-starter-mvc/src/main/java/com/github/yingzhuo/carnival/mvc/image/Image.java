/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.image;

import lombok.*;

import java.awt.image.BufferedImage;

/**
 * @author 应卓
 * @since 1.6.16
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class Image {

    private Format format;
    private BufferedImage image;

    public enum Format {

        PNG("png"),
        JPG("jpg"),
        JPEG("jpeg"),
        GIF("gif"),
        BMP("bmp"),
        WBMP("wbmp");

        private final String name;

        Format(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

}
