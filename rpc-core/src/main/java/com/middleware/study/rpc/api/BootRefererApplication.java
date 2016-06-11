package com.middleware.study.rpc.api;

import com.middleware.study.rpc.annotation.ServiceReferer;
import com.middleware.study.rpc.config.RefererConfig;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/9 23:55
 */
public class BootRefererApplication {
    private static Logger logger = LoggerFactory.getLogger(BootRefererApplication.class);
    private static Map<Class<?>, Object> refsMap = new HashMap<>();
    public static void init(String packagePath){
        Reflections reflections = new Reflections(packagePath);
        Set<Class<?>> refs = reflections.getTypesAnnotatedWith(ServiceReferer.class);
        for (Class<?> ref : refs){
            ServiceReferer annotation = ref.getAnnotation(ServiceReferer.class);
            Class<?> serviceInterface = annotation.serviceInterface();
            String registryUrl = annotation.registryUrl();
            RefererConfig refererConfig = new RefererConfig(serviceInterface, registryUrl);
            Object instance = refererConfig.getRef();
            refsMap.put(serviceInterface, instance);
        }
    }

    public static Object getRef(Class<?> clz){
        return refsMap.get(clz);
    }
}
