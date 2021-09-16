/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.support.handlebars;

import com.github.jknack.handlebars.Template;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 应卓
 * @since 1.10.20
 */
public final class Templates {

    Map<String, Template> map = new HashMap<>();

    Templates() {
        super();
    }

    public Template get(String location) {
        return map.get(location);
    }

}
