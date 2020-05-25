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
import lombok.val;
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

/**
 * @author 应卓
 * @since 1.6.10
 */
public class FastDfsHealthIndicator extends AbstractHealthIndicator {

    @Override
    protected void doHealthCheck(Health.Builder builder) {

        try (InputStream file = new ByteArrayInputStream(new byte[]{(byte) 0xFF})) {
            val path = FastDFS.upload(file, 1L, "");
            FastDFS.delete(path);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        }
    }

}
