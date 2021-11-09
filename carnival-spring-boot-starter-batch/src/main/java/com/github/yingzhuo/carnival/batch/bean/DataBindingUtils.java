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
import org.springframework.format.Parser;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;
import org.springframework.format.datetime.DateTimeFormatAnnotationFormatterFactory;
import org.springframework.format.number.NumberFormatAnnotationFormatterFactory;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;

/**
 * 数据绑定工具
 *
 * @author 应卓
 * @see ArrayIdx
 * @since 1.10.35
 */
@Slf4j
public final class DataBindingUtils {

    private static final Class<ArrayIdx> ANNOTATION_TYPE = ArrayIdx.class;
    private static final Class<DateTimeFormat> DATE_TIME_FORMAT_ANNOTATION_TYPE = DateTimeFormat.class;
    private static final Class<NumberFormat> NUMBER_FORMAT_ANNOTATION_TYPE = NumberFormat.class;
    private static final DateTimeFormatAnnotationFormatterFactory DATE_TIME_FORMAT_ANNOTATION_FORMATTER_FACTORY
            = new DateTimeFormatAnnotationFormatterFactory();
    private static final NumberFormatAnnotationFormatterFactory NUMBER_FORMAT_ANNOTATION_FORMATTER_FACTORY
            = new NumberFormatAnnotationFormatterFactory();

    private DataBindingUtils() {
    }

    public static <T> T bind(T obj, List<String> list) {
        if (obj == null || list == null || list.isEmpty()) {
            return obj;
        }
        return bind(obj, list.toArray(new String[0]));
    }

    public static <T> T bind(T obj, String[] array) {

        if (obj == null || array == null || array.length == 0) {
            return obj;
        }

        final BeanWrapper wrapper1 = new BeanWrapperImpl(obj);
        final DirectFieldAccessor wrapper2 = new DirectFieldAccessor(obj);

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
            // nop
        }

        return obj;
    }

    private static void doBind(Class<?> beanType, PropertyDescriptor descriptor, BeanWrapper w1, DirectFieldAccessor w2, String[] array) {
        Method writeMethod = descriptor.getWriteMethod();
        ArrayIdx annotation = writeMethod != null ? writeMethod.getDeclaredAnnotation(ANNOTATION_TYPE) : null;

        if (annotation != null) {
            w1.setPropertyValue(
                    descriptor.getName(),
                    doConvert(
                            array[annotation.value()],
                            writeMethod.getParameterTypes()[0],
                            writeMethod.getDeclaredAnnotation(DATE_TIME_FORMAT_ANNOTATION_TYPE),
                            writeMethod.getDeclaredAnnotation(NUMBER_FORMAT_ANNOTATION_TYPE)
                    )
            );
            return;
        }

        try {
            Field field = beanType.getDeclaredField(descriptor.getName());
            annotation = field.getDeclaredAnnotation(ANNOTATION_TYPE);
            if (annotation != null) {
                w2.setPropertyValue(
                        descriptor.getName(),
                        doConvert(
                                array[annotation.value()],
                                field.getType(),
                                field.getDeclaredAnnotation(DATE_TIME_FORMAT_ANNOTATION_TYPE),
                                field.getDeclaredAnnotation(NUMBER_FORMAT_ANNOTATION_TYPE)
                        )
                );
            }
        } catch (NoSuchFieldException e) {
            // nop
        }
    }

    private static Object doConvert(String source, Class<?> targetType, DateTimeFormat dateTimeFormat, NumberFormat numberFormat) {
        if (!ConversionUtils.canConvert(String.class, targetType)) {
            log.warn("Cannot convert java.lang.String to {}.", targetType);
            return null;
        } else {
            try {
                return ConversionUtils.convert(source, targetType);
            } catch (Exception e) {
                if (dateTimeFormat != null) {
                    return doConvertForDateTime(source, targetType, dateTimeFormat);
                } else if (numberFormat != null) {
                    return doConvertForNumber(source, targetType, numberFormat);
                } else {
                    return null;
                }
            }
        }
    }

    private static Object doConvertForDateTime(String source, Class<?> targetType, DateTimeFormat annotation) {
        try {
            Parser<?> parser = DATE_TIME_FORMAT_ANNOTATION_FORMATTER_FACTORY.getParser(annotation, targetType);
            return parser.parse(source, Locale.getDefault());
        } catch (ParseException e) {
            return null;
        }
    }

    private static Object doConvertForNumber(String source, Class<?> targetType, NumberFormat annotation) {
        try {
            Parser<?> parser = NUMBER_FORMAT_ANNOTATION_FORMATTER_FACTORY.getParser(annotation, targetType);
            return parser.parse(source, Locale.getDefault());
        } catch (Exception e) {
            return null;
        }
    }

}
