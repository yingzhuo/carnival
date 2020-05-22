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

    /**
     * 图片配置
     */
    private ThumbImage thumbImage;

    /**
     * 上传图片文件
     */
    public FastImageFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet) {
        super(inputStream, fileSize, fileExtName, metaDataSet);
    }

    /**
     * 上传图片文件
     */
    public FastImageFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet, ThumbImage thumbImage) {
        super(inputStream, fileSize, fileExtName, metaDataSet);
        this.thumbImage = thumbImage;
    }

    private FastImageFile() {
    }

    public ThumbImage getThumbImage() {
        return thumbImage;
    }

    /**
     * 获取缩略图路径
     */
    public String getThumbImagePath(String masterFilename) {
        return thumbImage.getThumbImagePath(masterFilename);
    }

    /**
     * 构造模式
     */
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

        /**
         * 按默认方式生成缩略图
         */
        public Builder withThumbImage() {
            this.thumbImage = new ThumbImage();
            return this;
        }

        /**
         * 缩略图配置
         */
        public Builder withThumbImage(int width, int height) {
            this.thumbImage = new ThumbImage(width, height);
            return this;
        }

        /**
         * 缩放比例配置
         */
        public Builder withThumbImage(double percent) {
            this.thumbImage = new ThumbImage(percent);
            return this;
        }

        /**
         * 构造上传文件对象
         */
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
