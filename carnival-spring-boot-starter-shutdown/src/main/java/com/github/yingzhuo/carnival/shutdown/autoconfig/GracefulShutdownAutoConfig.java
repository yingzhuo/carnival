/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.shutdown.autoconfig;

import com.github.yingzhuo.carnival.common.autoconfig.support.AnnotationAttributesHolder;
import com.github.yingzhuo.carnival.shutdown.EnableGracefulShutdown;
import com.github.yingzhuo.carnival.shutdown.GracefulShutdownHook;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.apache.tomcat.util.threads.ThreadPoolExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.OrderComparator;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.TimeUnit;

/**
 * @author 应卓
 * @since 1.3.1
 */
@ConditionalOnWebApplication
public class GracefulShutdownAutoConfig {

    @Autowired(required = false)
    private List<GracefulShutdownHook> hooks;

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory(GracefulShutdown gracefulShutdown) {
        final TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
        factory.addConnectorCustomizers(gracefulShutdown);
        return factory;
    }

    @Bean
    public GracefulShutdown gracefulShutdown() {
        Long timeout = AnnotationAttributesHolder.getValue(EnableGracefulShutdown.class, "timeout");
        TimeUnit timeoutTimeUnit = AnnotationAttributesHolder.getValue(EnableGracefulShutdown.class, "timeoutTimeUnit");

        if (hooks != null) {
            OrderComparator.sort(hooks);
        }

        final GracefulShutdown shutdown = new GracefulShutdown(timeout, timeoutTimeUnit);
        shutdown.setHooks(this.hooks);
        return shutdown;
    }

    @Slf4j
    static class GracefulShutdown implements
            ApplicationListener<ContextClosedEvent>,
            TomcatConnectorCustomizer {

        private volatile Connector connector;
        private final long timeout;
        private final TimeUnit timeoutTimeUnit;
        private List<GracefulShutdownHook> hooks;

        public GracefulShutdown(long timeout, TimeUnit timeoutTimeUnit) {
            this.timeout = timeout;
            this.timeoutTimeUnit = timeoutTimeUnit;
        }

        @Override
        public void customize(Connector connector) {
            this.connector = connector;
        }

        @Override
        public void onApplicationEvent(ContextClosedEvent event) {

            this.connector.pause();
            this.handleHooks(hooks);

            Executor executor = this.connector.getProtocolHandler().getExecutor();

            if (executor instanceof ThreadPoolExecutor) {
                ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
                threadPoolExecutor.shutdown();

                try {
                    threadPoolExecutor.awaitTermination(timeout, timeoutTimeUnit);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }

        void setHooks(List<GracefulShutdownHook> hooks) {
            this.hooks = hooks;
        }

        private void handleHooks(List<GracefulShutdownHook> hooks) {

            if (hooks != null && !hooks.isEmpty()) {
                for (GracefulShutdownHook hook : hooks) {
                    try {
                        hook.onShutdown();
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                    }
                }
            }
        }
    }

}
