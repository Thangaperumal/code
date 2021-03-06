package com.erp.lead.model.impl;

import com.erp.lead.model.Form;
import com.erp.lead.model.FormModel;
import com.erp.lead.model.FormSoap;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * The base model implementation for the Form service. Represents a row in the &quot;forms&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.FormModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link FormImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a Form model instance should use the {@link com.erp.lead.model.Form} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see FormImpl
 * @see com.erp.lead.model.Form
 * @see com.erp.lead.model.FormModel
 * @generated
 */
public class FormModelImpl extends BaseModelImpl<Form> implements FormModel {
    public static final String TABLE_NAME = "forms";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) },
            { "site_id", new Integer(Types.INTEGER) },
            { "form_code", new Integer(Types.VARCHAR) },
            { "salesforce", new Integer(Types.INTEGER) },
            { "dovetail", new Integer(Types.INTEGER) },
            { "demo_cd", new Integer(Types.INTEGER) },
            { "email", new Integer(Types.VARCHAR) },
            { "created_at", new Integer(Types.TIMESTAMP) },
            { "updated_at", new Integer(Types.TIMESTAMP) },
            { "vertical", new Integer(Types.VARCHAR) },
            { "trial_osub", new Integer(Types.INTEGER) },
            { "form_email_template_id", new Integer(Types.INTEGER) },
            { "osub_cancellation", new Integer(Types.INTEGER) },
            { "business_owner", new Integer(Types.VARCHAR) },
            { "eloqua", new Integer(Types.INTEGER) },
            { "trial_osub_followup", new Integer(Types.INTEGER) },
            { "auto_reply", new Integer(Types.INTEGER) },
            { "pure360", new Integer(Types.INTEGER) }
        };
    public static final String TABLE_SQL_CREATE = "create table forms (id INTEGER not null primary key IDENTITY,site_id INTEGER,form_code VARCHAR(75) null,salesforce INTEGER,dovetail INTEGER,demo_cd INTEGER,email VARCHAR(75) null,created_at DATE null,updated_at DATE null,vertical VARCHAR(75) null,trial_osub INTEGER,form_email_template_id INTEGER,osub_cancellation INTEGER,business_owner VARCHAR(75) null,eloqua INTEGER,trial_osub_followup INTEGER,auto_reply INTEGER,pure360 INTEGER)";
    public static final String TABLE_SQL_DROP = "drop table forms";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.erp.lead.model.Form"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.erp.lead.model.Form"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.erp.lead.model.Form"));
    private int _id;
    private int _siteId;
    private String _formCode;
    private int _salesforce;
    private int _dovetail;
    private int _demoCd;
    private String _email;
    private Date _createdAt;
    private Date _updatedAt;
    private String _vertical;
    private int _trialOsub;
    private int _formEmailTemplateId;
    private int _osubCancellation;
    private String _businessOwner;
    private int _eloqua;
    private int _trialOsubFollowup;
    private int _autoReply;
    private int _pure360;

    public FormModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Form toModel(FormSoap soapModel) {
        Form model = new FormImpl();

        model.setId(soapModel.getId());
        model.setSiteId(soapModel.getSiteId());
        model.setFormCode(soapModel.getFormCode());
        model.setSalesforce(soapModel.getSalesforce());
        model.setDovetail(soapModel.getDovetail());
        model.setDemoCd(soapModel.getDemoCd());
        model.setEmail(soapModel.getEmail());
        model.setCreatedAt(soapModel.getCreatedAt());
        model.setUpdatedAt(soapModel.getUpdatedAt());
        model.setVertical(soapModel.getVertical());
        model.setTrialOsub(soapModel.getTrialOsub());
        model.setFormEmailTemplateId(soapModel.getFormEmailTemplateId());
        model.setOsubCancellation(soapModel.getOsubCancellation());
        model.setBusinessOwner(soapModel.getBusinessOwner());
        model.setEloqua(soapModel.getEloqua());
        model.setTrialOsubFollowup(soapModel.getTrialOsubFollowup());
        model.setAutoReply(soapModel.getAutoReply());
        model.setPure360(soapModel.getPure360());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Form> toModels(FormSoap[] soapModels) {
        List<Form> models = new ArrayList<Form>(soapModels.length);

        for (FormSoap soapModel : soapModels) {
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

    public int getSiteId() {
        return _siteId;
    }

    public void setSiteId(int siteId) {
        _siteId = siteId;
    }

    public String getFormCode() {
        if (_formCode == null) {
            return StringPool.BLANK;
        } else {
            return _formCode;
        }
    }

    public void setFormCode(String formCode) {
        _formCode = formCode;
    }

    public int getSalesforce() {
        return _salesforce;
    }

    public void setSalesforce(int salesforce) {
        _salesforce = salesforce;
    }

    public int getDovetail() {
        return _dovetail;
    }

    public void setDovetail(int dovetail) {
        _dovetail = dovetail;
    }

    public int getDemoCd() {
        return _demoCd;
    }

    public void setDemoCd(int demoCd) {
        _demoCd = demoCd;
    }

    public String getEmail() {
        if (_email == null) {
            return StringPool.BLANK;
        } else {
            return _email;
        }
    }

    public void setEmail(String email) {
        _email = email;
    }

    public Date getCreatedAt() {
        return _createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        _createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return _updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        _updatedAt = updatedAt;
    }

    public String getVertical() {
        if (_vertical == null) {
            return StringPool.BLANK;
        } else {
            return _vertical;
        }
    }

    public void setVertical(String vertical) {
        _vertical = vertical;
    }

    public int getTrialOsub() {
        return _trialOsub;
    }

    public void setTrialOsub(int trialOsub) {
        _trialOsub = trialOsub;
    }

    public int getFormEmailTemplateId() {
        return _formEmailTemplateId;
    }

    public void setFormEmailTemplateId(int formEmailTemplateId) {
        _formEmailTemplateId = formEmailTemplateId;
    }

    public int getOsubCancellation() {
        return _osubCancellation;
    }

    public void setOsubCancellation(int osubCancellation) {
        _osubCancellation = osubCancellation;
    }

    public String getBusinessOwner() {
        if (_businessOwner == null) {
            return StringPool.BLANK;
        } else {
            return _businessOwner;
        }
    }

    public void setBusinessOwner(String businessOwner) {
        _businessOwner = businessOwner;
    }

    public int getEloqua() {
        return _eloqua;
    }

    public void setEloqua(int eloqua) {
        _eloqua = eloqua;
    }

    public int getTrialOsubFollowup() {
        return _trialOsubFollowup;
    }

    public void setTrialOsubFollowup(int trialOsubFollowup) {
        _trialOsubFollowup = trialOsubFollowup;
    }

    public int getAutoReply() {
        return _autoReply;
    }

    public void setAutoReply(int autoReply) {
        _autoReply = autoReply;
    }

    public int getPure360() {
        return _pure360;
    }

    public void setPure360(int pure360) {
        _pure360 = pure360;
    }

    public Form toEscapedModel() {
        if (isEscapedModel()) {
            return (Form) this;
        } else {
            return (Form) Proxy.newProxyInstance(Form.class.getClassLoader(),
                new Class[] { Form.class }, new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        FormImpl formImpl = new FormImpl();

        formImpl.setId(getId());

        formImpl.setSiteId(getSiteId());

        formImpl.setFormCode(getFormCode());

        formImpl.setSalesforce(getSalesforce());

        formImpl.setDovetail(getDovetail());

        formImpl.setDemoCd(getDemoCd());

        formImpl.setEmail(getEmail());

        formImpl.setCreatedAt(getCreatedAt());

        formImpl.setUpdatedAt(getUpdatedAt());

        formImpl.setVertical(getVertical());

        formImpl.setTrialOsub(getTrialOsub());

        formImpl.setFormEmailTemplateId(getFormEmailTemplateId());

        formImpl.setOsubCancellation(getOsubCancellation());

        formImpl.setBusinessOwner(getBusinessOwner());

        formImpl.setEloqua(getEloqua());

        formImpl.setTrialOsubFollowup(getTrialOsubFollowup());

        formImpl.setAutoReply(getAutoReply());

        formImpl.setPure360(getPure360());

        return formImpl;
    }

    public int compareTo(Form form) {
        int pk = form.getPrimaryKey();

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

        Form form = null;

        try {
            form = (Form) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = form.getPrimaryKey();

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
        StringBundler sb = new StringBundler(37);

        sb.append("{id=");
        sb.append(getId());
        sb.append(", siteId=");
        sb.append(getSiteId());
        sb.append(", formCode=");
        sb.append(getFormCode());
        sb.append(", salesforce=");
        sb.append(getSalesforce());
        sb.append(", dovetail=");
        sb.append(getDovetail());
        sb.append(", demoCd=");
        sb.append(getDemoCd());
        sb.append(", email=");
        sb.append(getEmail());
        sb.append(", createdAt=");
        sb.append(getCreatedAt());
        sb.append(", updatedAt=");
        sb.append(getUpdatedAt());
        sb.append(", vertical=");
        sb.append(getVertical());
        sb.append(", trialOsub=");
        sb.append(getTrialOsub());
        sb.append(", formEmailTemplateId=");
        sb.append(getFormEmailTemplateId());
        sb.append(", osubCancellation=");
        sb.append(getOsubCancellation());
        sb.append(", businessOwner=");
        sb.append(getBusinessOwner());
        sb.append(", eloqua=");
        sb.append(getEloqua());
        sb.append(", trialOsubFollowup=");
        sb.append(getTrialOsubFollowup());
        sb.append(", autoReply=");
        sb.append(getAutoReply());
        sb.append(", pure360=");
        sb.append(getPure360());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(58);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.Form");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>siteId</column-name><column-value><![CDATA[");
        sb.append(getSiteId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>formCode</column-name><column-value><![CDATA[");
        sb.append(getFormCode());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>salesforce</column-name><column-value><![CDATA[");
        sb.append(getSalesforce());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>dovetail</column-name><column-value><![CDATA[");
        sb.append(getDovetail());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>demoCd</column-name><column-value><![CDATA[");
        sb.append(getDemoCd());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>email</column-name><column-value><![CDATA[");
        sb.append(getEmail());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>createdAt</column-name><column-value><![CDATA[");
        sb.append(getCreatedAt());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>updatedAt</column-name><column-value><![CDATA[");
        sb.append(getUpdatedAt());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>vertical</column-name><column-value><![CDATA[");
        sb.append(getVertical());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>trialOsub</column-name><column-value><![CDATA[");
        sb.append(getTrialOsub());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>formEmailTemplateId</column-name><column-value><![CDATA[");
        sb.append(getFormEmailTemplateId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>osubCancellation</column-name><column-value><![CDATA[");
        sb.append(getOsubCancellation());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>businessOwner</column-name><column-value><![CDATA[");
        sb.append(getBusinessOwner());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eloqua</column-name><column-value><![CDATA[");
        sb.append(getEloqua());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>trialOsubFollowup</column-name><column-value><![CDATA[");
        sb.append(getTrialOsubFollowup());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>autoReply</column-name><column-value><![CDATA[");
        sb.append(getAutoReply());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>pure360</column-name><column-value><![CDATA[");
        sb.append(getPure360());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
