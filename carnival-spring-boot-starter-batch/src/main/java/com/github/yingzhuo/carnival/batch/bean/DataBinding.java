/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.batch.bean;

import com.github.yingzhuo.carnival.spring.ConversionUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.DirectFieldAccessor;
import org.springframework.util.ClassUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * 数据绑定工具
 *
 * @author 应卓
 * @see Binding
 * @since 1.10.35
 */
@Slf4j
public final class DataBinding {

    private static final Class<Binding> ANNOTATION_TYPE = Binding.class;

    private DataBinding() {
    }

    public static <T> T bind(T obj, String[] array) {

        if (obj == null || array == null || array.length == 0) {
            return obj;
        }

        BeanWrapper wrapper1 = new BeanWrapperImpl(obj);
        DirectFieldAccessor wrapper2 = new DirectFieldAccessor(obj);

        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
            PropertyDescriptor[] descriptors = beanInfo.getPropertyDescriptors();

            for (PropertyDescriptor descriptor : descriptors) {
                try {
                    doBind(obj.getClass(), descriptor, wrapper1, wrapper2, array);
                } catch (Exception e) {
                    // nop
                }
            }

        } catch (IntrospectionException e) {
            log.error(e.getMessage(), e);
        }

        return obj;
    }

    private static void doBind(Class<?> beanType, PropertyDescriptor descriptor, BeanWrapper w1, DirectFieldAccessor w2, String[] array) {
        Method writeMethod = descriptor.getWriteMethod();
        Binding annotation = writeMethod != null ? writeMethod.getDeclaredAnnotation(ANNOTATION_TYPE) : null;

        if (annotation != null) {
            w1.setPropertyValue(
                    descriptor.getName(),
                    doConvert(array[annotation.index()], writeMethod.getParameterTypes()[0])
            );
            return;
        }

        try {
            Field field = beanType.getDeclaredField(descriptor.getName());
            annotation = field.getDeclaredAnnotation(ANNOTATION_TYPE);
            if (annotation != null) {
                w2.setPropertyValue(
                        descriptor.getName(),
                        doConvert(array[annotation.index()], field.getType())
                );
            }
        } catch (NoSuchFieldException e) {
            // nop
        }
    }

    private static Object doConvert(String source, Class<?> targetType) {
        if (ClassUtils.isAssignable(String.class, targetType)) {
            return source;
        }

        if (ClassUtils.isAssignable(Long.class, targetType)) {
            try {
                return Long.parseLong(source);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        if (ClassUtils.isAssignable(Double.class, targetType)) {
            try {
                return Double.parseDouble(source);
            } catch (NumberFormatException e) {
                return null;
            }
        }

        if (ClassUtils.isAssignable(BigDecimal.class, targetType)) {
            try {
                return new BigDecimal(source);
            } catch (Exception e) {
                return null;
            }
        }

        if (ClassUtils.isAssignable(BigInteger.class, targetType)) {
            try {
                return new BigInteger(source);
            } catch (Exception e) {
                return null;
            }
        }

        try {
            return ConversionUtils.convert(source, targetType);
        } catch (Exception e) {
            return null;
        }
    }

}
