/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.proto.mapper;

/**
 * 动态属性类型
 * <pre>
 * 可以为空的属性-不发送该报文
 * 剩余的所有byte-将该字段全部写入到最后的byte当中
 * </pre>
 *
 * @author tobato
 */
public enum DynamicFieldType {

    /**
     * 非动态属性
     */
    NULL,

    /**
     * 剩余的所有Byte
     */
    ALL_REST_BYTE,

    /**
     * 可空的属性
     */
    NULLABLE,

    /**
     * 文件元数据Set
     */
    METADATA

}
