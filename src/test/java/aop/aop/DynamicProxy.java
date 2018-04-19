package aop.aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * desc
 *
 * @Author yjy
 * @Date 2018-04-19 16:20
 */
public class DynamicProxy implements InvocationHandler {

    private Object obj;

    public DynamicProxy(Object obj) {
        this.obj = obj;
    }

    public Object invoke(Object proxy, Method method, Object... args) throws Exception {
        Object res;
        System.out.println("before...");
        res = method.invoke(obj, args);
        System.out.println("after...");
        return res;
    }

}
