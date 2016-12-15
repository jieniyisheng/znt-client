package znt.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;

/**
 * @author elviswang
 * @date 2016/12/14
 * @time 11:32
 * Desc TODO
 */
public class ZkClient {
    private static final String CONNECT_STRING = "10.1.2.125:2181,10.1.2.125:2182,10.1.2.125:2183";

    public static CuratorFramework createClient() {
        CuratorFramework client = CuratorFrameworkFactory.builder().connectString(CONNECT_STRING)
                .sessionTimeoutMs(3000)
                .connectionTimeoutMs(3000)
                .canBeReadOnly(false)
                .retryPolicy(new ExponentialBackoffRetry(1000, Integer.MAX_VALUE))
                .defaultData(null)
                .build();
        client.start();
        return client;
    }
}
