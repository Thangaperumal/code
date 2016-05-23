package com.erp.lead.model.impl;

import com.erp.lead.model.OrderLine;
import com.erp.lead.model.OrderLineModel;
import com.erp.lead.model.OrderLineSoap;
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
 * The base model implementation for the OrderLine service. Represents a row in the &quot;order_lines&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.OrderLineModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link OrderLineImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a OrderLine model instance should use the {@link com.erp.lead.model.OrderLine} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see OrderLineImpl
 * @see com.erp.lead.model.OrderLine
 * @see com.erp.lead.model.OrderLineModel
 * @generated
 */
public class OrderLineModelImpl extends BaseModelImpl<OrderLine>
    implements OrderLineModel {
    public static final String TABLE_NAME = "order_lines";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) },
            { "item_id", new Integer(Types.INTEGER) },
            { "order_id", new Integer(Types.INTEGER) },
            { "quantity", new Integer(Types.INTEGER) },
            { "currency_id", new Integer(Types.INTEGER) },
            { "list_price_per_item", new Integer(Types.DOUBLE) },
            { "your_price_per_item", new Integer(Types.DOUBLE) },
            { "tax", new Integer(Types.DOUBLE) },
            { "created_at", new Integer(Types.TIMESTAMP) },
            { "updated_at", new Integer(Types.TIMESTAMP) },
            { "shipping_line_amount", new Integer(Types.DOUBLE) },
            { "application_guid", new Integer(Types.VARCHAR) },
            { "eschool_guid", new Integer(Types.VARCHAR) },
            { "premium_community_guid", new Integer(Types.VARCHAR) },
            { "user_guid", new Integer(Types.VARCHAR) },
            { "serial_number", new Integer(Types.VARCHAR) },
            { "activation_id", new Integer(Types.VARCHAR) },
            { "lotus_guid", new Integer(Types.VARCHAR) },
            { "language", new Integer(Types.VARCHAR) },
            { "application_ref_id", new Integer(Types.VARCHAR) },
            { "eschool_ref_id", new Integer(Types.VARCHAR) },
            { "premium_community_ref_id", new Integer(Types.VARCHAR) },
            { "lotus_ref_id", new Integer(Types.VARCHAR) },
            { "eschool_group_sessions_guid", new Integer(Types.VARCHAR) },
            { "eschool_one_on_one_sessions_guid", new Integer(Types.VARCHAR) },
            { "language_switching_guid", new Integer(Types.VARCHAR) },
            { "eschool_group_sessions_ref_id", new Integer(Types.VARCHAR) },
            { "eschool_one_on_one_sessions_ref_id", new Integer(Types.VARCHAR) },
            { "language_switching_ref_id", new Integer(Types.VARCHAR) },
            { "auto_renew", new Integer(Types.INTEGER) }
        };
    public static final String TABLE_SQL_CREATE = "create table order_lines (id INTEGER not null primary key IDENTITY,item_id INTEGER,order_id INTEGER,quantity INTEGER,currency_id INTEGER,list_price_per_item DOUBLE,your_price_per_item DOUBLE,tax DOUBLE,created_at DATE null,updated_at DATE null,shipping_line_amount DOUBLE,application_guid VARCHAR(75) null,eschool_guid VARCHAR(75) null,premium_community_guid VARCHAR(75) null,user_guid VARCHAR(75) null,serial_number VARCHAR(75) null,activation_id VARCHAR(75) null,lotus_guid VARCHAR(75) null,language VARCHAR(75) null,application_ref_id VARCHAR(75) null,eschool_ref_id VARCHAR(75) null,premium_community_ref_id VARCHAR(75) null,lotus_ref_id VARCHAR(75) null,eschool_group_sessions_guid VARCHAR(75) null,eschool_one_on_one_sessions_guid VARCHAR(75) null,language_switching_guid VARCHAR(75) null,eschool_group_sessions_ref_id VARCHAR(75) null,eschool_one_on_one_sessions_ref_id VARCHAR(75) null,language_switching_ref_id VARCHAR(75) null,auto_renew INTEGER)";
    public static final String TABLE_SQL_DROP = "drop table order_lines";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.rosettastone.cis.model.OrderLine"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.rosettastone.cis.model.OrderLine"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.rosettastone.cis.model.OrderLine"));
    private int _id;
    private int _itemId;
    private int _orderId;
    private int _quantity;
    private int _currencyId;
    private double _listPricePerItem;
    private double _yourPricePerItem;
    private double _tax;
    private Date _createdAt;
    private Date _updatedAt;
    private double _shippingLineAmount;
    private String _applicationGuid;
    private String _eschoolGuid;
    private String _premiumCommunityGuid;
    private String _userGuid;
    private String _serialNumber;
    private String _activationId;
    private String _lotusGuid;
    private String _language;
    private String _applicationRefId;
    private String _eschoolRefId;
    private String _premiumCommunityRefId;
    private String _lotusRefId;
    private String _eschoolGroupSessionsGuid;
    private String _eschoolOneOnOneSessionsGuid;
    private String _languageSwitchingGuid;
    private String _eschoolGroupSessionsRefId;
    private String _eschoolOneOnOneSessionsRefId;
    private String _languageSwitchingRefId;
    private int _autoRenew;

    public OrderLineModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static OrderLine toModel(OrderLineSoap soapModel) {
        OrderLine model = new OrderLineImpl();

        model.setId(soapModel.getId());
        model.setItemId(soapModel.getItemId());
        model.setOrderId(soapModel.getOrderId());
        model.setQuantity(soapModel.getQuantity());
        model.setCurrencyId(soapModel.getCurrencyId());
        model.setListPricePerItem(soapModel.getListPricePerItem());
        model.setYourPricePerItem(soapModel.getYourPricePerItem());
        model.setTax(soapModel.getTax());
        model.setCreatedAt(soapModel.getCreatedAt());
        model.setUpdatedAt(soapModel.getUpdatedAt());
        model.setShippingLineAmount(soapModel.getShippingLineAmount());
        model.setApplicationGuid(soapModel.getApplicationGuid());
        model.setEschoolGuid(soapModel.getEschoolGuid());
        model.setPremiumCommunityGuid(soapModel.getPremiumCommunityGuid());
        model.setUserGuid(soapModel.getUserGuid());
        model.setSerialNumber(soapModel.getSerialNumber());
        model.setActivationId(soapModel.getActivationId());
        model.setLotusGuid(soapModel.getLotusGuid());
        model.setLanguage(soapModel.getLanguage());
        model.setApplicationRefId(soapModel.getApplicationRefId());
        model.setEschoolRefId(soapModel.getEschoolRefId());
        model.setPremiumCommunityRefId(soapModel.getPremiumCommunityRefId());
        model.setLotusRefId(soapModel.getLotusRefId());
        model.setEschoolGroupSessionsGuid(soapModel.getEschoolGroupSessionsGuid());
        model.setEschoolOneOnOneSessionsGuid(soapModel.getEschoolOneOnOneSessionsGuid());
        model.setLanguageSwitchingGuid(soapModel.getLanguageSwitchingGuid());
        model.setEschoolGroupSessionsRefId(soapModel.getEschoolGroupSessionsRefId());
        model.setEschoolOneOnOneSessionsRefId(soapModel.getEschoolOneOnOneSessionsRefId());
        model.setLanguageSwitchingRefId(soapModel.getLanguageSwitchingRefId());
        model.setAutoRenew(soapModel.getAutoRenew());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<OrderLine> toModels(OrderLineSoap[] soapModels) {
        List<OrderLine> models = new ArrayList<OrderLine>(soapModels.length);

        for (OrderLineSoap soapModel : soapModels) {
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

    public int getItemId() {
        return _itemId;
    }

    public void setItemId(int itemId) {
        _itemId = itemId;
    }

    public int getOrderId() {
        return _orderId;
    }

    public void setOrderId(int orderId) {
        _orderId = orderId;
    }

    public int getQuantity() {
        return _quantity;
    }

    public void setQuantity(int quantity) {
        _quantity = quantity;
    }

    public int getCurrencyId() {
        return _currencyId;
    }

    public void setCurrencyId(int currencyId) {
        _currencyId = currencyId;
    }

    public double getListPricePerItem() {
        return _listPricePerItem;
    }

    public void setListPricePerItem(double listPricePerItem) {
        _listPricePerItem = listPricePerItem;
    }

    public double getYourPricePerItem() {
        return _yourPricePerItem;
    }

    public void setYourPricePerItem(double yourPricePerItem) {
        _yourPricePerItem = yourPricePerItem;
    }

    public double getTax() {
        return _tax;
    }

    public void setTax(double tax) {
        _tax = tax;
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

    public double getShippingLineAmount() {
        return _shippingLineAmount;
    }

    public void setShippingLineAmount(double shippingLineAmount) {
        _shippingLineAmount = shippingLineAmount;
    }

    public String getApplicationGuid() {
        if (_applicationGuid == null) {
            return StringPool.BLANK;
        } else {
            return _applicationGuid;
        }
    }

    public void setApplicationGuid(String applicationGuid) {
        _applicationGuid = applicationGuid;
    }

    public String getEschoolGuid() {
        if (_eschoolGuid == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolGuid;
        }
    }

    public void setEschoolGuid(String eschoolGuid) {
        _eschoolGuid = eschoolGuid;
    }

    public String getPremiumCommunityGuid() {
        if (_premiumCommunityGuid == null) {
            return StringPool.BLANK;
        } else {
            return _premiumCommunityGuid;
        }
    }

    public void setPremiumCommunityGuid(String premiumCommunityGuid) {
        _premiumCommunityGuid = premiumCommunityGuid;
    }

    public String getUserGuid() {
        if (_userGuid == null) {
            return StringPool.BLANK;
        } else {
            return _userGuid;
        }
    }

    public void setUserGuid(String userGuid) {
        _userGuid = userGuid;
    }

    public String getSerialNumber() {
        if (_serialNumber == null) {
            return StringPool.BLANK;
        } else {
            return _serialNumber;
        }
    }

    public void setSerialNumber(String serialNumber) {
        _serialNumber = serialNumber;
    }

    public String getActivationId() {
        if (_activationId == null) {
            return StringPool.BLANK;
        } else {
            return _activationId;
        }
    }

    public void setActivationId(String activationId) {
        _activationId = activationId;
    }

    public String getLotusGuid() {
        if (_lotusGuid == null) {
            return StringPool.BLANK;
        } else {
            return _lotusGuid;
        }
    }

    public void setLotusGuid(String lotusGuid) {
        _lotusGuid = lotusGuid;
    }

    public String getLanguage() {
        if (_language == null) {
            return StringPool.BLANK;
        } else {
            return _language;
        }
    }

    public void setLanguage(String language) {
        _language = language;
    }

    public String getApplicationRefId() {
        if (_applicationRefId == null) {
            return StringPool.BLANK;
        } else {
            return _applicationRefId;
        }
    }

    public void setApplicationRefId(String applicationRefId) {
        _applicationRefId = applicationRefId;
    }

    public String getEschoolRefId() {
        if (_eschoolRefId == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolRefId;
        }
    }

    public void setEschoolRefId(String eschoolRefId) {
        _eschoolRefId = eschoolRefId;
    }

    public String getPremiumCommunityRefId() {
        if (_premiumCommunityRefId == null) {
            return StringPool.BLANK;
        } else {
            return _premiumCommunityRefId;
        }
    }

    public void setPremiumCommunityRefId(String premiumCommunityRefId) {
        _premiumCommunityRefId = premiumCommunityRefId;
    }

    public String getLotusRefId() {
        if (_lotusRefId == null) {
            return StringPool.BLANK;
        } else {
            return _lotusRefId;
        }
    }

    public void setLotusRefId(String lotusRefId) {
        _lotusRefId = lotusRefId;
    }

    public String getEschoolGroupSessionsGuid() {
        if (_eschoolGroupSessionsGuid == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolGroupSessionsGuid;
        }
    }

    public void setEschoolGroupSessionsGuid(String eschoolGroupSessionsGuid) {
        _eschoolGroupSessionsGuid = eschoolGroupSessionsGuid;
    }

    public String getEschoolOneOnOneSessionsGuid() {
        if (_eschoolOneOnOneSessionsGuid == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolOneOnOneSessionsGuid;
        }
    }

    public void setEschoolOneOnOneSessionsGuid(
        String eschoolOneOnOneSessionsGuid) {
        _eschoolOneOnOneSessionsGuid = eschoolOneOnOneSessionsGuid;
    }

    public String getLanguageSwitchingGuid() {
        if (_languageSwitchingGuid == null) {
            return StringPool.BLANK;
        } else {
            return _languageSwitchingGuid;
        }
    }

    public void setLanguageSwitchingGuid(String languageSwitchingGuid) {
        _languageSwitchingGuid = languageSwitchingGuid;
    }

    public String getEschoolGroupSessionsRefId() {
        if (_eschoolGroupSessionsRefId == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolGroupSessionsRefId;
        }
    }

    public void setEschoolGroupSessionsRefId(String eschoolGroupSessionsRefId) {
        _eschoolGroupSessionsRefId = eschoolGroupSessionsRefId;
    }

    public String getEschoolOneOnOneSessionsRefId() {
        if (_eschoolOneOnOneSessionsRefId == null) {
            return StringPool.BLANK;
        } else {
            return _eschoolOneOnOneSessionsRefId;
        }
    }

    public void setEschoolOneOnOneSessionsRefId(
        String eschoolOneOnOneSessionsRefId) {
        _eschoolOneOnOneSessionsRefId = eschoolOneOnOneSessionsRefId;
    }

    public String getLanguageSwitchingRefId() {
        if (_languageSwitchingRefId == null) {
            return StringPool.BLANK;
        } else {
            return _languageSwitchingRefId;
        }
    }

    public void setLanguageSwitchingRefId(String languageSwitchingRefId) {
        _languageSwitchingRefId = languageSwitchingRefId;
    }

    public int getAutoRenew() {
        return _autoRenew;
    }

    public void setAutoRenew(int autoRenew) {
        _autoRenew = autoRenew;
    }

    public OrderLine toEscapedModel() {
        if (isEscapedModel()) {
            return (OrderLine) this;
        } else {
            return (OrderLine) Proxy.newProxyInstance(OrderLine.class.getClassLoader(),
                new Class[] { OrderLine.class }, new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        OrderLineImpl orderLineImpl = new OrderLineImpl();

        orderLineImpl.setId(getId());

        orderLineImpl.setItemId(getItemId());

        orderLineImpl.setOrderId(getOrderId());

        orderLineImpl.setQuantity(getQuantity());

        orderLineImpl.setCurrencyId(getCurrencyId());

        orderLineImpl.setListPricePerItem(getListPricePerItem());

        orderLineImpl.setYourPricePerItem(getYourPricePerItem());

        orderLineImpl.setTax(getTax());

        orderLineImpl.setCreatedAt(getCreatedAt());

        orderLineImpl.setUpdatedAt(getUpdatedAt());

        orderLineImpl.setShippingLineAmount(getShippingLineAmount());

        orderLineImpl.setApplicationGuid(getApplicationGuid());

        orderLineImpl.setEschoolGuid(getEschoolGuid());

        orderLineImpl.setPremiumCommunityGuid(getPremiumCommunityGuid());

        orderLineImpl.setUserGuid(getUserGuid());

        orderLineImpl.setSerialNumber(getSerialNumber());

        orderLineImpl.setActivationId(getActivationId());

        orderLineImpl.setLotusGuid(getLotusGuid());

        orderLineImpl.setLanguage(getLanguage());

        orderLineImpl.setApplicationRefId(getApplicationRefId());

        orderLineImpl.setEschoolRefId(getEschoolRefId());

        orderLineImpl.setPremiumCommunityRefId(getPremiumCommunityRefId());

        orderLineImpl.setLotusRefId(getLotusRefId());

        orderLineImpl.setEschoolGroupSessionsGuid(getEschoolGroupSessionsGuid());

        orderLineImpl.setEschoolOneOnOneSessionsGuid(getEschoolOneOnOneSessionsGuid());

        orderLineImpl.setLanguageSwitchingGuid(getLanguageSwitchingGuid());

        orderLineImpl.setEschoolGroupSessionsRefId(getEschoolGroupSessionsRefId());

        orderLineImpl.setEschoolOneOnOneSessionsRefId(getEschoolOneOnOneSessionsRefId());

        orderLineImpl.setLanguageSwitchingRefId(getLanguageSwitchingRefId());

        orderLineImpl.setAutoRenew(getAutoRenew());

        return orderLineImpl;
    }

    public int compareTo(OrderLine orderLine) {
        int pk = orderLine.getPrimaryKey();

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

        OrderLine orderLine = null;

        try {
            orderLine = (OrderLine) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = orderLine.getPrimaryKey();

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
        StringBundler sb = new StringBundler(61);

        sb.append("{id=");
        sb.append(getId());
        sb.append(", itemId=");
        sb.append(getItemId());
        sb.append(", orderId=");
        sb.append(getOrderId());
        sb.append(", quantity=");
        sb.append(getQuantity());
        sb.append(", currencyId=");
        sb.append(getCurrencyId());
        sb.append(", listPricePerItem=");
        sb.append(getListPricePerItem());
        sb.append(", yourPricePerItem=");
        sb.append(getYourPricePerItem());
        sb.append(", tax=");
        sb.append(getTax());
        sb.append(", createdAt=");
        sb.append(getCreatedAt());
        sb.append(", updatedAt=");
        sb.append(getUpdatedAt());
        sb.append(", shippingLineAmount=");
        sb.append(getShippingLineAmount());
        sb.append(", applicationGuid=");
        sb.append(getApplicationGuid());
        sb.append(", eschoolGuid=");
        sb.append(getEschoolGuid());
        sb.append(", premiumCommunityGuid=");
        sb.append(getPremiumCommunityGuid());
        sb.append(", userGuid=");
        sb.append(getUserGuid());
        sb.append(", serialNumber=");
        sb.append(getSerialNumber());
        sb.append(", activationId=");
        sb.append(getActivationId());
        sb.append(", lotusGuid=");
        sb.append(getLotusGuid());
        sb.append(", language=");
        sb.append(getLanguage());
        sb.append(", applicationRefId=");
        sb.append(getApplicationRefId());
        sb.append(", eschoolRefId=");
        sb.append(getEschoolRefId());
        sb.append(", premiumCommunityRefId=");
        sb.append(getPremiumCommunityRefId());
        sb.append(", lotusRefId=");
        sb.append(getLotusRefId());
        sb.append(", eschoolGroupSessionsGuid=");
        sb.append(getEschoolGroupSessionsGuid());
        sb.append(", eschoolOneOnOneSessionsGuid=");
        sb.append(getEschoolOneOnOneSessionsGuid());
        sb.append(", languageSwitchingGuid=");
        sb.append(getLanguageSwitchingGuid());
        sb.append(", eschoolGroupSessionsRefId=");
        sb.append(getEschoolGroupSessionsRefId());
        sb.append(", eschoolOneOnOneSessionsRefId=");
        sb.append(getEschoolOneOnOneSessionsRefId());
        sb.append(", languageSwitchingRefId=");
        sb.append(getLanguageSwitchingRefId());
        sb.append(", autoRenew=");
        sb.append(getAutoRenew());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(94);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.OrderLine");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>itemId</column-name><column-value><![CDATA[");
        sb.append(getItemId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>orderId</column-name><column-value><![CDATA[");
        sb.append(getOrderId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>quantity</column-name><column-value><![CDATA[");
        sb.append(getQuantity());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>currencyId</column-name><column-value><![CDATA[");
        sb.append(getCurrencyId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>listPricePerItem</column-name><column-value><![CDATA[");
        sb.append(getListPricePerItem());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>yourPricePerItem</column-name><column-value><![CDATA[");
        sb.append(getYourPricePerItem());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>tax</column-name><column-value><![CDATA[");
        sb.append(getTax());
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
            "<column><column-name>shippingLineAmount</column-name><column-value><![CDATA[");
        sb.append(getShippingLineAmount());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>applicationGuid</column-name><column-value><![CDATA[");
        sb.append(getApplicationGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolGuid</column-name><column-value><![CDATA[");
        sb.append(getEschoolGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>premiumCommunityGuid</column-name><column-value><![CDATA[");
        sb.append(getPremiumCommunityGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>userGuid</column-name><column-value><![CDATA[");
        sb.append(getUserGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>serialNumber</column-name><column-value><![CDATA[");
        sb.append(getSerialNumber());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>activationId</column-name><column-value><![CDATA[");
        sb.append(getActivationId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>lotusGuid</column-name><column-value><![CDATA[");
        sb.append(getLotusGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>language</column-name><column-value><![CDATA[");
        sb.append(getLanguage());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>applicationRefId</column-name><column-value><![CDATA[");
        sb.append(getApplicationRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolRefId</column-name><column-value><![CDATA[");
        sb.append(getEschoolRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>premiumCommunityRefId</column-name><column-value><![CDATA[");
        sb.append(getPremiumCommunityRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>lotusRefId</column-name><column-value><![CDATA[");
        sb.append(getLotusRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolGroupSessionsGuid</column-name><column-value><![CDATA[");
        sb.append(getEschoolGroupSessionsGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolOneOnOneSessionsGuid</column-name><column-value><![CDATA[");
        sb.append(getEschoolOneOnOneSessionsGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>languageSwitchingGuid</column-name><column-value><![CDATA[");
        sb.append(getLanguageSwitchingGuid());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolGroupSessionsRefId</column-name><column-value><![CDATA[");
        sb.append(getEschoolGroupSessionsRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>eschoolOneOnOneSessionsRefId</column-name><column-value><![CDATA[");
        sb.append(getEschoolOneOnOneSessionsRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>languageSwitchingRefId</column-name><column-value><![CDATA[");
        sb.append(getLanguageSwitchingRefId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>autoRenew</column-name><column-value><![CDATA[");
        sb.append(getAutoRenew());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}