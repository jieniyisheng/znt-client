package znt.proxy;

import org.apache.thrift.transport.TTransport;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author elviswang
 * @date 2016/12/14
 * @time 15:55
 * Desc TODO
 */
public class ZntInvocationHandler implements InvocationHandler {

    private Object subject;

    private TTransport transport;

    public ZntInvocationHandler(Object subject, TTransport transport) {
        this.subject = subject;
        this.transport = transport;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        transport.open();
        Object returnValue = method.invoke(subject, args);
        transport.close();
        return returnValue;
    }
}
