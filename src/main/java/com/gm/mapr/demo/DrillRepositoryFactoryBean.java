package com.gm.mapr.demo;

import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;

/**
 * Scans for the beans decorated as @{@link org.springframework.stereotype.Repository} & creates a proxy
 * which handles repository methods
 */
public class DrillRepositoryFactoryBean {


    @SuppressWarnings({"unchecked"})
    public <T> T createDrillRepository(Class<?> repositoryInterface, AutowireCapableBeanFactory bf) throws ClassNotFoundException {

        ProxyFactoryBean pfb = new ProxyFactoryBean();
        pfb.setTarget(new SimpleDrillRepository());
        pfb.setBeanFactory(bf);
        pfb.setInterceptorNames(new String[] { "drillDefaultMethodInvokingMethodInterceptor" });

        pfb.setProxyTargetClass(true);
        pfb.setProxyInterfaces(new Class<?>[]{repositoryInterface});
        return (T) pfb.getObject();

//        ProxyFactory result = new ProxyFactory();
//        result.setProxyTargetClass(true);
//        result.setTargetClass(SimpleDrillRepository.class);
//        result.setInterfaces(repositoryInterface);

//        result.addAdvice(SurroundingTransactionDetectorMethodInterceptor.INSTANCE);
//        result.addAdvisor(ExposeInvocationInterceptor.ADVISOR);

//        result.addAdvice(new DefaultMethodInvokingMethodInterceptor());

//        return (T) result.getProxy(ClassUtils.getDefaultClassLoader())   ;
    }

}
