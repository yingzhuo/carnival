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

import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 映射对象元数据
 * <p>
 * <pre>
 * 映射对象元数据必须由{@code @FdfsColumn}注解
 * </pre>
 *
 * @author tobato
 */
@Slf4j
public class ObjectMetaData {

    /**
     * 映射对象类名
     */
    private String className;

    /**
     * 映射列(全部)
     */
    private List<FieldMetaData> fieldList = new ArrayList<>();

    /**
     * 动态计算列(部分)fieldList包含dynamicFieldList
     */
    private List<FieldMetaData> dynamicFieldList = new ArrayList<>();

    /**
     * FieldsTotalSize
     */
    private int fieldsTotalSize = 0;

    /**
     * 映射对象元数据构造函数
     */
    public <T> ObjectMetaData(Class<T> genericType) {
        // 获得对象类名
        this.className = genericType.getName();
        this.fieldList = parseFieldList(genericType);
        // 校验映射定义
        validateFieldListDefine();
    }

    public String getClassName() {
        return className;
    }

    public List<FieldMetaData> getFieldList() {
        return Collections.unmodifiableList(fieldList);
    }

    /**
     * 解析映射对象数据映射情况
     */
    private <T> List<FieldMetaData> parseFieldList(Class<T> genericType) {
        Field[] fields = genericType.getDeclaredFields();
        List<FieldMetaData> mappedFieldList = new ArrayList<>();
        for (int i = 0; i < fields.length; i++) {
            if (fields[i].isAnnotationPresent(Column.class)) {
                FieldMetaData fieldMetaData = new FieldMetaData(fields[i], fieldsTotalSize);
                mappedFieldList.add(fieldMetaData);
                // 计算偏移量
                fieldsTotalSize += fieldMetaData.getRealSize();
                // 如果是动态计算列
                if (fieldMetaData.isDynamicField()) {
                    dynamicFieldList.add(fieldMetaData);
                }
            }
        }
        return mappedFieldList;
    }

    /**
     * 检查数据列定义
     * <p>
     * <pre>
     * 为了减少编码的错误，检查数据列定义是否存在列名相同或者索引定义相同(多个大于0相同的)的
     * </pre>
     */
    private void validateFieldListDefine() {
        for (FieldMetaData field : fieldList) {
            validateFieldItemDefineByIndex(field);
        }
    }

    /**
     * 检查按索引映射
     */
    private void validateFieldItemDefineByIndex(FieldMetaData field) {
        for (FieldMetaData otherField : fieldList) {
            if (!field.equals(otherField) && (field.getIndex() == otherField.getIndex())) {
                Object[] param = {className, field.getFieldName(), otherField.getFieldName(), field.getIndex()};
                log.warn("在类{}映射定义中{}与{}索引定义相同为{}(请检查是否为程序错误)", param);
            }
        }
    }

    /**
     * 是否有动态数据列
     */
    private boolean hasDynamicField() {
        for (FieldMetaData field : fieldList) {
            if (field.isDynamicField()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取动态数据列长度
     */
    private int getDynamicFieldSize(Object obj, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        int size = 0;
        for (FieldMetaData field : dynamicFieldList) {
            size = size + field.getDynamicFieldByteSize(obj, charset);
        }
        return size;
    }

    /**
     * 获取固定参数对象总长度
     */
    public int getFieldsFixTotalSize() {
        if (hasDynamicField()) {
            throw new FastDFSColumnMappingException(
                    className + " class hasDynamicField, unsupported operator getFieldsTotalSize");
        }
        return fieldsTotalSize;
    }

    /**
     * 获取需要发送的报文长度
     */
    public int getFieldsSendTotalByteSize(Object bean, Charset charset) {
        if (!hasDynamicField()) {
            return fieldsTotalSize;
        } else {
            return getDynamicTotalFieldSize(bean, charset);
        }
    }

    /**
     * 获取动态属性长度
     */
    private int getDynamicTotalFieldSize(Object bean, Charset charset) {
        try {
            int dynamicFieldSize = getDynamicFieldSize(bean, charset);
            return fieldsTotalSize + dynamicFieldSize;
        } catch (NoSuchMethodException ie) {
            log.debug("Cannot invoke get method: ", ie);
            throw new FastDFSColumnMappingException(ie);
        } catch (IllegalAccessException iae) {
            log.debug("Illegal access: ", iae);
            throw new FastDFSColumnMappingException(iae);
        } catch (InvocationTargetException ite) {
            log.debug("Cannot invoke method: ", ite);
            throw new FastDFSColumnMappingException(ite);
        }
    }

//    /**
//     * 导出调试信息
//     */
//    public void dumpObjectMetaData() {
//        log.debug("dump class={}", className);
//        log.debug("----------------------------------------");
//        for (FieldMetaData md : fieldList) {
//            log.debug(md.toString());
//        }
//    }

}
