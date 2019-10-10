/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.protobuf;

import org.springframework.http.MediaType;

/**
 * @author 应卓
 * @since 1.2.1
 */
public final class ProtobufUtils {

    public static final String PROTOBUF_MEDIA_TYPE_STRING = "application/x-protobuf;charset=UTF-8";

    public static final MediaType PROTOBUF_MEDIA_TYPE = new MediaType(PROTOBUF_MEDIA_TYPE_STRING);

    // -----------------------------------------------------------------------------------------------------------------

    private ProtobufUtils() {
    }

}
