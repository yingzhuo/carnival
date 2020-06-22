/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.openfeign.autoconfig;

import com.github.yingzhuo.carnival.openfeign.FeignFormatterRegistrar;
import com.github.yingzhuo.carnival.openfeign.contract.AnnotatedParameterProcessor;
import com.github.yingzhuo.carnival.openfeign.contract.SpringContract;
import com.github.yingzhuo.carnival.openfeign.retryer.NeverRetryer;
import feign.Client;
import feign.Contract;
import feign.Request;
import feign.Retryer;
import feign.codec.Decoder;
import feign.codec.Encoder;
import feign.codec.ErrorDecoder;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.NoUniqueBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.format.support.DefaultFormattingConversionService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author 应卓
 * @since 1.6.21
 */
final class FeignComponentFinder {

    public static Optional<Client> client(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Client.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign client found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();
        }
    }

    public static Optional<Encoder> encoder(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Encoder.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign encoder found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.of(new JacksonEncoder());
        }
    }

    public static Optional<Decoder> decoder(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Decoder.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign decoder found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.of(new JacksonDecoder());
        }
    }

    public static Optional<ErrorDecoder> errorDecoder(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(ErrorDecoder.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign error decoder found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();
        }
    }

    public static Optional<Retryer> retryer(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Retryer.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign retryer found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.of(NeverRetryer.INSTANCE);
        }
    }

    public static Optional<Request.Options> options(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Request.Options.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign request options found in application context.");
        } catch (NoSuchBeanDefinitionException e) {
            return Optional.empty();
        }
    }

    public static Optional<Contract> contract(ApplicationContext applicationContext) {
        try {
            return Optional.of(applicationContext.getBean(Contract.class));
        } catch (NoUniqueBeanDefinitionException e) {
            throw new IllegalArgumentException("No unique feign contract found in application context.");
        } catch (NoSuchBeanDefinitionException e) {

            List<FeignFormatterRegistrar> registrars = new ArrayList<>(applicationContext.getBeansOfType(FeignFormatterRegistrar.class).values());
            List<AnnotatedParameterProcessor> processors = new ArrayList<>(applicationContext.getBeansOfType(AnnotatedParameterProcessor.class).values());

            final DefaultFormattingConversionService conversionService = new DefaultFormattingConversionService();
            for (FeignFormatterRegistrar feignFormatterRegistrar : registrars) {
                feignFormatterRegistrar.registerFormatters(conversionService);
            }
            return Optional.of(new SpringContract(processors, conversionService));
        }
    }

}
