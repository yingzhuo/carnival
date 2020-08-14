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
import org.hashids.Hashids;

/**
 * @author 应卓
 * @since 1.7.2
 */
public class IdHashImpl implements IdHash {

    private final Hashids hashids;

    public IdHashImpl(String salt, int minLength) {
        this.hashids = new Hashids(salt, minLength);
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
