/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.storage.enums;

/**
 * 元数据设置方式
 *
 * @author tobato
 */
public enum StorageMetadataSetType {

    /**
     * 覆盖
     */
    STORAGE_SET_METADATA_FLAG_OVERWRITE((byte) 'O'),

    /**
     * 没有的条目增加，有则条目覆盖
     */
    STORAGE_SET_METADATA_FLAG_MERGE((byte) 'M');

    private byte type;

    private StorageMetadataSetType(byte type) {
        this.type = type;
    }

    public byte getType() {
        return type;
    }

}
