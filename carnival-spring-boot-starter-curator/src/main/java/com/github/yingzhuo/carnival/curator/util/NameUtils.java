/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.curator.util;

import com.google.common.base.Preconditions;
import lombok.val;
import org.apache.commons.lang3.StringUtils;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.3.3
 */
public final class NameUtils extends AbstractUtils {

    private NameUtils() {
    }

    public static Long getNameAsLong(Class<?> entityType) {
        String name = getName(entityType);
        return Long.parseLong(StringUtils.stripStart(name, "0"));
    }

    public static String getName(Class<?> entityType) {
        Preconditions.checkArgument(entityType != null);
        return getName(entityType.getName(), true);
    }

    public static String getName(String path) {
        return getName(path, true);
    }

    public static long getNameAsLong(String path) {
        String name = getName(path, true);
        return Long.parseLong(StringUtils.stripStart(name, "0"));
    }

    // ------------------------------------------------------------------------------------------------------------

    public static String getName(String path, boolean removePath) {
        Preconditions.checkArgument(path != null);

        try {
            path = betterPath(path);

            CuratorFramework cli = getClient();

            val result = cli.create()
                    .creatingParentContainersIfNeeded()
                    .withMode(CreateMode.PERSISTENT_SEQUENTIAL)
                    .forPath(path);

            String name = new String(result.getBytes(UTF_8));
            return removePath ? name.substring(path.length()) : name;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
