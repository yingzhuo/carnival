/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.qrcode;

import org.springframework.core.io.Resource;

import javax.imageio.ImageIO;
import javax.imageio.stream.ImageInputStream;
import java.awt.*;
import java.io.*;
import java.util.Objects;

/**
 * Logo
 *
 * @author 应卓
 * @since 1.7.3
 */
public final class Logo implements Serializable {

    private Image image;
    private boolean compress = true;

    private Logo() {
    }

    public static Builder builder() {
        return new Builder();
    }

    public Image getImage() {
        return image;
    }

    public boolean isCompress() {
        return compress;
    }

    private static class Builder {
        private Image image;
        private boolean compress = true;

        public Builder image(Image image) {
            this.image = image;
            return this;
        }

        public Builder image(Resource resource) {
            try {
                return image(resource.getFile());
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder image(InputStream inputStream) {
            try {
                this.image = ImageIO.read(inputStream);
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder image(ImageInputStream inputStream) {
            try {
                this.image = ImageIO.read(inputStream);
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder image(File file) {
            try {
                this.image = ImageIO.read(file);
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder image(byte[] bytes) {
            try {
                ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                this.image = ImageIO.read(bais);
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder compress(boolean compress) {
            this.compress = compress;
            return this;
        }

        public Logo build() {
            Logo logo = new Logo();
            logo.image = Objects.requireNonNull(image);
            logo.compress = this.compress;
            return logo;
        }
    }

}
