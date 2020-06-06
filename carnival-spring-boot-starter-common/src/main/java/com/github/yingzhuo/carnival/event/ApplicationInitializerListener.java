/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.event;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.core.OrderComparator;

/**
 * @author 应卓
 * @since 1.6.15
 */
@Slf4j
public class ApplicationInitializerListener implements ApplicationListener<ApplicationReadyEvent> {

    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {

        OrderComparator.sort(ApplicationInitializerHolder.INITIALIZERS);

        for (ApplicationInitializer initializer : ApplicationInitializerHolder.INITIALIZERS) {
            try {
                initializer.execute();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

}
