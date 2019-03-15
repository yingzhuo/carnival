/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mvc.download;

import org.apache.commons.io.FileUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UncheckedIOException;

/**
 * @author 应卓
 */
public interface DownloadEntity {

    public static Builder builder() {
        return new Builder();
    }

    public String getFilename();

    public String getFilenameEncoding();

    public HttpStatus getHttpStatus();

    public InputStream getInputStream();

    public static class Builder {

        private final static ResourceLoader RESOURCE_LOADER = new DefaultResourceLoader();
        private String filename = null;
        private String filenameEncoding = "UTF-8";
        private HttpStatus httpStatus = HttpStatus.OK;
        private InputStream inputStream = null;

        private Builder() {
            super();
        }

        public Builder filename(String filename) {
            this.filename = filename;
            return this;
        }

        public Builder filenameEncoding(String filenameEncoding) {
            this.filename = filenameEncoding;
            return this;
        }

        public Builder file(File file) {
            try {
                this.inputStream = FileUtils.openInputStream(file);
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder location(String location) {
            try {
                this.inputStream = RESOURCE_LOADER.getResource(location).getInputStream();
                return this;
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public Builder inputStream(InputStream inputStream) {
            this.inputStream = inputStream;
            return this;
        }

        public Builder httpStatus(HttpStatus httpStatus) {
            this.httpStatus = httpStatus;
            return this;
        }

        public DownloadEntity build() {
            return new DownloadEntity() {
                @Override
                public String getFilename() {
                    return filename;
                }

                @Override
                public String getFilenameEncoding() {
                    return filenameEncoding;
                }

                @Override
                public HttpStatus getHttpStatus() {
                    return httpStatus;
                }

                @Override
                public InputStream getInputStream() {
                    return inputStream;
                }
            };
        }
    }

}
