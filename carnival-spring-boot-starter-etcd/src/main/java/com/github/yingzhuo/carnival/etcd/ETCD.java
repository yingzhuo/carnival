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

import com.github.yingzhuo.carnival.spring.SpringUtils;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.options.PutOption;
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
            kvCli.put(to(key), to(value)).get();
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
            kvCli.put(ByteSequence.from(key, charset), ByteSequence.from(value, charset),
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
            val result = kvCli.get(to(key)).get();

            if (result.getCount() == 0) {
                return null;
            } else if (result.getCount() > 1) {
                // TODO:
                throw new RuntimeException();
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
            kvCli.delete(to(key)).get();
        } catch (Exception e) {
            throw new ETCDException(e);
        }
    }

    private static ByteSequence to(String string) {
        return ByteSequence.from(string, charset);
    }

}
