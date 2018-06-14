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

import com.github.yingzhuo.carnival.datasource.CompositeDataSource;
import com.github.yingzhuo.carnival.datasource.DataSourceSwitch;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;

import javax.sql.DataSource;

/**
 * @author 应卓
 */
@Slf4j
@Aspect
public class DataSourceSwitchAdvice {

    @Autowired(required = false)
    private DataSource dataSource;

    @Pointcut("@annotation(com.github.yingzhuo.carnival.datasource.DataSourceSwitch)")
    public void pointcut() {
    }

    @Around("pointcut()")
    public Object before(ProceedingJoinPoint joinPoint) throws Throwable {

        final DataSourceSwitch annotation =
                ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(DataSourceSwitch.class);

        if (dataSource instanceof CompositeDataSource && annotation != null) {
            final String name = annotation.name();
            log.debug("annotation.name() == {}", name);
            CompositeDataSource cds = (CompositeDataSource) dataSource;
            cds.getRemote().setName(name);
        } else {
            log.debug("annotation == null or dataSource is NOT a CompositeDataSource's instance.");
        }

        return joinPoint.proceed();
    }

}
