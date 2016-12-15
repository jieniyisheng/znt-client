package znt.look;

import znt.zk.ServerNodeConfig;

import java.util.List;

/**
 * @author elviswang
 * @date 2016/12/14
 * @time 11:28
 * Desc TODO
 */
public interface LookupService {

    List<ServerNodeConfig> lookupServiceNode(String serviceName);
}
