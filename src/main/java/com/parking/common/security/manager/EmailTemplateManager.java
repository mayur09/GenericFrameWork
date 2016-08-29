package com.parking.common.security.manager;


import com.parking.common.security.model.EmailTemplate;

public abstract interface EmailTemplateManager extends GenericManager<EmailTemplate, String> {
	public abstract EmailTemplate getEmailTemplateByTemplateKey(String paramString);
}