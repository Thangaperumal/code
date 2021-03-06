package com.erp.lead.model.impl;

import com.erp.lead.model.Customer;
import com.erp.lead.model.CustomerModel;
import com.erp.lead.model.CustomerSoap;
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
 * The base model implementation for the Customer service. Represents a row in the &quot;customers&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.CustomerModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link CustomerImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a Customer model instance should use the {@link com.erp.lead.model.Customer} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see CustomerImpl
 * @see com.erp.lead.model.Customer
 * @see com.erp.lead.model.CustomerModel
 * @generated
 */
public class CustomerModelImpl extends BaseModelImpl<Customer>
    implements CustomerModel {
    public static final String TABLE_NAME = "customers";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) },
            { "full_name", new Integer(Types.VARCHAR) },
            { "email", new Integer(Types.VARCHAR) },
            { "password", new Integer(Types.VARCHAR) },
            { "you_can_spam_me", new Integer(Types.INTEGER) },
            { "affiliates_can_spam_me", new Integer(Types.INTEGER) },
            { "lead_code", new Integer(Types.VARCHAR) },
            { "locale", new Integer(Types.VARCHAR) },
            { "affiliate_code", new Integer(Types.VARCHAR) },
            { "ab_split", new Integer(Types.VARCHAR) },
            { "created_at", new Integer(Types.TIMESTAMP) },
            { "updated_at", new Integer(Types.TIMESTAMP) },
            { "organization_name", new Integer(Types.VARCHAR) },
            { "first_name", new Integer(Types.VARCHAR) },
            { "last_name", new Integer(Types.VARCHAR) },
            { "phone_number", new Integer(Types.VARCHAR) },
            { "generic_lead_code", new Integer(Types.VARCHAR) },
            { "dnis_telephone_number", new Integer(Types.VARCHAR) },
            { "referring_domain", new Integer(Types.VARCHAR) },
            { "guid", new Integer(Types.VARCHAR) },
            { "last_referring_domain", new Integer(Types.VARCHAR) },
            { "learner_email", new Integer(Types.VARCHAR) }
        };
    public static final String TABLE_SQL_CREATE = "create table customers (id INTEGER not null primary key IDENTITY,full_name VARCHAR(75) null,email VARCHAR(75) null,password VARCHAR(75) null,you_can_spam_me INTEGER,affiliates_can_spam_me INTEGER,lead_code VARCHAR(75) null,locale VARCHAR(75) null,affiliate_code VARCHAR(75) null,ab_split VARCHAR(75) null,created_at DATE null,updated_at DATE null,organization_name VARCHAR(75) null,first_name VARCHAR(75) null,last_name VARCHAR(75) null,phone_number VARCHAR(75) null,generic_lead_code VARCHAR(75) null,dnis_telephone_number VARCHAR(75) null,referring_domain VARCHAR(75) null,guid VARCHAR(75) null,last_referring_domain VARCHAR(75) null,learner_email VARCHAR(75) null)";
    public static final String TABLE_SQL_DROP = "drop table customers";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.erp.lead.model.Customer"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.erp.lead.model.Customer"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.erp.lead.model.Customer"));
    private int _id;
    private String _fullName;
    private String _email;
    private String _password;
    private int _youCanSpamMe;
    private int _affiliatesCanSpamMe;
    private String _leadCode;
    private String _locale;
    private String _affiliateCode;
    private String _abSplit;
    private Date _createdAt;
    private Date _updatedAt;
    private String _organizationName;
    private String _firstName;
    private String _lastName;
    private String _phoneNumber;
    private String _genericLeadCode;
    private String _dnisTelephoneNumber;
    private String _referringDomain;
    private String _guid;
    private String _lastReferringDomain;
    private String _learnerEmail;

    public CustomerModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Customer toModel(CustomerSoap soapModel) {
        Customer model = new CustomerImpl();

        model.setId(soapModel.getId());
        model.setFullName(soapModel.getFullName());
        model.setEmail(soapModel.getEmail());
        model.setPassword(soapModel.getPassword());
        model.setYouCanSpamMe(soapModel.getYouCanSpamMe());
        model.setAffiliatesCanSpamMe(soapModel.getAffiliatesCanSpamMe());
        model.setLeadCode(soapModel.getLeadCode());
        model.setLocale(soapModel.getLocale());
        model.setAffiliateCode(soapModel.getAffiliateCode());
        model.setAbSplit(soapModel.getAbSplit());
        model.setCreatedAt(soapModel.getCreatedAt());
        model.setUpdatedAt(soapModel.getUpdatedAt());
        model.setOrganizationName(soapModel.getOrganizationName());
        model.setFirstName(soapModel.getFirstName());
        model.setLastName(soapModel.getLastName());
        model.setPhoneNumber(soapModel.getPhoneNumber());
        model.setGenericLeadCode(soapModel.getGenericLeadCode());
        model.setDnisTelephoneNumber(soapModel.getDnisTelephoneNumber());
        model.setReferringDomain(soapModel.getReferringDomain());
        model.setGuid(soapModel.getGuid());
        model.setLastReferringDomain(soapModel.getLastReferringDomain());
        model.setLearnerEmail(soapModel.getLearnerEmail());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Customer> toModels(CustomerSoap[] soapModels) {
        List<Customer> models = new ArrayList<Customer>(soapModels.length);

        for (CustomerSoap soapModel : soapModels) {
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

    public String getFullName() {
        if (_fullName == null) {
            return StringPool.BLANK;
        } else {
            return _fullName;
        }
    }

    public void setFullName(String fullName) {
        _fullName = fullName;
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

    public String getPassword() {
        if (_password == null) {
            return StringPool.BLANK;
        } else {
            return _password;
        }
    }

    public void setPassword(String password) {
        _password = password;
    }

    public int getYouCanSpamMe() {
        return _youCanSpamMe;
    }

    public void setYouCanSpamMe(int youCanSpamMe) {
        _youCanSpamMe = youCanSpamMe;
    }

    public int getAffiliatesCanSpamMe() {
        return _affiliatesCanSpamMe;
    }

    public void setAffiliatesCanSpamMe(int affiliatesCanSpamMe) {
        _affiliatesCanSpamMe = affiliatesCanSpamMe;
    }

    public String getLeadCode() {
        if (_leadCode == null) {
            return StringPool.BLANK;
        } else {
            return _leadCode;
        }
    }

    public void setLeadCode(String leadCode) {
        _leadCode = leadCode;
    }

    public String getLocale() {
        if (_locale == null) {
            return StringPool.BLANK;
        } else {
            return _locale;
        }
    }

    public void setLocale(String locale) {
        _locale = locale;
    }

    public String getAffiliateCode() {
        if (_affiliateCode == null) {
            return StringPool.BLANK;
        } else {
            return _affiliateCode;
        }
    }

    public void setAffiliateCode(String affiliateCode) {
        _affiliateCode = affiliateCode;
    }

    public String getAbSplit() {
        if (_abSplit == null) {
            return StringPool.BLANK;
        } else {
            return _abSplit;
        }
    }

    public void setAbSplit(String abSplit) {
        _abSplit = abSplit;
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

    public String getOrganizationName() {
        if (_organizationName == null) {
            return StringPool.BLANK;
        } else {
            return _organizationName;
        }
    }

    public void setOrganizationName(String organizationName) {
        _organizationName = organizationName;
    }

    public String getFirstName() {
        if (_firstName == null) {
            return StringPool.BLANK;
        } else {
            return _firstName;
        }
    }

    public void setFirstName(String firstName) {
        _firstName = firstName;
    }

    public String getLastName() {
        if (_lastName == null) {
            return StringPool.BLANK;
        } else {
            return _lastName;
        }
    }

    public void setLastName(String lastName) {
        _lastName = lastName;
    }

    public String getPhoneNumber() {
        if (_phoneNumber == null) {
            return StringPool.BLANK;
        } else {
            return _phoneNumber;
        }
    }

    public void setPhoneNumber(String phoneNumber) {
        _phoneNumber = phoneNumber;
    }

    public String getGenericLeadCode() {
        if (_genericLeadCode == null) {
            return StringPool.BLANK;
        } else {
            return _genericLeadCode;
        }
    }

    public void setGenericLeadCode(String genericLeadCode) {
        _genericLeadCode = genericLeadCode;
    }

    public String getDnisTelephoneNumber() {
        if (_dnisTelephoneNumber == null) {
            return StringPool.BLANK;
        } else {
            return _dnisTelephoneNumber;
        }
    }

    public void setDnisTelephoneNumber(String dnisTelephoneNumber) {
        _dnisTelephoneNumber = dnisTelephoneNumber;
    }

    public String getReferringDomain() {
        if (_referringDomain == null) {
            return StringPool.BLANK;
        } else {
            return _referringDomain;
        }
    }

    public void setReferringDomain(String referringDomain) {
        _referringDomain = referringDomain;
    }

    public String getGuid() {
        if (_guid == null) {
            return StringPool.BLANK;
        } else {
            return _guid;
        }
    }

    public void setGuid(String guid) {
        _guid = guid;
    }

    public String getLastReferringDomain() {
        if (_lastReferringDomain == null) {
            return StringPool.BLANK;
        } else {
            return _lastReferringDomain;
        }
    }

    public void setLastReferringDomain(String lastReferringDomain) {
        _lastReferringDomain = lastReferringDomain;
    }

    public String getLearnerEmail() {
        if (_learnerEmail == null) {
            return StringPool.BLANK;
        } else {
            return _learnerEmail;
        }
    }

    public void setLearnerEmail(String learnerEmail) {
        _learnerEmail = learnerEmail;
    }

    public Customer toEscapedModel() {
        if (isEscapedModel()) {
            return (Customer) this;
        } else {
            return (Customer) Proxy.newProxyInstance(Customer.class.getClassLoader(),
                new Class[] { Customer.class }, new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        CustomerImpl customerImpl = new CustomerImpl();

        customerImpl.setId(getId());

        customerImpl.setFullName(getFullName());

        customerImpl.setEmail(getEmail());

        customerImpl.setPassword(getPassword());

        customerImpl.setYouCanSpamMe(getYouCanSpamMe());

        customerImpl.setAffiliatesCanSpamMe(getAffiliatesCanSpamMe());

        customerImpl.setLeadCode(getLeadCode());

        customerImpl.setLocale(getLocale());

        customerImpl.setAffiliateCode(getAffiliateCode());

        customerImpl.setAbSplit(getAbSplit());

        customerImpl.setCreatedAt(getCreatedAt());

        customerImpl.setUpdatedAt(getUpdatedAt());

        customerImpl.setOrganizationName(getOrganizationName());

        customerImpl.setFirstName(getFirstName());

        customerImpl.setLastName(getLastName());

        customerImpl.setPhoneNumber(getPhoneNumber());

        customerImpl.setGenericLeadCode(getGenericLeadCode());

        customerImpl.setDnisTelephoneNumber(getDnisTelephoneNumber());

        customerImpl.setReferringDomain(getReferringDomain());

        customerImpl.setGuid(getGuid());

        customerImpl.setLastReferringDomain(getLastReferringDomain());

        customerImpl.setLearnerEmail(getLearnerEmail());

        return customerImpl;
    }

    public int compareTo(Customer customer) {
        int pk = customer.getPrimaryKey();

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

        Customer customer = null;

        try {
            customer = (Customer) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = customer.getPrimaryKey();

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
        StringBundler sb = new StringBundler(45);

        sb.append("{id=");
        sb.append(getId());
        sb.append(", fullName=");
        sb.append(getFullName());
        sb.append(", email=");
        sb.append(getEmail());
        sb.append(", password=");
        sb.append(getPassword());
        sb.append(", youCanSpamMe=");
        sb.append(getYouCanSpamMe());
        sb.append(", affiliatesCanSpamMe=");
        sb.append(getAffiliatesCanSpamMe());
        sb.append(", leadCode=");
        sb.append(getLeadCode());
        sb.append(", locale=");
        sb.append(getLocale());
        sb.append(", affiliateCode=");
        sb.append(getAffiliateCode());
        sb.append(", abSplit=");
        sb.append(getAbSplit());
        sb.append(", createdAt=");
        sb.append(getCreatedAt());
        sb.append(", updatedAt=");
        sb.append(getUpdatedAt());
        sb.append(", organizationName=");
        sb.append(getOrganizationName());
        sb.append(", firstName=");
        sb.append(getFirstName());
        sb.append(", lastName=");
        sb.append(getLastName());
        sb.append(", phoneNumber=");
        sb.append(getPhoneNumber());
        sb.append(", genericLeadCode=");
        sb.append(getGenericLeadCode());
        sb.append(", dnisTelephoneNumber=");
        sb.append(getDnisTelephoneNumber());
        sb.append(", referringDomain=");
        sb.append(getReferringDomain());
        sb.append(", guid=");
        sb.append(getGuid());
        sb.append(", lastReferringDomain=");
        sb.append(getLastReferringDomain());
        sb.append(", learnerEmail=");
        sb.append(getLearnerEmail());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(70);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.Customer");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>fullName</column-name><column-value><![CDATA[");
        sb.append(getFullName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>email</column-name><column-value><![CDATA[");
        sb.append(getEmail());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>password</column-name><column-value><![CDATA[");
        sb.append(getPassword());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>youCanSpamMe</column-name><column-value><![CDATA[");
        sb.append(getYouCanSpamMe());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>affiliatesCanSpamMe</column-name><column-value><![CDATA[");
        sb.append(getAffiliatesCanSpamMe());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>leadCode</column-name><column-value><![CDATA[");
        sb.append(getLeadCode());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>locale</column-name><column-value><![CDATA[");
        sb.append(getLocale());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>affiliateCode</column-name><column-value><![CDATA[");
        sb.append(getAffiliateCode());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>abSplit</column-name><column-value><![CDATA[");
        sb.append(getAbSplit());
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
            "<column><column-name>organizationName</column-name><column-value><![CDATA[");
        sb.append(getOrganizationName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>firstName</column-name><column-value><![CDATA[");
        sb.append(getFirstName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>lastName</column-name><column-value><![CDATA[");
        sb.append(getLastName());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>phoneNumber</column-name><column-value><![CDATA[");
        sb.append(getPhoneNumber());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>genericLeadCode</column-name><column-value><![CDATA[");
        sb.append(getGenericLeadCode());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>dnisTelephoneNumber</column-name><column-value><![CDATA[");
        sb.append(getDnisTelephoneNumber());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>referringDomain</column-name><column-value><![CDATA[");
        sb.append(getReferringDomain());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>guid</column-name><column-value><![CDATA[");
        sb.append(getGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>lastReferringDomain</column-name><column-value><![CDATA[");
        sb.append(getLastReferringDomain());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>learnerEmail</column-name><column-value><![CDATA[");
        sb.append(getLearnerEmail());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
