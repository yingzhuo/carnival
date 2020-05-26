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

import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * param对象与byte映射器
 *
 * @author tobato
 */
public final class ParamMapperUtils {

    private static Map<String, ObjectMetaData> mapCache = new HashMap<>();

    private ParamMapperUtils() {
    }

    public static <T> T map(byte[] content, Class<T> genericType, Charset charset) {
        // 获取映射对象
        ObjectMetaData objectMap = getObjectMap(genericType);

        try {
            return mapByIndex(content, genericType, objectMap, charset);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new FastDFSColumnMappingException(e);
        }
    }

    public static <T> ObjectMetaData getObjectMap(Class<T> genericType) {
        if (null == mapCache.get(genericType.getName())) {
            // 还未缓存过
            mapCache.put(genericType.getName(), new ObjectMetaData(genericType));
        }
        return mapCache.get(genericType.getName());
    }

    private static <T> T mapByIndex(byte[] content, Class<T> genericType, ObjectMetaData objectMap, Charset charset)
            throws InstantiationException, IllegalAccessException, InvocationTargetException {

        List<FieldMetaData> mappingFields = objectMap.getFieldList();
        T obj = genericType.newInstance();
        for (int i = 0; i < mappingFields.size(); i++) {
            FieldMetaData field = mappingFields.get(i);
            // 设置属性值
            BeanUtils.setProperty(obj, field.getFieldName(), field.getValue(content, charset));
        }
        return obj;
    }

    public static byte[] toByte(Object object, Charset charset) {
        ObjectMetaData objectMap = getObjectMap(object.getClass());
        try {
            return convertFieldToByte(objectMap, object, charset);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ie) {
            throw new FastDFSColumnMappingException(ie);
        }
    }

    private static byte[] convertFieldToByte(ObjectMetaData objectMap, Object object, Charset charset)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        List<FieldMetaData> mappingFields = objectMap.getFieldList();
        // 获取报文长度 (固定长度+动态长度)
        int size = objectMap.getFieldsSendTotalByteSize(object, charset);
        byte[] result = new byte[size];
        int offsize = 0;
        for (int i = 0; i < mappingFields.size(); i++) {
            FieldMetaData field = mappingFields.get(i);
            byte[] fieldByte = field.toByte(object, charset);
            if (null != fieldByte) {
                System.arraycopy(fieldByte, 0, result, offsize, fieldByte.length);
                offsize += fieldByte.length;
            }
        }
        return result;
    }

}