/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.restful.security.core;

import com.github.yingzhuo.carnival.restful.security.annotation.Requires;
import com.github.yingzhuo.carnival.spring.RequestMappingUtils;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.web.method.HandlerMethod;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @author 应卓
 * @since 1.1.5
 */
public final class ReflectCache {

    private static boolean initialized = false;
    private static Map<Method, List<MethodCheckPoint>> cache = new HashMap<>();

    public synchronized static void init() {
        if (!initialized) {
            final Collection<HandlerMethod> handlerMethods = RequestMappingUtils.getRequestMappingHandlerMapping().getHandlerMethods().values();

            for (HandlerMethod hm : handlerMethods) {
                final LinkedList<MethodCheckPoint> list = new LinkedList<>();

                for (Annotation typeAnnotation : hm.getBeanType().getDeclaredAnnotations()) {
                    Requires requires = typeAnnotation.annotationType().getAnnotation(Requires.class);
                    if (requires != null) {
                        list.addFirst(new MethodCheckPoint(typeAnnotation, SpringUtils.getBean(requires.value())));
                    }
                }

                final Method method = hm.getMethod();
                for (Annotation methodAnnotation : method.getDeclaredAnnotations()) {
                    Requires requires = methodAnnotation.annotationType().getAnnotation(Requires.class);
                    if (requires != null) {

                        Iterator<MethodCheckPoint> iterator = list.iterator();
                        while (iterator.hasNext()) {
                            final Class<?> cachedAnnotationClz = iterator.next().getAnnotation().getClass();
                            if (methodAnnotation.getClass() == cachedAnnotationClz) {
                                iterator.remove();
                            }
                        }

                        list.addFirst(new MethodCheckPoint(methodAnnotation, SpringUtils.getBean(requires.value())));
                    }
                }

                if (!list.isEmpty()) {
                    cache.put(method, list);
                }
            }

            cache = Collections.unmodifiableMap(cache);
            initialized = true;
        }
    }

    public static Map<Method, List<MethodCheckPoint>> get() {
        return cache;
    }

    private ReflectCache() {
    }

}
