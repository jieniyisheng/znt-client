package znt.loadbalancer;

import java.util.concurrent.ThreadLocalRandom;

/**
 * @author elviswang
 * @date 2016/12/14
 * @time 15:35
 * Desc TODO
 */
public class RandomLoadBalancer {

    public int selectIndex(int size){
        return ThreadLocalRandom.current().nextInt(size);
    }
}
