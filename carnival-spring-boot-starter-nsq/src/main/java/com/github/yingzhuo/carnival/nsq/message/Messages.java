/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.message;

import lombok.val;
import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.1
 */
public final class Messages implements Serializable {

    private final List<String> lines = new LinkedList<>();

    public static Messages of(String... msg) {
        val messages = new Messages();
        messages.lines.addAll(Arrays.asList(msg));
        return messages;
    }

    private Messages() {
    }

    @Override
    public String toString() {
        return StringUtils.join(lines, '\n');
    }
}
