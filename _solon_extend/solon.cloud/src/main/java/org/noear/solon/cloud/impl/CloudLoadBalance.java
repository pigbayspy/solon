package org.noear.solon.cloud.impl;

import org.noear.solon.Utils;
import org.noear.solon.cloud.CloudClient;
import org.noear.solon.core.LoadBalance;
import org.noear.solon.cloud.model.Discovery;

/**
 * 负载均衡
 *
 * @author noear
 * @since 1.2
 */
public class CloudLoadBalance implements LoadBalance {
    private static CloudLoadStrategy strategy = new CloudLoadStrategyDefault();

    /**
     * 获取负载策略
     */
    public static CloudLoadStrategy getStrategy() {
        return strategy;
    }

    /**
     * 设置负载策略
     */
    public static void setStrategy(CloudLoadStrategy strategy) {
        if (strategy != null) {
            CloudLoadBalance.strategy = strategy;
        }
    }

    ////////////////

    private final String service;
    private final String group;
    private Discovery discovery;
    private LoadBalance loadBalance;

    public CloudLoadBalance(String group, String service) {
        this.service = service;
        this.group = group;

        if (CloudClient.discovery() != null) {
            discovery = CloudClient.discovery().find(group, service);

            CloudClient.discovery().attention(group, service, d1 -> {
                discovery = d1;
            });
        }
    }

    public CloudLoadBalance(String group, String service, Discovery discovery) {
        this.service = service;
        this.group = group;
        this.discovery = discovery;
    }

    public CloudLoadBalance(String group, String service, LoadBalance loadBalance) {
        this.service = service;
        this.group = group;
        this.loadBalance = loadBalance;
    }

    /**
     * 服务组
     */
    public String getGroup() {
        return group;
    }

    /**
     * 服务名
     */
    public String getService() {
        return service;
    }

    /**
     * 服务发现数据
     */
    public Discovery getDiscovery() {
        return discovery;
    }

    @Override
    public String getServer() {
        if (loadBalance != null) {
            return loadBalance.getServer();
        }

        if (discovery == null) {
            return null;
        } else {
            if (Utils.isNotEmpty(discovery.agent())) {
                return discovery.agent();
            } else {
                int count = discovery.clusterSize();

                if (count == 0) {
                    return null;
                } else {
                    return getStrategy().getServer(discovery);
                }
            }
        }
    }
}
