package com.middleware.study.rpc.api;

import com.middleware.study.rpc.annotation.ServiceExporter;
import com.middleware.study.rpc.config.ServiceConfig;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.UnknownHostException;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/9 23:51
 */
public class BootExporterApplication {
    private static Logger logger = LoggerFactory.getLogger(BootExporterApplication.class);
    public static void export(String packagePath){
        Reflections reflections = new Reflections(packagePath);
        Set<Class<?>> services = reflections.getTypesAnnotatedWith(ServiceExporter.class);
        for (Class<?> service : services){
            ServiceExporter annotation = service.getAnnotation(ServiceExporter.class);
            Class<?> serviceInterface = annotation.serviceInterface();
            Class<?> serviceImpl = annotation.serviceImpl();
            int port = annotation.exportPort();
            String registryUrl = annotation.registryUrl();
            try {
                ServiceConfig serviceConfig = new ServiceConfig(serviceInterface.getName(), serviceImpl.newInstance(), port, registryUrl);
                serviceConfig.export();
            } catch (UnknownHostException e) {
                logger.error(e.getMessage(), e);
            } catch (InstantiationException e) {
                logger.error(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
