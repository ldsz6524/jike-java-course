package io.kimmking.rpcfx.server;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import io.kimmking.rpcfx.api.RpcfxRequest;
import io.kimmking.rpcfx.api.RpcfxResolver;
import io.kimmking.rpcfx.api.RpcfxResponse;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatcher;
import net.bytebuddy.matcher.ElementMatchers;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class RpcfxInvoker {

    private RpcfxResolver resolver;

    public RpcfxInvoker(RpcfxResolver resolver) {
        this.resolver = resolver;
    }



    //尝试将服务端写死查找接口实现类变成泛型和反射；
    //尝试将客户端动态代理改成 AOP，添加异常处理；
    //尝试使用 Netty+HTTP 作为 client 端传输方式。

    public RpcfxResponse invoke(RpcfxRequest request) {
        RpcfxResponse response = new RpcfxResponse();
        String serviceClass = request.getServiceClass();

        // 作业1：改成泛型和反射
        Object service = resolver.resolve(serviceClass);//this.applicationContext.getBean(serviceClass);



        try {
            Class<?> aClass = Class.forName(serviceClass);
            final String name = aClass.getName();
            final ClassLoader classLoader = aClass.getClassLoader();
            final Method met = ReflectionUtils.findMethod(aClass, request.getMethod());
//            final Object o2 = ReflectionUtils.invokeMethod(met, aClass.newInstance());


//            final Class<?> loaded = new ByteBuddy().subclass(Object.class).make().load(aClass.getClassLoader()).getLoaded();
//            final Object o = loaded.newInstance();
//            final Method m = resolveMethodFromClass(loaded, request.getMethod());
//            final Object invoke = m.invoke(loaded.newInstance(), request.getParams());
//            System.err.println("====" + invoke);
            //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
            //Class<?> aClass = Class.forName(serviceClass);
            //Method method = resolveMethodFromClass(aClass, request.getMethod());
            //boolean anInterface = aClass.isInterface();
            //Class<?> superclass = aClass.getSuperclass();
            //ClassLoader parent = aClass.getClassLoader().getParent();
            //Object o = aClass.newInstance();
            //Object object = aClass.newInstance();//TODO
            //Object result = method.invoke(object, request.getParams()); // dubbo, fastjson,
            //&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&
            Method method = resolveMethodFromClass(service.getClass(), request.getMethod());
            Object result = method.invoke(service, request.getParams()); // dubbo, fastjson,
            // 两次json序列化能否合并成一个
            response.setResult(JSON.toJSONString(result, SerializerFeature.WriteClassName));
            response.setStatus(true);
            return response;
//        } catch (IllegalAccessException | InvocationTargetException e) {
        } catch (Exception e) {

            // 3.Xstream

            // 2.封装一个统一的RpcfxException
            // 客户端也需要判断异常
            e.printStackTrace();
            response.setException(e);
            response.setStatus(false);
            return response;
        }
    }

    private Method resolveMethodFromClass(Class<?> klass, String methodName) {
        return Arrays.stream(klass.getMethods()).filter(m -> methodName.equals(m.getName())).findFirst().get();
    }

}
