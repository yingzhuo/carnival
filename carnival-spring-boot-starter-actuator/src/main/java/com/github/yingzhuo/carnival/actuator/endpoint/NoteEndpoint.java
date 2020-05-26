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
 * @since 1.6.5
 */
@Endpoint(id = "note")
public class NoteEndpoint extends AbstractEndpoint {

    private static final String[] LOCATIONS = new String[]{
            "classpath:note.md",
            "classpath:NOTE.md",
            "classpath:META-INF/note.md",
            "classpath:META-INF/NOTE.md"
    };

    private String html = "<h2>Note</h2>";

    public NoteEndpoint() {
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
