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
import java.util.HashSet;
import java.util.Set;

/**
 * 构造FastFile抽象对象
 *
 * @author wuyf
 */
public abstract class AbstractFastFileBuilder<T> {

    /**
     * 输入流
     */
    protected InputStream inputStream;

    /**
     * 文件大小
     */
    protected long fileSize;

    /**
     * 文件扩展名
     */
    protected String fileExtName;

    /**
     * 文件元数据
     */
    protected Set<MetaData> metaDataSet = new HashSet<>();

    /**
     * 上传文件分组
     */
    protected String groupName;

    public AbstractFastFileBuilder<T> withFile(InputStream inputStream, long fileSize, String fileExtName) {
        this.inputStream = inputStream;
        this.fileSize = fileSize;
        this.fileExtName = fileExtName;
        return this;
    }

    public AbstractFastFileBuilder<T> withMetaData(String name, String value) {
        this.metaDataSet.add(new MetaData(name, value));
        return this;
    }

    public AbstractFastFileBuilder<T> withMetaData(Set<MetaData> metaDataSet) {
        this.metaDataSet.addAll(metaDataSet);
        return this;
    }

    public AbstractFastFileBuilder<T> toGroup(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public abstract T build();

}
