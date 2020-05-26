/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.actuator.util;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 应卓
 * @since 1.6.11
 */
public class MarkdownEntity {

    private final static String TAG_WIDTH = "<style type=\"text/css\"> %s { width:85%%} </style>";

    private String css;
    private Map<String, String> divStyle = new ConcurrentHashMap<>();
    private String html;

    MarkdownEntity() {
    }

    @Override
    public String toString() {
        return asHtml();
    }

    public String asHtml() {
        return css + "\n<div " + parseDiv() + ">\n" + html + "\n</div>";
    }

    private String parseDiv() {
        StringBuilder builder = new StringBuilder();
        for (Map.Entry<String, String> entry : divStyle.entrySet()) {
            builder.append(entry.getKey()).append("=\"")
                    .append(entry.getValue()).append("\" ");
        }
        return builder.toString();
    }

    void addDivStyle(String attrKey, String value) {
        if (divStyle.containsKey(attrKey)) {
            divStyle.put(attrKey, divStyle.get(attrKey) + " " + value);
        } else {
            divStyle.put(attrKey, value);
        }
    }

    void addWidthCss(String tag) {
        String wcss = String.format(TAG_WIDTH, tag);
        css += wcss;
    }

    void setCss(String css) {
        this.css = css;
    }

    void setHtml(String html) {
        this.html = html;
    }

}
