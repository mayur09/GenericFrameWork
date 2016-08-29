package com.parking.common.security.dao;

import com.parking.common.dao.GenericDao;
import com.parking.common.security.model.EmailTemplate;

public abstract interface EmailTemplateDao extends GenericDao<EmailTemplate, String> {
	public abstract EmailTemplate getEmailTemplateByTemplateKey(String paramString);
}