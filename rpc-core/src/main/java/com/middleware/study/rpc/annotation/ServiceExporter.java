package com.middleware.study.rpc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author wuhaitao
 * @date 2016/6/9 23:15
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface ServiceExporter {
    Class<?> serviceInterface();
    Class<?> serviceImpl();
    int exportPort();
    String registryUrl();
}
