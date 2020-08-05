/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.mustache;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import org.springframework.util.DigestUtils;

import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * @author 应卓
 * @since 1.6.33
 */
public class MustacheBeanImpl implements MustacheBean {

    @Override
    public String render(String templateContent, Map<String, Object> context) {
        Writer writer = new StringWriter();
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile(new StringReader(templateContent), getName(templateContent));
        mustache.execute(writer, context);
        return writer.toString();
    }

    private String getName(String templateContent) {
        return DigestUtils.md5DigestAsHex(templateContent.getBytes(UTF_8));
    }

}
