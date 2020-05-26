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

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author 应卓
 * @since 1.6.10
 */
@Getter
@Setter
public class MarkdownEntity {

    static String TAG_WIDTH = "<style type=\"text/css\"> %s { width:85%%} </style>";

    // css 样式
    private String css;

    // 最外网的div标签， 可以用来设置样式，宽高，字体等
    private Map<String, String> divStyle = new ConcurrentHashMap<>();

    // 转换后的html文档
    private String html;

    public MarkdownEntity() {
    }

    public MarkdownEntity(String html) {
        this.html = html;
    }

    @Override
    public String toString() {
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

    public void addDivStyle(String attrKey, String value) {
        if (divStyle.containsKey(attrKey)) {
            divStyle.put(attrKey, divStyle.get(attrKey) + " " + value);
        } else {
            divStyle.put(attrKey, value);
        }
    }

    public void addWidthCss(String tag) {
        String wcss = String.format(TAG_WIDTH, tag);
        css += wcss;
    }
}
