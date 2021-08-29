/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.graphql.autoconfig;

import java.nio.charset.Charset;

/**
 * 内部使用
 *
 * @author 应卓
 * @see com.github.yingzhuo.carnival.graphql.EnableGraphQL
 * @since 1.10.14
 */
public final class ConfigHolder {

    public static String url;
    public static String[] schemaLocations;
    public static Charset schemaCharset;

    private ConfigHolder() {
    }

}
