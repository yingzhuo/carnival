/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.fastdfs.domain.fdfs;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 文件元数据
 *
 * @author tobato
 * @author 应卓
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
public class MetaData implements Serializable {

    /**
     * 名
     */
    private String name;
    /**
     * 值
     */
    private String value;

    public MetaData(String name) {
        this.name = name;
    }

    public MetaData(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public static MetaData of(String name, String value) {
        return new MetaData(name, value);
    }

    public static MetaData of(String name) {
        return new MetaData(name);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((value == null) ? 0 : value.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        MetaData other = (MetaData) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (value == null) {
            return other.value == null;
        } else return value.equals(other.value);
    }

}
