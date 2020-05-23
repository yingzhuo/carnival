/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.actuator;

import com.github.yingzhuo.carnival.fastdfs.FastDFS;
import com.github.yingzhuo.carnival.spring.ResourceLoaderUtils;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 应卓
 * @since 1.6.10
 */
public class FastDfsHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) throws IOException {
        final InputStream file = ResourceLoaderUtils.getResourceLoader()
                .getResource("classpath:/META-INF/1x1.png")
                .getInputStream();

        try {
            final String filePath = FastDFS.upload(file, "PNG");
            FastDFS.delete(filePath);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        }
    }

}
