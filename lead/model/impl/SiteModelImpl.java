package com.erp.lead.model.impl;

import com.erp.lead.model.Site;
import com.erp.lead.model.SiteModel;
import com.erp.lead.model.SiteSoap;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * The base model implementation for the Site service. Represents a row in the &quot;sites&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.SiteModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link SiteImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a Site model instance should use the {@link com.erp.lead.model.Site} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see SiteImpl
 * @see com.erp.lead.model.Site
 * @see com.erp.lead.model.SiteModel
 * @generated
 */
public class SiteModelImpl extends BaseModelImpl<Site> implements SiteModel {
    public static final String TABLE_NAME = "sites";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) },
            { "code", new Integer(Types.VARCHAR) },
            { "description", new Integer(Types.VARCHAR) },
            { "currency_id", new Integer(Types.INTEGER) },
            { "organization_id", new Integer(Types.INTEGER) },
            { "country_id", new Integer(Types.VARCHAR) },
            { "url", new Integer(Types.VARCHAR) },
            { "call_center", new Integer(Types.INTEGER) },
            { "language_iso_2", new Integer(Types.VARCHAR) },
            { "localization_id", new Integer(Types.INTEGER) }
        };
    public static final String TABLE_SQL_CREATE = "create table sites (id INTEGER not null primary key IDENTITY,code VARCHAR(75) null,description VARCHAR(75) null,currency_id INTEGER,organization_id INTEGER,country_id VARCHAR(75) null,url VARCHAR(75) null,call_center INTEGER,language_iso_2 VARCHAR(75) null,localization_id INTEGER)";
    public static final String TABLE_SQL_DROP = "drop table sites";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.erp.lead.model.Site"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.erp.lead.model.Site"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.erp.lead.model.Site"));
    private int _id;
    private String _code;
    private String _description;
    private int _currencyId;
    private int _organizationId;
    private String _countryId;
    private String _url;
    private int _callCenter;
    private String _languageIso2;
    private int _localizationId;

    public SiteModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Site toModel(SiteSoap soapModel) {
        Site model = new SiteImpl();

        model.setId(soapModel.getId());
        model.setCode(soapModel.getCode());
        model.setDescription(soapModel.getDescription());
        model.setCurrencyId(soapModel.getCurrencyId());
        model.setOrganizationId(soapModel.getOrganizationId());
        model.setCountryId(soapModel.getCountryId());
        model.setUrl(soapModel.getUrl());
        model.setCallCenter(soapModel.getCallCenter());
        model.setLanguageIso2(soapModel.getLanguageIso2());
        model.setLocalizationId(soapModel.getLocalizationId());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Site> toModels(SiteSoap[] soapModels) {
        List<Site> models = new ArrayList<Site>(soapModels.length);

        for (SiteSoap soapModel : soapModels) {
            models.add(toModel(soapModel));
        }

        return models;
    }

    public int getPrimaryKey() {
        return _id;
    }

    public void setPrimaryKey(int pk) {
        setId(pk);
    }

    public Serializable getPrimaryKeyObj() {
        return new Integer(_id);
    }

    public int getId() {
        return _id;
    }

    public void setId(int id) {
        _id = id;
    }

    public String getCode() {
        if (_code == null) {
            return StringPool.BLANK;
        } else {
            return _code;
        }
    }

    public void setCode(String code) {
        _code = code;
    }

    public String getDescription() {
        if (_description == null) {
            return StringPool.BLANK;
        } else {
            return _description;
        }
    }

    public void setDescription(String description) {
        _description = description;
    }

    public int getCurrencyId() {
        return _currencyId;
    }

    public void setCurrencyId(int currencyId) {
        _currencyId = currencyId;
    }

    public int getOrganizationId() {
        return _organizationId;
    }

    public void setOrganizationId(int organizationId) {
        _organizationId = organizationId;
    }

    public String getCountryId() {
        if (_countryId == null) {
            return StringPool.BLANK;
        } else {
            return _countryId;
        }
    }

    public void setCountryId(String countryId) {
        _countryId = countryId;
    }

    public String getUrl() {
        if (_url == null) {
            return StringPool.BLANK;
        } else {
            return _url;
        }
    }

    public void setUrl(String url) {
        _url = url;
    }

    public int getCallCenter() {
        return _callCenter;
    }

    public void setCallCenter(int callCenter) {
        _callCenter = callCenter;
    }

    public String getLanguageIso2() {
        if (_languageIso2 == null) {
            return StringPool.BLANK;
        } else {
            return _languageIso2;
        }
    }

    public void setLanguageIso2(String languageIso2) {
        _languageIso2 = languageIso2;
    }

    public int getLocalizationId() {
        return _localizationId;
    }

    public void setLocalizationId(int localizationId) {
        _localizationId = localizationId;
    }

    public Site toEscapedModel() {
        if (isEscapedModel()) {
            return (Site) this;
        } else {
            return (Site) Proxy.newProxyInstance(Site.class.getClassLoader(),
                new Class[] { Site.class }, new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        SiteImpl siteImpl = new SiteImpl();

        siteImpl.setId(getId());

        siteImpl.setCode(getCode());

        siteImpl.setDescription(getDescription());

        siteImpl.setCurrencyId(getCurrencyId());

        siteImpl.setOrganizationId(getOrganizationId());

        siteImpl.setCountryId(getCountryId());

        siteImpl.setUrl(getUrl());

        siteImpl.setCallCenter(getCallCenter());

        siteImpl.setLanguageIso2(getLanguageIso2());

        siteImpl.setLocalizationId(getLocalizationId());

        return siteImpl;
    }

    public int compareTo(Site site) {
        int pk = site.getPrimaryKey();

        if (getPrimaryKey() < pk) {
            return -1;
        } else if (getPrimaryKey() > pk) {
            return 1;
        } else {
            return 0;
        }
    }

    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }

        Site site = null;

        try {
            site = (Site) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = site.getPrimaryKey();

        if (getPrimaryKey() == pk) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {
        return getPrimaryKey();
    }

    public String toString() {
        StringBundler sb = new StringBundler(21);

        sb.append("{id=");
        sb.append(getId());
        sb.append(", code=");
        sb.append(getCode());
        sb.append(", description=");
        sb.append(getDescription());
        sb.append(", currencyId=");
        sb.append(getCurrencyId());
        sb.append(", organizationId=");
        sb.append(getOrganizationId());
        sb.append(", countryId=");
        sb.append(getCountryId());
        sb.append(", url=");
        sb.append(getUrl());
        sb.append(", callCenter=");
        sb.append(getCallCenter());
        sb.append(", languageIso2=");
        sb.append(getLanguageIso2());
        sb.append(", localizationId=");
        sb.append(getLocalizationId());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(34);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.Site");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>code</column-name><column-value><![CDATA[");
        sb.append(getCode());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>description</column-name><column-value><![CDATA[");
        sb.append(getDescription());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>currencyId</column-name><column-value><![CDATA[");
        sb.append(getCurrencyId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>organizationId</column-name><column-value><![CDATA[");
        sb.append(getOrganizationId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>countryId</column-name><column-value><![CDATA[");
        sb.append(getCountryId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>url</column-name><column-value><![CDATA[");
        sb.append(getUrl());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>callCenter</column-name><column-value><![CDATA[");
        sb.append(getCallCenter());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>languageIso2</column-name><column-value><![CDATA[");
        sb.append(getLanguageIso2());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>localizationId</column-name><column-value><![CDATA[");
        sb.append(getLocalizationId());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
