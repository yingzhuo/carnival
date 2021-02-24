/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto;

import com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper.ParamMapperUtils;
import org.springframework.core.GenericTypeResolver;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.nio.charset.Charset;

/**
 * 交易应答
 *
 * @author tobato
 */
public abstract class Response<T> implements Serializable {

    protected final Class<T> genericType;

    protected ProtoHead head;

    @SuppressWarnings("unchecked")
    public Response() {
        this.genericType = (Class<T>) GenericTypeResolver.resolveTypeArgument(getClass(), Response.class);
    }

    protected long getContentLength() {
        return head.getContentLength();
    }

    public T decode(ProtoHead head, InputStream in, Charset charset) throws IOException {
        this.head = head;
        return decodeContent(in, charset);
    }

    public T decodeContent(InputStream in, Charset charset) throws IOException {
        // 如果有内容
        if (getContentLength() > 0) {
            byte[] bytes = new byte[(int) getContentLength()];
            int contentSize = in.read(bytes);
            // 获取数据
            if (contentSize != getContentLength()) {
                throw new IOException();
            }
            return ParamMapperUtils.map(bytes, genericType, charset);
        }
        return null;
    }

}
