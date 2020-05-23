/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.client;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.StorageConnectionManager;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageNode;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorePath;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageSetMetadataCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageUploadFileCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.StorageUploadSlaveFileCommand;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.enums.StorageMetadataSetType;
import com.github.yingzhuo.carnival.fastdfs.domain.upload.FastFile;
import com.github.yingzhuo.carnival.fastdfs.domain.upload.FastImageFile;
import com.github.yingzhuo.carnival.fastdfs.domain.upload.ThumbImage;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSUnsupportedImageFormatException;
import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSUploadImageException;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.io.IOUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * 面向应用的接口实现
 *
 * @author tobato
 * @author 应卓
 */
@Slf4j
public class FastFileStorageClientImpl extends GenerateStorageClientImpl implements FastFileStorageClient {

    private static final Set<String> SUPPORTED_IMAGE
            = new HashSet<>(Arrays.asList("JPG", ".JPG", "JPEG", ".JPEG", "PNG", ".PNG", "GIF", ".GIF", "BMP", ".BMP", "WBMP", ".WBMP"));

    private final int thumbImageWidth;
    private final int thumbImageHeight;

    public FastFileStorageClientImpl(TrackerClient trackerClient, StorageConnectionManager manager, int thumbImageWidth, int thumbImageHeight) {
        super(trackerClient, manager);
        this.thumbImageWidth = thumbImageWidth;
        this.thumbImageHeight = thumbImageHeight;
    }

    /**
     * 上传文件
     */
    @Override
    public StorePath uploadFile(InputStream inputStream, long fileSize,
                                String fileExtName, Set<MetaData> metaDataSet) {
        FastFile fastFile;
        if (null == metaDataSet) {
            fastFile = new FastFile.Builder()
                    .withFile(inputStream, fileSize, fileExtName)
                    .build();
        } else {
            fastFile = new FastFile.Builder()
                    .withFile(inputStream, fileSize, fileExtName)
                    .withMetaData(metaDataSet)
                    .build();
        }
        return uploadFile(fastFile);
    }

    /**
     * 上传图片并且生成缩略图
     */
    @Override
    public StorePath uploadImageAndCrtThumbImage(InputStream inputStream,
                                                 long fileSize,
                                                 String fileExtName,
                                                 Set<MetaData> metaDataSet) {
        FastImageFile fastImageFile;
        if (null == metaDataSet) {
            fastImageFile = new FastImageFile.Builder()
                    .withFile(inputStream, fileSize, fileExtName)
                    .withThumbImage()
                    .build();
        } else {
            fastImageFile = new FastImageFile.Builder()
                    .withFile(inputStream, fileSize, fileExtName)
                    .withMetaData(metaDataSet)
                    .withThumbImage()
                    .build();
        }
        return uploadImage(fastImageFile);
    }

    @Override
    public StorePath uploadFile(FastFile fastFile) {
        // 获取存储节点
        StorageNode client = getStorageNode(fastFile.getGroupName());
        // 上传文件
        return uploadFileAndMetaData(client, fastFile.getInputStream(),
                fastFile.getFileSize(), fastFile.getFileExtName(),
                fastFile.getMetaDataSet());
    }

    @Override
    public StorePath uploadImage(FastImageFile fastImageFile) {
        String fileExtName = fastImageFile.getFileExtName();
        // 检查是否能处理此类图片
        if (!isSupportImage(fileExtName)) {
            throw new FastDFSUnsupportedImageFormatException("不支持的图片格式" + fileExtName);
        }
        // 获取存储节点
        StorageNode client = getStorageNode(fastImageFile.getGroupName());
        byte[] bytes = inputStreamToByte(fastImageFile.getInputStream());

        // 上传文件和metaDataSet
        StorePath path = uploadFileAndMetaData(client, new ByteArrayInputStream(bytes),
                fastImageFile.getFileSize(), fileExtName,
                fastImageFile.getMetaDataSet());

        //如果设置了需要上传缩略图
        if (null != fastImageFile.getThumbImage()) {
            // 上传缩略图
            uploadThumbImage(client, new ByteArrayInputStream(bytes), path.getPath(), fastImageFile);
        }
        bytes = null;
        return path;
    }

    private StorageNode getStorageNode(String groupName) {
        if (null == groupName) {
            return trackerClient.getStoreStorage();
        } else {
            return trackerClient.getStoreStorage(groupName);
        }
    }

    private byte[] inputStreamToByte(InputStream inputStream) {
        try {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("image inputStream to byte error", e);
            throw new FastDFSUploadImageException("upload ThumbImage error", e.getCause());
        }
    }

    private boolean hasMetaData(Set<MetaData> metaDataSet) {
        return null != metaDataSet && !metaDataSet.isEmpty();
    }

    private boolean isSupportImage(String fileExtName) {
        return SUPPORTED_IMAGE.contains(fileExtName.toUpperCase());
    }

    private StorePath uploadFileAndMetaData(StorageNode client, InputStream inputStream, long fileSize,
                                            String fileExtName, Set<MetaData> metaDataSet) {
        // 上传文件
        StorageUploadFileCommand command = new StorageUploadFileCommand(client.getStoreIndex(), inputStream,
                fileExtName, fileSize, false);
        StorePath path = connectionManager.executeCommand(client.getInetSocketAddress(), command);
        // 上传metadata
        if (hasMetaData(metaDataSet)) {
            StorageSetMetadataCommand setMDCommand = new StorageSetMetadataCommand(path.getGroup(), path.getPath(),
                    metaDataSet, StorageMetadataSetType.STORAGE_SET_METADATA_FLAG_OVERWRITE);
            connectionManager.executeCommand(client.getInetSocketAddress(), setMDCommand);
        }
        return path;
    }

    private void uploadThumbImage(StorageNode client, InputStream inputStream,
                                  String masterFilename, FastImageFile fastImageFile) {
        ByteArrayInputStream thumbImageStream = null;
        ThumbImage thumbImage = fastImageFile.getThumbImage();
        try {
            //生成缩略图片
            thumbImageStream = generateThumbImageStream(inputStream, thumbImage);
            // 获取文件大小
            long fileSize = thumbImageStream.available();
            // 获取配置缩略图前缀
            String prefixName = thumbImage.getPrefixName();
            if (log.isDebugEnabled()) {
                log.debug("获取到缩略图前缀{}", prefixName);
            }
            StorageUploadSlaveFileCommand command = new StorageUploadSlaveFileCommand(thumbImageStream, fileSize,
                    masterFilename, prefixName, fastImageFile.getFileExtName());
            connectionManager.executeCommand(client.getInetSocketAddress(), command);

        } catch (IOException e) {
            log.error("upload ThumbImage error", e);
            throw new FastDFSUploadImageException("upload ThumbImage error", e.getCause());
        } finally {
            IOUtils.closeQuietly(thumbImageStream);
        }
    }

    private ByteArrayInputStream generateThumbImageStream(InputStream inputStream,
                                                          ThumbImage thumbImage) throws IOException {
        //根据传入配置生成缩略图
        if (thumbImage.isDefaultConfig()) {
            thumbImage.setDefaultSize(thumbImageWidth, thumbImageHeight);
            return generateThumbImageByDefault(inputStream);
        } else if (thumbImage.getPercent() != 0) {
            return generateThumbImageByPercent(inputStream, thumbImage);
        } else {
            return generateThumbImageBySize(inputStream, thumbImage);
        }

    }

    private ByteArrayInputStream generateThumbImageByPercent(InputStream inputStream,
                                                             ThumbImage thumbImage) throws IOException {
        log.debug("根据传入比例生成缩略图");
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails
                .of(inputStream)
                .scale(thumbImage.getPercent())
                .imageType(BufferedImage.TYPE_INT_ARGB)
                .toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    private ByteArrayInputStream generateThumbImageBySize(InputStream inputStream,
                                                          ThumbImage thumbImage) throws IOException {
        log.debug("根据传入尺寸生成缩略图");
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        //@formatter:off
        Thumbnails
                .of(inputStream)
                .size(thumbImage.getWidth(), thumbImage.getHeight())
                .imageType(BufferedImage.TYPE_INT_ARGB)
                .toOutputStream(out);
        //@formatter:on
        return new ByteArrayInputStream(out.toByteArray());
    }

    private ByteArrayInputStream generateThumbImageByDefault(InputStream inputStream) throws IOException {
        log.debug("根据默认配置生成缩略图");
        // 在内存当中生成缩略图
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Thumbnails
                .of(inputStream)
                .size(thumbImageWidth, thumbImageHeight)
                .imageType(BufferedImage.TYPE_INT_ARGB)
                .toOutputStream(out);
        return new ByteArrayInputStream(out.toByteArray());
    }

    @Override
    public void deleteFile(String filePath) {
        StorePath storePath = StorePath.parseFromUrl(filePath);
        super.deleteFile(storePath.getGroup(), storePath.getPath());
    }

}
