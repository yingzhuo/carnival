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
import com.github.yingzhuo.carnival.common.io.ResourceText;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;

/**
 * @author 应卓
 * @since 1.6.4
 */
@Endpoint(id = "description")
public class DescriptionEndpoint {

    private static final String DEFAULT_TEXT = "<h2>Description</h2>";

    private final String text;

    public DescriptionEndpoint(String markdownLocation) {
        if (markdownLocation == null) {
            this.text = DEFAULT_TEXT;
            return;
        }

        this.text = Processor.process(ResourceText.of(markdownLocation).getText());
    }

    @ReadOperation(produces = "text/html;charset=UTF-8")
    public String description() {
        return this.text;
    }

}
