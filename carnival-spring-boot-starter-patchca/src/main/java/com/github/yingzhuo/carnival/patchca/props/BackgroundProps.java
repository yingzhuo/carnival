/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.patchca.props;

import lombok.Data;
import org.patchca.color.ColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.awt.*;

@Data
@ConfigurationProperties(prefix = "carnival.patchca.background")
public class BackgroundProps {

    private int r = 255;
    private int g = 255;
    private int b = 255;

    public ColorFactory createColorFactory() {
        if (r < 0 || g < 0 || b < 0) {
            return new RandomColorFactory();
        } else {
            return new SingleColorFactory(new Color(r, g, b));
        }
    }
}
