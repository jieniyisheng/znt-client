package znt.helper;

import org.apache.commons.lang3.StringUtils;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import znt.loadbalancer.RandomLoadBalancer;
import znt.look.ZkLookupService;
import znt.proxy.ZntInvocationHandler;
import znt.zk.ServerNodeConfig;

import java.lang.reflect.Constructor;
import java.lang.reflect.Proxy;
import java.util.List;

/**
 * @author elviswang
 * @date 2016/12/13
 * @time 16:43
 * Desc TODO
 */
public class ZntServiceHelper {

    public static <T> T getService(Class<T> clazz) {
        try {
            String thriftClassName = StringUtils.split(clazz.getName(), '$')[0];
            String serviceName = Thread.currentThread().getContextClassLoader().loadClass(thriftClassName).getSimpleName();
            List<ServerNodeConfig> nodeConfigsList = new ZkLookupService().lookupServiceNode(serviceName);

            ServerNodeConfig serverNodeConfig = loadBalancerSelect(nodeConfigsList);

            TTransport transport = new TSocket(serverNodeConfig.getHost(), serverNodeConfig.getPort());
            TProtocol protocol = new TBinaryProtocol(transport);

            Class clientClazz = Thread.currentThread().getContextClassLoader().loadClass(thriftClassName + "$Client");
            Constructor<T> cons = clientClazz.getConstructor(TProtocol.class);
            Object proxy = Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                    new Class[]{clazz}, new ZntInvocationHandler(cons.newInstance(protocol), transport));
            return clazz.cast(proxy);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static ServerNodeConfig loadBalancerSelect(List<ServerNodeConfig> nodeConfigsList) {
        int index = new RandomLoadBalancer().selectIndex(nodeConfigsList.size());
        return nodeConfigsList.get(index);
    }
}
