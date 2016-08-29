package com.parking.common.security.intercept;

import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.expression.SecurityExpressionHandler;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.expression.ExpressionBasedFilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import com.parking.common.security.expression.CustomWebSecurityExpressionHandler;
import com.parking.common.security.manager.URIPatternManager;

@Component("customSecurityMetadataSource")
public class CustomSecurityMetadataSource implements FilterInvocationSecurityMetadataSource, InitializingBean {
	private static final Logger LOG = LogManager.getLogger(CustomSecurityMetadataSource.class.getName());
	private FilterInvocationSecurityMetadataSource delegate;
	private SecurityExpressionHandler<FilterInvocation> expressionHandler;
	private URIPatternManager uriPatternManager;

	@Autowired
	public CustomSecurityMetadataSource(CustomWebSecurityExpressionHandler expressionHandler,
			URIPatternManager uriPatternManager) {
		this.expressionHandler = expressionHandler;
		this.uriPatternManager = uriPatternManager;
	}

	public Collection<ConfigAttribute> getAllConfigAttributes() {
		return this.delegate.getAllConfigAttributes();
	}

	public Collection<ConfigAttribute> getAttributes(Object object) {
		return this.delegate.getAttributes(object);
	}

	public boolean supports(Class<?> clazz) {
		return this.delegate.supports(clazz);
	}

	public void afterPropertiesSet() throws Exception {
		populateSecurityMetadataSource();
	}

	public void populateSecurityMetadataSource() throws Exception {
		LOG.debug("In SecurityMetadataSource - populateSecurityMetadataSource start");
		Map customMetaData = this.uriPatternManager.getAllURIPatternsWithRoles();
		processSecurityMetadataSource(customMetaData);
	}

	private void processSecurityMetadataSource(Map<String, StringBuilder> customMetaData) throws Exception {
		LOG.debug("In SecurityMetadataSource - processSecurityMetadataSource start");
		LinkedHashMap requestMap = new LinkedHashMap(customMetaData.size());

		for (String key : customMetaData.keySet()) {
			Collection configCollection = Collections
					.singleton(new SecurityConfig("hasAnyRole(" + customMetaData.get(key) + ")"));

			requestMap.put(new AntPathRequestMatcher(key), configCollection);
		}

		LOG.debug("Dynamic intercept-url map :: " + requestMap);
		LOG.debug("In SecurityMetadataSource - populateSecurityMetadataSource end.");
		this.delegate = new ExpressionBasedFilterInvocationSecurityMetadataSource(requestMap, this.expressionHandler);
	}
}