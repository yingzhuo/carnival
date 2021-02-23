/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.etcd;

import com.github.yingzhuo.carnival.etcd.exception.ETCDException;
import com.github.yingzhuo.carnival.etcd.exception.NotUniqueValueException;
import com.github.yingzhuo.carnival.spring.SpringUtils;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.options.PutOption;
import io.etcd.jetcd.options.WatchOption;
import lombok.val;

import java.nio.charset.Charset;
import java.time.Duration;

/**
 * @author 应卓
 * @since 1.6.11
 */
public final class ETCD {

    static Charset charset;

    private ETCD() {
    }

    public static void put(String key, String value) {
        val cli = SpringUtils.getBean(Client.class);
        val kvCli = cli.getKVClient();

        try {
            kvCli.put(c(key), c(value)).get();
        } catch (Exception e) {
            throw new ETCDException(e);
        }
    }

    public static void put(String key, String value, Duration ttl) {
        val cli = SpringUtils.getBean(Client.class);
        val kvCli = cli.getKVClient();

        try {
            val leaseCli = cli.getLeaseClient();
            val leaseId = leaseCli.grant(ttl.getSeconds()).get().getID();
            kvCli.put(c(key), c(value),
                    PutOption.newBuilder()
                            .withLeaseId(leaseId)
                            .build())
                    .get();
        } catch (Exception e) {
            throw new ETCDException(e);
        }
    }

    public static String get(String key) {
        val cli = SpringUtils.getBean(Client.class);
        val kvCli = cli.getKVClient();

        try {
            val result = kvCli.get(c(key)).get();

            if (result.getCount() == 0) {
                return null;
            } else if (result.getCount() > 1) {
                throw new NotUniqueValueException();
            } else {
                return result.getKvs().get(0).getValue().toString(charset);
            }
        } catch (Exception e) {
            throw new ETCDException(e);
        }
    }

    public static void delete(String key) {
        val cli = SpringUtils.getBean(Client.class);
        val kvCli = cli.getKVClient();

        try {
            kvCli.delete(c(key)).get();
        } catch (Exception e) {
            throw new ETCDException(e);
        }
    }

    public static void watch(String key, Watch.Listener listener) {
        watch(key, null, listener);
    }

    public static void watch(String key, WatchOption option, Watch.Listener listener) {
        val cli = SpringUtils.getBean(Client.class);
        val watchCli = cli.getWatchClient();

        if (option == null) {
            option = WatchOption.DEFAULT;
        }

        watchCli.watch(c(key), option, listener);
    }

    private static ByteSequence c(String string) {
        return ByteSequence.from(string, charset);
    }

}
