/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.nsq.producer;

import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * @author 应卓
 * @since 1.3.1
 */
public final class Messages implements Iterable<String> {

    public static Messages of(String... message) {
        Messages xs = new Messages();
        Collections.addAll(xs.messages, message);
        return xs;
    }

    private List<String> messages = new LinkedList<>();

    private Messages() {
    }

    public boolean isEmpty() {
        return messages.isEmpty();
    }

    public int size() {
        return messages.size();
    }

    @Override
    public Iterator<String> iterator() {
        return Collections.unmodifiableList(messages).iterator();
    }

    @Override
    public String toString() {
        return StringUtils.collectionToDelimitedString(messages, "\n");
    }

}
