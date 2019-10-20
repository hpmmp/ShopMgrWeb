package com.shop.web.service;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DubboServiceFactory {

    private static ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(new String[]{"shopMgrConsumer.xml"});

    static {
        context.start();
    }

    public static <T> T get(Class<T> t) {
        if (!context.isRunning()) {
            context.start();
        }
        return context.getBean(t);
    }
}
