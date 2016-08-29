package com.parking.common.security.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;

import com.parking.common.security.intercept.CustomSecurityMetadataSource;

@Component
public class CustomSecurityMetadataSourceBeanPostProcessor implements BeanPostProcessor {

	@Autowired
	private CustomSecurityMetadataSource metadataSource;

	public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
		if (bean instanceof FilterInvocationSecurityMetadataSource) {
			return this.metadataSource;
		}
		if (bean instanceof FilterChainProxy.FilterChainValidator)
			return new FilterChainProxy.FilterChainValidator() {
				public void validate(FilterChainProxy filterChainProxy) {
				}
			};
		return bean;
	}

	public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
		return bean;
	}
}