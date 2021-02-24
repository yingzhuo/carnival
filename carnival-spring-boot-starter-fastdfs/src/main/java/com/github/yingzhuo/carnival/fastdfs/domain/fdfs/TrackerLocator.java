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

import com.github.yingzhuo.carnival.fastdfs.exception.FastDFSUnavailableException;
import org.apache.commons.lang3.StringUtils;

import java.net.InetSocketAddress;
import java.util.*;

/**
 * 表示Tracker服务器位置
 * <pre>
 * 支持负载均衡对IP轮询
 * </pre>
 *
 * @author tobato
 */
public class TrackerLocator {

    private static final int DEFAULT_RETRY_AFTER_SECOND = 10 * 60;

    private List<String> trackerList = new ArrayList<>();

    private Map<InetSocketAddress, TrackerAddressHolder> trackerAddressMap = new HashMap<>();

    private CircularList<TrackerAddressHolder> trackerAddressCircular = new CircularList<>();

    private int retryAfterSecond = DEFAULT_RETRY_AFTER_SECOND;

    public TrackerLocator(List<String> trackerList) {
        super();
        this.trackerList = trackerList;
        buildTrackerAddresses();
    }

    private void buildTrackerAddresses() {
        Set<InetSocketAddress> addressSet = new HashSet<>();
        for (String item : trackerList) {
            if (StringUtils.isBlank(item)) {
                continue;
            }
            String[] parts = StringUtils.split(item, ":", 2);
            if (parts.length != 2) {
                throw new IllegalArgumentException(
                        "the value of item \"tracker_server\" is invalid, the correct format is host:port");
            }
            InetSocketAddress address = new InetSocketAddress(parts[0].trim(), Integer.parseInt(parts[1].trim()));
            addressSet.add(address);
        }
        // 放到轮询圈
        for (InetSocketAddress item : addressSet) {
            TrackerAddressHolder holder = new TrackerAddressHolder(item);
            trackerAddressCircular.add(holder);
            trackerAddressMap.put(item, holder);
        }

    }

    public void setRetryAfterSecond(int retryAfterSecond) {
        this.retryAfterSecond = retryAfterSecond;
    }

    public InetSocketAddress getTrackerAddress() {
        TrackerAddressHolder holder;
        // 遍历连接地址,抓取当前有效的地址
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            holder = trackerAddressCircular.next();
            if (holder.canTryToConnect(retryAfterSecond)) {
                return holder.getAddress();
            }
        }
        throw new FastDFSUnavailableException("找不到可用的tracker " + getTrackerAddressConfigString());
    }

    private String getTrackerAddressConfigString() {
        StringBuffer config = new StringBuffer();
        for (int i = 0; i < trackerAddressCircular.size(); i++) {
            TrackerAddressHolder holder = trackerAddressCircular.next();
            InetSocketAddress address = holder.getAddress();
            config.append(address.toString()).append(",");
        }
        return new String(config);
    }

    public void setActive(InetSocketAddress address) {
        TrackerAddressHolder holder = trackerAddressMap.get(address);
        holder.setActive();
    }

    public void setInActive(InetSocketAddress address) {
        TrackerAddressHolder holder = trackerAddressMap.get(address);
        holder.setInActive();
    }

    public List<String> getTrackerList() {
        return Collections.unmodifiableList(trackerList);
    }

    public void setTrackerList(List<String> trackerList) {
        this.trackerList = trackerList;
    }

}
