/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs;

import com.github.yingzhuo.carnival.fastdfs.domain.conn.ConnectionFactory;
import com.github.yingzhuo.carnival.fastdfs.domain.conn.ConnectionPool;
import com.github.yingzhuo.carnival.fastdfs.domain.conn.StorageConnectionManager;
import com.github.yingzhuo.carnival.fastdfs.domain.conn.TrackerConnectionManager;
import com.github.yingzhuo.carnival.fastdfs.properties.*;
import com.github.yingzhuo.carnival.fastdfs.service.*;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.jmx.support.RegistrationPolicy;
import org.springframework.util.StringUtils;

import java.util.stream.Collectors;

/**
 * 自动配置
 *
 * @author tobato
 * @author 应卓
 */
@Configuration
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
@EnableConfigurationProperties({
        RootProperties.class,
        PoolProperties.class,
        TrackerProperties.class,
        WebProperties.class,
        ThumbImageProperties.class
})
@ConditionalOnProperty(prefix = "carnival.fastdfs", name = "enabled", havingValue = "true", matchIfMissing = true)
public class FastDfsClientAutoConfig {

    @Bean
    @ConditionalOnMissingBean
    public ConnectionFactory connectionFactory(RootProperties rootConfig) {
        return new ConnectionFactory(
                rootConfig.getSoTimeout(),
                rootConfig.getConnectTimeout(),
                rootConfig.getCharset()
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public ConnectionPool connectionPool(ConnectionFactory factory, PoolProperties poolConfig) {
        return new ConnectionPool(factory, poolConfig);
    }

    @Bean
    @ConditionalOnMissingBean
    public StorageConnectionManager storageConnectionManager(ConnectionPool pool) {
        return new StorageConnectionManager(pool);
    }

    @Bean
    @ConditionalOnMissingBean
    public TrackerConnectionManager trackerConnectionManager(ConnectionPool pool, TrackerProperties trackerConfig) {
        return new TrackerConnectionManager(
                pool,
                trackerConfig.getNodes().parallelStream()
                        .filter(StringUtils::hasText)
                        .map(String::trim)
                        .collect(Collectors.toList())
        );
    }

    @Bean
    @ConditionalOnMissingBean
    public TrackerClient trackerClient(TrackerConnectionManager manager, RootProperties rootConfig) {
        TrackerClient bean = new TrackerClientImpl(manager);
        return rootConfig.isSafeMode() ? new TrackerClientImplSafe(bean) : bean;
    }

    @Bean
    @ConditionalOnMissingBean
    public GenerateStorageClient generateStorageClient(TrackerClient client, StorageConnectionManager manager) {
        return new GenerateStorageClientImpl(client, manager);
    }

    @Bean
    @ConditionalOnMissingBean
    public AppendFileStorageClient appendFileStorageClient(TrackerClient client, StorageConnectionManager manager) {
        return new AppendFileStorageClientImpl(client, manager);
    }

    @Bean
    @ConditionalOnMissingBean
    public FastFileStorageClient fastFileStorageClient(TrackerClient client, StorageConnectionManager manager, ThumbImageProperties thumbImageConfig) {
        return new FastFileStorageClientImpl(
                client,
                manager,
                thumbImageConfig.getDefaultWidth(),
                thumbImageConfig.getDefaultHeight()
        );
    }

}
