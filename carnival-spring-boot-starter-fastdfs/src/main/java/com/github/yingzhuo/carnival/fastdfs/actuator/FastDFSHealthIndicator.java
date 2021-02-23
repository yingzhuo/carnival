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
import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;

import java.io.ByteArrayInputStream;

/**
 * @author 应卓
 * @since 1.6.10
 */
public class FastDFSHealthIndicator extends AbstractHealthIndicator {

    private static final byte[] FILE_CONTENT = new byte[]{
            (byte) 0,
    };

    private static final long FILE_LENGTH = FILE_CONTENT.length;

    @Override
    protected void doHealthCheck(Health.Builder builder) {

        try {
            String filePath = FastDFS.upload(new ByteArrayInputStream(FILE_CONTENT), FILE_LENGTH, "tmp");
            FastDFS.delete(filePath);
            builder.up();
        } catch (Exception e) {
            builder.down(e);
        }
    }

}
