/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.document;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * @author 应卓
 * @since 1.6.11
 */
public interface DocumentConfigurer {

    public default String rootPath() {
        return "/document";
    }

    public default DocumentMap documentMap() {
        return DocumentMap.newInstance();
    }

    public default Charset charset() {
        return StandardCharsets.UTF_8;
    }

}
