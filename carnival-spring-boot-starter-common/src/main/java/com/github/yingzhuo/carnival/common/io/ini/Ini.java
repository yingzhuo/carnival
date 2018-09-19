/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.common.io.ini;

import com.github.yingzhuo.carnival.common.util.StringUtils;
import lombok.val;
import org.apache.commons.io.FileUtils;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author 应卓
 */
public final class Ini implements Map<String, String>, Iterable<Map.Entry<String, String>> {

    private final Map<String, String> delegate;
    private final List<String> sections;

    public Ini(Resource resource) {

        val _sections = new LinkedList<String>();
        val _delegate = new TreeMap<String, String>();

        try {
            val lines = FileUtils.readLines(resource.getFile(), StandardCharsets.UTF_8)
                    .stream()
                    .filter(IniUtils::isNotBlank)              // 删除空白行
                    .map(IniUtils::removeCommentsAndTrim)      // 删除注释
                    .collect(Collectors.toList());

            String section = null;

            for (final String line : lines) {
                if (line.startsWith("[") && line.endsWith("]")) {
                    section = line.substring(1, line.length() - 1);
                    _sections.add(section);
                } else {

                    if (StringUtils.isNotBlank(line)) {
                        val optionAndValue = line.split("=");
                        if (optionAndValue == null || optionAndValue.length != 2 || section == null) {
                            throw new IniConfigException();
                        }

                        val option = optionAndValue[0].trim();
                        val value = optionAndValue[1].trim();

                        _delegate.put(section + "." + option, value);
                    }
                }
            }

            delegate = Collections.unmodifiableMap(_delegate);
            sections = Collections.unmodifiableList(_sections);

            try {
                resource.getInputStream().close();
            } catch (IOException e) {
                // NOP
            }

        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // ---------------------------------------------------------------------------------------------------------------

    public List<String> getSections() {
        return this.sections;
    }

    @Override
    public Iterator<Entry<String, String>> iterator() {
        return delegate.entrySet().iterator();
    }

    @Override
    public int size() {
        return this.delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return this.delegate.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return this.delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return this.delegate.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return this.delegate.get(key);
    }

    @Override
    public String put(String key, String value) {
        return this.delegate.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return this.delegate.remove(key);
    }

    @Override
    public void putAll(Map<? extends String, ? extends String> m) {
        this.delegate.putAll(m);
    }

    @Override
    public void clear() {
        this.delegate.clear();
    }

    @Override
    public Set<String> keySet() {
        return this.delegate.keySet();
    }

    @Override
    public Collection<String> values() {
        return this.delegate.values();
    }

    @Override
    public Set<Entry<String, String>> entrySet() {
        return this.delegate.entrySet();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ini ini = (Ini) o;
        return Objects.equals(delegate, ini.delegate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delegate);
    }
}
