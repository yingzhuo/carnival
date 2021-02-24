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

import com.github.yingzhuo.carnival.fastdfs.domain.fdfs.MetaData;
import com.github.yingzhuo.carnival.fastdfs.domain.proto.OtherConstants;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.util.Set;

/**
 * 属性映射MetaData定义
 *
 * @author tobato
 */
@Getter
@Setter
@ToString
class FieldMetaData {

    /**
     * 列
     */
    private Field field;
    /**
     * 列索引
     */
    private int index;

    /**
     * 单元最大长度
     */
    private int max;

    /**
     * 单元长度
     */
    private int size;

    /**
     * 列偏移量
     */
    private int offset;

    /**
     * 动态属性类型
     */
    private DynamicFieldType dynamicFieldType;

    public FieldMetaData(Field mappedField, int offset) {
        Column column = mappedField.getAnnotation(Column.class);
        this.field = mappedField;
        this.index = column.index();
        this.max = column.max();
        this.size = getFieldSize(field);
        this.offset = offset;
        this.dynamicFieldType = column.dynamicField();
        // 如果强制设置了最大值，以最大值为准
        if (this.max > 0 && this.size > this.max) {
            this.size = this.max;
        }
    }

    /**
     * 获取Field大小
     */
    private int getFieldSize(Field field) {
        if (String.class == field.getType()) {
            return this.max;
        } else if (long.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (int.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (java.util.Date.class == field.getType()) {
            return OtherConstants.FDFS_PROTO_PKG_LEN_SIZE;
        } else if (byte.class == field.getType()) {
            return 1;
        } else if (boolean.class == field.getType()) {
            return 1;
        } else if (Set.class == field.getType()) {
            return 0;
        }
        throw new FastDFSColumnMappingException(field.getName() + "获取Field大小时未识别的FdfsColumn类型" + field.getType());
    }

    /**
     * 获取值
     */
    public Object getValue(byte[] bs, Charset charset) {
        if (String.class == field.getType()) {
            if (isDynamicField()) {
                return (new String(bs, offset, bs.length - offset, charset)).trim();
            }
            return (new String(bs, offset, size, charset)).trim();
        } else if (long.class == field.getType()) {
            return BytesUtils.buff2long(bs, offset);
        } else if (int.class == field.getType()) {
            return (int) BytesUtils.buff2long(bs, offset);
        } else if (java.util.Date.class == field.getType()) {
            return new Date(BytesUtils.buff2long(bs, offset) * 1000);
        } else if (byte.class == field.getType()) {
            return bs[offset];
        } else if (boolean.class == field.getType()) {
            return bs[offset] != 0;
        }
        throw new FastDFSColumnMappingException(field.getName() + "获取值时未识别的FdfsColumn类型" + field.getType());
    }


    public String getFieldName() {
        return field.getName();
    }

    /**
     * 获取真实属性
     */
    public int getRealSize() {
        // 如果是动态属性
        if (isDynamicField()) {
            return 0;
        }
        return size;
    }

    /**
     * 将属性值转换为byte
     */
    public byte[] toByte(Object bean, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = this.getFieldValue(bean);
        if (isDynamicField()) {
            return getDynamicFieldByteValue(value, charset);
        } else if (String.class.equals(field.getType())) {
            // 如果是动态属性
            return BytesUtils.objString2Byte((String) value, max, charset);
        } else if (long.class.equals(field.getType())) {
            return BytesUtils.long2buff((long) value);
        } else if (int.class.equals(field.getType())) {
            return BytesUtils.long2buff((int) value);
        } else if (Date.class.equals(field.getType())) {
            throw new FastDFSColumnMappingException("Date 还不支持");
        } else if (byte.class.equals(field.getType())) {
            byte[] result = new byte[1];
            result[0] = (byte) value;
            return result;
        } else if (boolean.class.equals(field.getType())) {
            throw new FastDFSColumnMappingException("boolean 还不支持");
        }
        throw new FastDFSColumnMappingException("将属性值转换为byte时未识别的FdfsColumn类型" + field.getName());
    }

    /**
     * 获取动态属性值
     */
    @SuppressWarnings("unchecked")
    private byte[] getDynamicFieldByteValue(Object value, Charset charset) {
        switch (dynamicFieldType) {
            // 如果是打包剩余的所有Byte
            case ALL_REST_BYTE:
                return BytesUtils.objString2Byte((String) value, charset);
            // 如果是文件metadata
            case METADATA:
                return MetadataMapper.toByte((Set<MetaData>) value, charset);
            default:
                return BytesUtils.objString2Byte((String) value, charset);
        }
    }

    /**
     * 获取单元对应值
     */
    private Object getFieldValue(Object bean)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        return PropertyUtils.getProperty(bean, field.getName());
    }

    /**
     * 获取动态属性长度
     */
    @SuppressWarnings("unchecked")
    public int getDynamicFieldByteSize(Object bean, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object value = PropertyUtils.getProperty(bean, field.getName());
        if (null == value) {
            return 0;
        }
        switch (dynamicFieldType) {
            // 如果是打包剩余的所有Byte
            case ALL_REST_BYTE:
                return ((String) value).getBytes(charset).length;
            // 如果是文件metadata
            case METADATA:
                return MetadataMapper.toByte((Set<MetaData>) value, charset).length;
            default:
                return getFieldSize(field);
        }
    }

    /**
     * 是否动态属性
     */
    public boolean isDynamicField() {
        return (!DynamicFieldType.NULL.equals(dynamicFieldType));
    }

}
