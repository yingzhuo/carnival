/*
 *  ____    _    ____  _   _ _____     ___    _
 * / ___|  / \  |  _ \| \ | |_ _\ \   / / \  | |
 * | |    / _ \ | |_) |  \| || | \ \ / / _ \ | |
 * | |___/ ___ \|  _ <| |\  || |  \ V / ___ \| |___
 * \____/_/   \_\_| \_\_| \_|___|  \_/_/   \_\_____|
 *
 * https://github.com/yingzhuo/carnival
 */
package com.github.yingzhuo.carnival.etcd.watch;

import io.etcd.jetcd.Response;
import io.etcd.jetcd.Watch;
import io.etcd.jetcd.watch.WatchEvent;
import io.etcd.jetcd.watch.WatchResponse;

import java.util.List;

/**
 * @author 应卓
 * @since 1.6.11
 */
public abstract class AbstractListener implements Watch.Listener {

    @Override
    public final void onNext(WatchResponse response) {
        Response.Header header = response.getHeader();
        List<WatchEvent> watchEvents = response.getEvents();
        onNext(header, watchEvents);
    }

    public void onNext(Response.Header header, List<WatchEvent> watchEvents) {
    }

    @Override
    public void onError(Throwable throwable) {
    }

    @Override
    public void onCompleted() {
    }

}
