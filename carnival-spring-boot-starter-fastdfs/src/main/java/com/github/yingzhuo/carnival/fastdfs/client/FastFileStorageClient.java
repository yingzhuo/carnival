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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorePath;
import com.github.yingzhuo.carnival.fastdfs.domain.upload.FastFile;
import com.github.yingzhuo.carnival.fastdfs.domain.upload.FastImageFile;

import java.io.InputStream;
import java.util.Set;

/**
 * 面向普通应用的文件操作接口封装
 *
 * @author tobato
 */
public interface FastFileStorageClient extends GenerateStorageClient {

    /*
     * 上传一般文件
     */
    public StorePath uploadFile(InputStream inputStream, long fileSize, String fileExtName, Set<MetaData> metaDataSet);

    /*
     * 上传图片并且生成缩略图
     * <pre>
     * 支持的图片格式包括"JPG", "JPEG", "PNG", "GIF", "BMP", "WBMP"
     *
     * 缩略图为上传文件名+缩略图后缀 _150x150,如 xxx.jpg,缩略图为 xxx_150x150.jpg
     *
     * 实际样例如下
     *
     *  原图   http://localhost:8098/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374.jpg
     *  缩略图 http://localhost:8098/M00/00/17/rBEAAl33pQaAWNQNAAHYvQQn-YE374_150x150.jpg
     *
     * </pre>
     */
    public StorePath uploadImageAndCrtThumbImage(InputStream inputStream, long fileSize, String fileExtName,
                                                 Set<MetaData> metaDataSet);

    /*
     * 上传图片
     * <pre>
     * 可通过fastImageFile对象配置
     * 1. 上传图像分组
     * 2. 上传元数据metaDataSet
     * 3. 是否生成缩略图
     *   3.1 根据默认配置生成缩略图
     *   3.2 根据指定尺寸生成缩略图
     *   3.3 根据指定比例生成缩略图
     * <pre/>
     */
    public StorePath uploadImage(FastImageFile fastImageFile);


    /*
     * 上传文件
     * <pre>
     * 可通过fastFile对象配置
     * 1. 上传图像分组
     * 2. 上传元数据metaDataSet
     * <pre/>
     */
    public StorePath uploadFile(FastFile fastFile);

    /*
     * 删除文件
     *
     * @param filePath 文件路径(groupName/path)
     */
    public void deleteFile(String filePath);

}
