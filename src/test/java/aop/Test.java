package aop;

import aop.aop.DynamicProxy;
import aop.aop.ProxyService;
import aop.service.Service;
import aop.service.ServiceImpl;

import java.lang.reflect.Proxy;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-19 16:11
 */
public class Test {

    public static void main(String[] args) {
//        new ProxyService(new ServiceImpl()).hello();

        Service service = new ServiceImpl();
        Service proxy = (Service)Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), new Class[]{Service.class},
                new DynamicProxy(service));
        proxy.hello();

    }

}
