/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.id.hash;

import com.github.yingzhuo.carnival.id.IdHash;
import com.github.yingzhuo.codegen4j.Hashids;

/**
 * @author 应卓
 * @since 1.7.2
 */
public class IdHashImpl implements IdHash {

    private final Hashids hashids;

    public IdHashImpl(String salt, int minLength, String chars) {
        if (chars == null || "".equals(chars)) {
            this.hashids = new Hashids(salt, minLength);
        } else {
            this.hashids = new Hashids(salt, minLength, chars);
        }
    }

    @Override
    public String encode(long... ids) {
        return hashids.encode(ids);
    }

    @Override
    public long[] decode(String hash) {
        return hashids.decode(hash);
    }

}
