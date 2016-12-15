package znt.look;

import com.alibaba.fastjson.JSON;
import org.apache.curator.framework.CuratorFramework;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import znt.zk.ServerNodeConfig;
import znt.zk.ZkClient;

import java.util.ArrayList;
import java.util.List;

/**
 * @author elviswang
 * @date 2016/12/14
 * @time 11:30
 * Desc TODO
 */
public class ZkLookupService implements LookupService {
    private static final Logger LOG = LoggerFactory.getLogger(ZkLookupService.class);

    private static final String PREFIX = "/znt/rpc/";
    private static final String magic = "ZNT";

    public List<ServerNodeConfig> lookupServiceNode(String serviceName) {
        List<ServerNodeConfig> nodeConfigs = new ArrayList<ServerNodeConfig>();

        try {
            CuratorFramework client = ZkClient.createClient();
            String path = PREFIX + "ZNT_" + serviceName;
            List<String> children = client.getChildren().forPath(path);

            if (null == children || children.size() == 0) {
                LOG.warn("Zk children node is null ,path = {}", path);
                return nodeConfigs;
            }
            for (String node : children) {
                String fullPath = path + "/" + node;
                byte[] data = client.getData().forPath(fullPath);
                ServerNodeConfig serverNodeConfig = JSON.parseObject(new String(data), ServerNodeConfig.class);
                nodeConfigs.add(serverNodeConfig);
            }
        } catch (Exception e) {
            throw new RuntimeException("lookup zk node error ", e);
        }
        return nodeConfigs;
    }
}
