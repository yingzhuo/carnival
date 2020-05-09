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

import com.github.rjeschke.txtmark.Processor;
import com.github.yingzhuo.carnival.common.io.ResourceOptional;
import lombok.val;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author 应卓
 * @since 1.6.5
 */
@Endpoint(id = "note")
public class NoteEndpoint {

    private static final String[] LOCATIONS = new String[]{
            "classpath:note.md",
            "classpath:NOTE.md",
            "classpath:META-INF/note.md",
            "classpath:META-INF/NOTE.md"
    };

    private final String html;

    public NoteEndpoint() {
        val resourceOptional = ResourceOptional.of(LOCATIONS);

        if (resourceOptional.isPresent()) {
            val text = resourceOptional.toResourceText().getText();
            html = Processor.process(text);
        } else {
            html = "<h2>Note</h2>";
        }
    }

    @ReadOperation(produces = "text/html;charset=UTF-8")
    public String read() {
        return html;
    }

}
