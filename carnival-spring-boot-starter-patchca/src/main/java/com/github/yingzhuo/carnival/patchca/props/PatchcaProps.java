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

import com.github.yingzhuo.carnival.patchca.core.PatchcaServletFilter;
import lombok.Getter;
import lombok.Setter;
import org.patchca.color.ColorFactory;
import org.patchca.color.RandomColorFactory;
import org.patchca.color.SingleColorFactory;
import org.patchca.filter.FilterType;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.Ordered;

import java.awt.*;

@Getter
@Setter
@ConfigurationProperties(prefix = "carnival.patchca")
public class PatchcaProps {

    private boolean enabled = true;
    private BackgroundProps background = new BackgroundProps();
    private ForegroundProps foreground = new ForegroundProps();
    private FilterProps filter = new FilterProps();
    private FontProps font = new FontProps();
    private TextRendererProps textRenderer = new TextRendererProps();
    private WordProps word = new WordProps();
    private SizeProps size = new SizeProps();
    private ServletFilterProps servletFilter = new ServletFilterProps();

    // -----------------------------------------------------------------------------------------------------------------

    @Getter
    @Setter
    public static class FilterProps {
        private FilterType type = FilterType.CURVES;
    }

    @Getter
    @Setter
    public static class ForegroundProps {
        private int r = -1;
        private int g = -1;
        private int b = -1;

        public ColorFactory createColorFactory() {
            if (r < 0 || g < 0 || b < 0) {
                return new RandomColorFactory();
            } else {
                return new SingleColorFactory(new Color(r, g, b));
            }
        }
    }

    @Getter
    @Setter
    public static class BackgroundProps {
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

    @Getter
    @Setter
    public static class FontProps {
        private String[] families = new String[]{"Verdana", "Tahoma"};
        private int minSize = 45;
        private int maxSize = 45;
    }

    @Getter
    @Setter
    public static class TextRendererProps {
        private int leftMargin = 10;
        private int rightMargin = 10;
        private int topMargin = 5;
        private int bottomMargin = 5;
    }

    @Getter
    @Setter
    public static class WordProps {
        private String wideCharacters = "wm";
        private String characters = "abcdefghijklmnopqrstuvwxyz0123456789";
        private int minLength = 6;
        private int maxLength = 6;
    }

    @Getter
    @Setter
    public static class SizeProps {
        private int width = 160;
        private int height = 70;
    }

    @Getter
    @Setter
    public static class ServletFilterProps {
        private String name = PatchcaServletFilter.class.getSimpleName();
        private int order = Ordered.LOWEST_PRECEDENCE;
        private String[] urlPatterns = new String[]{"/patchca.png", "/patchca"};
        private String sessionAttributeName = "_PATCHCA";
    }

}
