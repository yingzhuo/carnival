/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.tracker.internal;

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.StorageState;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.Response;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.ObjectMetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.ParamMapperUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 列出分组信息执行结果
 *
 * @author tobato
 */
public class TrackerListStorageResponse extends Response<List<StorageState>> {

    /**
     * 解析反馈内容
     */
    @Override
    public List<StorageState> decodeContent(InputStream in, Charset charset) throws IOException {
        // 解析报文内容
        byte[] bytes = new byte[(int) getContentLength()];
        int contentSize = in.read(bytes);
        if (contentSize != getContentLength()) {
            throw new IOException("读取到的数据长度与协议长度不符");
        }
        return decode(bytes, charset);
    }

    /**
     * 解析Group
     */
    private List<StorageState> decode(byte[] bs, Charset charset) throws IOException {
        // 获取对象转换定义
        ObjectMetaData objectMetaData = ParamMapperUtils.getObjectMap(StorageState.class);
        int fixFieldsTotalSize = objectMetaData.getFieldsFixTotalSize();
        if (bs.length % fixFieldsTotalSize != 0) {
            throw new IOException("fixFieldsTotalSize=" + fixFieldsTotalSize + "but byte array length: " + bs.length
                    + " is invalid!");
        }
        // 计算反馈对象数量
        int count = bs.length / fixFieldsTotalSize;
        int offset = 0;
        List<StorageState> results = new ArrayList<>(count);

        for (int i = 0; i < count; i++) {
            byte[] one = new byte[fixFieldsTotalSize];
            System.arraycopy(bs, offset, one, 0, fixFieldsTotalSize);
            results.add(ParamMapperUtils.map(one, StorageState.class, charset));
            offset += fixFieldsTotalSize;
        }

        return results;
    }
}
