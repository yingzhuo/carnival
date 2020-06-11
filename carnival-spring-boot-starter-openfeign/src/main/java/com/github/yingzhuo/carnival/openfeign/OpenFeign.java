/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign;

import com.github.yingzhuo.carnival.openfeign.props.LoggerProps;
import com.github.yingzhuo.carnival.openfeign.props.RootProps;
import feign.Client;
import feign.Feign;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.slf4j.Slf4jLogger;
import feign.spring.SpringContract;
import lombok.val;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;

/**
 * @author 应卓
 * @since 1.6.16
 */
public final class OpenFeign {

    public static Feign.Builder builder(ApplicationContext applicationContext) {
        return new Builder(applicationContext);
    }

    private OpenFeign() {
    }

    public static class Builder extends Feign.Builder {

        private final ApplicationContext applicationContext;

        private Builder(ApplicationContext applicationContext) {
            super();
            this.applicationContext = applicationContext;
            initClient();
            initLogger();
            initEncoder();
            initDecoder();
            initAnnotationStyle();
        }

        private void initClient() {
            try {
                val client = applicationContext.getBean(Client.class);
                super.client(client);
            } catch (NoSuchBeanDefinitionException e) {
                // NoOP
            }
        }

        private void initLogger() {
            val props = applicationContext.getBean(LoggerProps.class);
            super.logger(new Slf4jLogger(props.getName()));
            super.logLevel(props.getLevel());
        }

        private void initEncoder() {
            val encoder = applicationContext.getBean(Encoder.class);
            super.encoder(encoder);
        }

        private void initDecoder() {
            val decoder = applicationContext.getBean(Decoder.class);
            super.decoder(decoder);
        }

        private void initAnnotationStyle() {
            val props = applicationContext.getBean(RootProps.class);

            if (props.getAnnotationStyle() == RootProps.AnnotationStyle.SPRING) {
                val contract = applicationContext.getBean(SpringContract.class);
                super.contract(contract);
            }
        }
    }

}
