/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache.util;

import com.github.yingzhuo.carnival.common.io.ResourceText;
import com.github.yingzhuo.carnival.mustache.MustacheBean;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import org.springframework.core.io.Resource;

import java.util.HashMap;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.33
 */
public final class MustacheUtils {

    private MustacheUtils() {
    }

    public static String fromResource(String location, Map<String, Object> context) {
        String template = ResourceText.of(location).getText();
        if (context == null) {
            context = new HashMap<>();
        }
        return SpringUtils.getBean(MustacheBean.class).render(template, context);
    }

    public static String fromResource(Resource resource, Map<String, Object> context) {
        String template = ResourceText.of(resource, UTF_8).getText();
        if (context == null) {
            context = new HashMap<>();
        }
        return SpringUtils.getBean(MustacheBean.class).render(template, context);
    }

}
