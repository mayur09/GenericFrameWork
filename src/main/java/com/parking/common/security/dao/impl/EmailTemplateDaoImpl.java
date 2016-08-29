package com.parking.common.security.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import com.parking.common.dao.impl.GenericDaoImpl;
import com.parking.common.security.dao.EmailTemplateDao;
import com.parking.common.security.model.EmailTemplate;

@Repository("emailTemplateDao")
public class EmailTemplateDaoImpl extends GenericDaoImpl<EmailTemplate, String> implements EmailTemplateDao {
	@Autowired
	public EmailTemplateDaoImpl(
			@Value("com.pmcretail.framework.security.model.EmailTemplate") Class<EmailTemplate> persistentClass) {
		super(persistentClass);
	}

	public EmailTemplate getEmailTemplateByTemplateKey(String mKey) {
		StringBuilder sql = new StringBuilder("From EmailTemplate e ");
		sql.append(new StringBuilder().append(" where e.templateKey = '").append(mKey).append("'").toString());
		List tempaltes = findByQuery(sql.toString(), null);

		if ((tempaltes != null) && (!(tempaltes.isEmpty()))) {
			return ((EmailTemplate) tempaltes.get(0));
		}
		return null;
	}
}