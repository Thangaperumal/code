package com.erp.lead.service.persistence;

import com.erp.lead.model.Site;
import com.erp.lead.model.impl.SiteImpl;
import com.erp.lead.service.persistence.SiteFinder;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.util.List;
import java.util.ArrayList;

import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.SQLQuery;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.util.dao.orm.CustomSQLUtil;
import com.liferay.portal.kernel.json.JSONArray;
import com.liferay.portal.kernel.json.JSONFactoryUtil;
import com.liferay.portal.kernel.dao.orm.Type;

public class SiteFinderImpl extends BasePersistenceImpl<Site> implements SiteFinder {

	public static final String GET_WEBITES = SiteFinder.class.getName() +".getWebsitesByCallCenter";
	public List<Object> getWebsitesByCallCenter() {

		    Session session = null;
		    try {
		        session = openSession();
		        String sql = CustomSQLUtil.get(GET_WEBITES);
		        SQLQuery q = session.createSQLQuery(sql);
		        q.setCacheable(false);
		        QueryPos qPos = QueryPos.getInstance(q);
		        List<Object> result = (List) QueryUtil.list(q, getDialect(),0,QueryUtil.ALL_POS);
		        return result;
		    } catch (Exception e) {
		        try {
		            throw new SystemException(e);
		        } catch (SystemException se) {
		            se.printStackTrace();
		        }
		    } finally {
		        closeSession(session);
		    }

		    return null;
		}

}