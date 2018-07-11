/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.datasource.aop;

import com.github.yingzhuo.carnival.datasource.DataSourceSwitch;
import com.github.yingzhuo.carnival.datasource.ForkDataSource;
import com.github.yingzhuo.carnival.datasource.SwitchListener;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
@Slf4j
@Aspect
public class DataSourceSwitchAdvice implements ApplicationContextAware {

    @Autowired(required = false)
    private DataSource dataSource;

    private ApplicationContext applicationContext;

    @Pointcut("@annotation(com.github.yingzhuo.carnival.datasource.DataSourceSwitch)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        final DataSourceSwitch annotation =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(DataSourceSwitch.class);

        if (!(dataSource instanceof ForkDataSource)) {
            return joinPoint.proceed();
        }

        final List<SwitchListener> listeners = getSwitchListeners(annotation.listeners());
        final String name = annotation.name();
        ForkDataSource cds = (ForkDataSource) dataSource;


        listeners.forEach(SwitchListener::before);
        cds.getRemote().setName(name);

        try {
            return joinPoint.proceed();
        } finally {
            listeners.forEach(SwitchListener::after);
        }
    }

    private List<SwitchListener> getSwitchListeners(Class<? extends SwitchListener>[] types) {
        if (types == null || types.length == 0) {
            return Collections.emptyList();
        }

        return Collections.unmodifiableList(Arrays.stream(types).map(SpringUtils::getBean).collect(Collectors.toList()));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}
