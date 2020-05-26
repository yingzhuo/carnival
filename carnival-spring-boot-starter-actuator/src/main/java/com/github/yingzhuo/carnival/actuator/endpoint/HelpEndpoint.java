/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.endpoint;

import com.github.yingzhuo.carnival.actuator.util.MarkdownEntityFactory;
import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author 应卓
 * @since 1.6.8
 */
@Endpoint(id = "help")
public class HelpEndpoint extends AbstractEndpoint {

    private static final String[] LOCATIONS = new String[]{
            "classpath:help.md",
            "classpath:HELP.md",
            "classpath:META-INF/help.md",
            "classpath:META-INF/HELP.md"
    };

    private String html = "<h2>Help</h2>";

    public HelpEndpoint() {
        ResourceOptional resourceOptional = ResourceOptional.of(LOCATIONS);

        if (resourceOptional.isPresent()) {
            String text = resourceOptional.toResourceText().getText();
            html = MarkdownEntityFactory.create(text).toString();
        }
    }

    @ReadOperation(produces = PRODUCE_HTML)
    public String read() {
        return html;
    }

}
