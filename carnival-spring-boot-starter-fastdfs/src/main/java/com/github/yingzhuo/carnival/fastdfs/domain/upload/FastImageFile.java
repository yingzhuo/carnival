/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.upload;

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;

import java.io.InputStream;
import java.util.Set;

/**
 * 上传图片文件
 *
 * @author tobato
 */
public class FastImageFile extends FastFile {

    private ThumbImage thumbImage;

    public FastImageFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {
        super(inputStream, fileSize, fileExtName, metaDataSet);
    }

    public FastImageFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet, ThumbImage thumbImage) {
        super(inputStream, fileSize, fileExtName, metaDataSet);
        this.thumbImage = thumbImage;
    }

    private FastImageFile() {
    }

    public ThumbImage getThumbImage() {
        return thumbImage;
    }

    public String getThumbImagePath(String masterFilename) {
        return thumbImage.getThumbImagePath(masterFilename);
    }

    public static class Builder extends AbstractFastFileBuilder<FastImageFile> {

        private ThumbImage thumbImage;

        @Override
        public Builder toGroup(String groupName) {
            super.toGroup(groupName);
            return this;
        }

        @Override
        public Builder withFile(InputStream inputStream, long fileSize, String fileExtName) {
            super.withFile(inputStream, fileSize, fileExtName);
            return this;
        }

        @Override
        public Builder withMetaData(String name, String value) {
            super.withMetaData(name, value);
            return this;
        }

        @Override
        public Builder withMetaData(Set<MetaData> metaDataSet) {
            super.withMetaData(metaDataSet);
            return this;
        }

        public Builder withThumbImage() {
            this.thumbImage = new ThumbImage();
            return this;
        }

        public Builder withThumbImage(int width, int height) {
            this.thumbImage = new ThumbImage(width, height);
            return this;
        }

        public Builder withThumbImage(double percent) {
            this.thumbImage = new ThumbImage(percent);
            return this;
        }

        @Override
        public FastImageFile build() {
            FastImageFile file = new FastImageFile();
            file.inputStream = this.inputStream;
            file.fileExtName = this.fileExtName;
            file.fileSize = this.fileSize;
            file.metaDataSet = this.metaDataSet;
            file.thumbImage = this.thumbImage;
            file.groupName = this.groupName;
            return file;
        }
    }
}
