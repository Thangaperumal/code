package com.erp.lead.model.impl;

import com.erp.lead.model.EcOrderIdSequence;
import com.erp.lead.model.EcOrderIdSequenceModel;
import com.erp.lead.model.EcOrderIdSequenceSoap;
import com.liferay.portal.kernel.bean.AutoEscapeBeanHandler;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.model.impl.BaseModelImpl;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 * The base model implementation for the EcOrderIdSequence service. Represents a row in the &quot;ec_order_id_sequence&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.EcOrderIdSequenceModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link EcOrderIdSequenceImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a EcOrderIdSequence model instance should use the {@link com.erp.lead.model.EcOrderIdSequence} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see EcOrderIdSequenceImpl
 * @see com.erp.lead.model.EcOrderIdSequence
 * @see com.erp.lead.model.EcOrderIdSequenceModel
 * @generated
 */
public class EcOrderIdSequenceModelImpl extends BaseModelImpl<EcOrderIdSequence>
    implements EcOrderIdSequenceModel {
    public static final String TABLE_NAME = "ec_order_id_sequence";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) }
        };
    public static final String TABLE_SQL_CREATE = "create table ec_order_id_sequence (id INTEGER not null primary key)";
    public static final String TABLE_SQL_DROP = "drop table ec_order_id_sequence";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.rosettastone.cis.model.EcOrderIdSequence"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.rosettastone.cis.model.EcOrderIdSequence"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.rosettastone.cis.model.EcOrderIdSequence"));
    private int _id;

    public EcOrderIdSequenceModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static EcOrderIdSequence toModel(EcOrderIdSequenceSoap soapModel) {
        EcOrderIdSequence model = new EcOrderIdSequenceImpl();

        model.setId(soapModel.getId());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<EcOrderIdSequence> toModels(
        EcOrderIdSequenceSoap[] soapModels) {
        List<EcOrderIdSequence> models = new ArrayList<EcOrderIdSequence>(soapModels.length);

        for (EcOrderIdSequenceSoap soapModel : soapModels) {
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

    public EcOrderIdSequence toEscapedModel() {
        if (isEscapedModel()) {
            return (EcOrderIdSequence) this;
        } else {
            return (EcOrderIdSequence) Proxy.newProxyInstance(EcOrderIdSequence.class.getClassLoader(),
                new Class[] { EcOrderIdSequence.class },
                new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        EcOrderIdSequenceImpl ecOrderIdSequenceImpl = new EcOrderIdSequenceImpl();

        ecOrderIdSequenceImpl.setId(getId());

        return ecOrderIdSequenceImpl;
    }

    public int compareTo(EcOrderIdSequence ecOrderIdSequence) {
        int pk = ecOrderIdSequence.getPrimaryKey();

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

        EcOrderIdSequence ecOrderIdSequence = null;

        try {
            ecOrderIdSequence = (EcOrderIdSequence) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = ecOrderIdSequence.getPrimaryKey();

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
        StringBundler sb = new StringBundler(3);

        sb.append("{id=");
        sb.append(getId());

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(7);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.EcOrderIdSequence");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}