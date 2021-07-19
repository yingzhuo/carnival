/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.support;

import com.github.yingzhuo.carnival.common.util.HexUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.SneakyThrows;
import org.springframework.core.io.Resource;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static org.apache.commons.io.IOUtils.closeQuietly;
import static org.apache.commons.io.IOUtils.toByteArray;

/**
 * @author 应卓
 * @since 1.9.13
 */
@Getter
@Setter
public final class DownloadingFile {

    public static Builder builder() {
        return new Builder();
    }

    DownloadingFile() {
    }

    private byte[] content;
    private String filename;
    private Charset filenameCharset = StandardCharsets.ISO_8859_1;

    public static class Builder {
        private byte[] content = new byte[0];
        private String filename = "file";
        private Charset filenameCharset = StandardCharsets.ISO_8859_1;

        Builder() {
        }

        @SneakyThrows
        public Builder content(Resource content) {
            InputStream inputStream = content.getInputStream();
            this.content = toByteArray(inputStream);
            closeQuietly(inputStream);
            return this;
        }

        public Builder content(byte[] content) {
            this.content = content;
            return this;
        }

        public Builder content(String hexString) {
            this.content = HexUtils.decode(hexString);
            return this;
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder filenameCharset(Charset charset) {
            this.filenameCharset = charset;
            return this;
        }

        public DownloadingFile build() {
            DownloadingFile df = new DownloadingFile();
            df.setContent(this.content);
            df.setFilename(this.filename);
            df.setFilenameCharset(this.filenameCharset);
            return df;
        }
    }

}
