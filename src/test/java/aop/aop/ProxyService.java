package aop.aop;

import aop.service.Service;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-19 16:06
 */
public class ProxyService implements Service {

    private Service service;

    public ProxyService(Service service) {
        this.service = service;
    }

    @Override
    public void hello() {
        System.out.println("before....");
        this.service.hello();
        System.out.println("after....");
    }
}
