/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.zxing;

import java.util.Objects;

/**
 * @author 应卓
 */
public final class QRCode {

    public static QRCode of(String content) {
        return new QRCode(content);
    }

    private final String content;

    public QRCode(String content) {
        this.content = Objects.requireNonNull(content);
    }

    public String getContent() {
        return content;
    }

    @Override
    public String toString() {
        return "QRCode(" + getContent() + ")";
    }

}
