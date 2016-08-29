package com.pmcretail.framework.security.manager.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.dao.impl.EmailTemplateDaoImpl;
import com.parking.common.security.manager.EmailTemplateManager;
import com.parking.common.security.manager.impl.GenericManagerImpl;
import com.parking.common.security.model.EmailTemplate;

@Service("emailTemplateManager")
public class EmailTemplateManagerImpl extends GenericManagerImpl<EmailTemplate, String>
		implements EmailTemplateManager {
	@Autowired
	public EmailTemplateManagerImpl(@Qualifier("emailTemplateDao") GenericDao<EmailTemplate, String> genericDao) {
		super(genericDao);
	}

	@Transactional
	public EmailTemplate getEmailTemplateByTemplateKey(String mKey) {
		EmailTemplate template = ((EmailTemplateDaoImpl) this.genericDao).getEmailTemplateByTemplateKey(mKey);
		return template;
	}
}