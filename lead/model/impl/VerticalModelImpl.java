package com.erp.lead.model.impl;

import com.erp.lead.model.Vertical;
import com.erp.lead.model.VerticalModel;
import com.erp.lead.model.VerticalSoap;
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
 * The base model implementation for the Vertical service. Represents a row in the &quot;verticals&quot; database table, with each column mapped to a property of this class.
 *
 * <p>
 * This implementation and its corresponding interface {@link com.erp.lead.model.VerticalModel} exist only as a container for the default property accessors generated by ServiceBuilder. Helper methods and all application logic should be put in {@link VerticalImpl}.
 * </p>
 *
 * <p>
 * Never modify or reference this class directly. All methods that expect a Vertical model instance should use the {@link com.erp.lead.model.Vertical} interface instead.
 * </p>
 *
 * @author Thangaperumal
 * @see VerticalImpl
 * @see com.erp.lead.model.Vertical
 * @see com.erp.lead.model.VerticalModel
 * @generated
 */
public class VerticalModelImpl extends BaseModelImpl<Vertical>
    implements VerticalModel {
    public static final String TABLE_NAME = "verticals";
    public static final Object[][] TABLE_COLUMNS = {
            { "id", new Integer(Types.INTEGER) },
            { "description", new Integer(Types.VARCHAR) },
            { "edition", new Integer(Types.VARCHAR) }
        };
    public static final String TABLE_SQL_CREATE = "create table verticals (id INTEGER not null primary key IDENTITY,description VARCHAR(75) null,edition VARCHAR(75) null)";
    public static final String TABLE_SQL_DROP = "drop table verticals";
    public static final String DATA_SOURCE = "cisDS";
    public static final String SESSION_FACTORY = "cisSessionFactory";
    public static final String TX_MANAGER = "cisTransactionManager";
    public static final boolean ENTITY_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.entity.cache.enabled.com.erp.lead.model.Vertical"),
            true);
    public static final boolean FINDER_CACHE_ENABLED = GetterUtil.getBoolean(com.liferay.util.service.ServiceProps.get(
                "value.object.finder.cache.enabled.com.erp.lead.model.Vertical"),
            true);
    public static final long LOCK_EXPIRATION_TIME = GetterUtil.getLong(com.liferay.util.service.ServiceProps.get(
                "lock.expiration.time.com.erp.lead.model.Vertical"));
    private int _id;
    private String _description;
    private String _edition;

    public VerticalModelImpl() {
    }

    /**
     * Converts the soap model instance into a normal model instance.
     *
     * @param soapModel the soap model instance to convert
     * @return the normal model instance
     */
    public static Vertical toModel(VerticalSoap soapModel) {
        Vertical model = new VerticalImpl();

        model.setId(soapModel.getId());
        model.setDescription(soapModel.getDescription());
        model.setEdition(soapModel.getEdition());

        return model;
    }

    /**
     * Converts the soap model instances into normal model instances.
     *
     * @param soapModels the soap model instances to convert
     * @return the normal model instances
     */
    public static List<Vertical> toModels(VerticalSoap[] soapModels) {
        List<Vertical> models = new ArrayList<Vertical>(soapModels.length);

        for (VerticalSoap soapModel : soapModels) {
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

    public String getEdition() {
        if (_edition == null) {
            return StringPool.BLANK;
        } else {
            return _edition;
        }
    }

    public void setEdition(String edition) {
        _edition = edition;
    }

    public Vertical toEscapedModel() {
        if (isEscapedModel()) {
            return (Vertical) this;
        } else {
            return (Vertical) Proxy.newProxyInstance(Vertical.class.getClassLoader(),
                new Class[] { Vertical.class }, new AutoEscapeBeanHandler(this));
        }
    }

    public Object clone() {
        VerticalImpl verticalImpl = new VerticalImpl();

        verticalImpl.setId(getId());

        verticalImpl.setDescription(getDescription());

        verticalImpl.setEdition(getEdition());

        return verticalImpl;
    }

    public int compareTo(Vertical vertical) {
        int pk = vertical.getPrimaryKey();

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

        Vertical vertical = null;

        try {
            vertical = (Vertical) obj;
        } catch (ClassCastException cce) {
            return false;
        }

        int pk = vertical.getPrimaryKey();

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
        StringBundler sb = new StringBundler(7);

        sb.append("{id=");
        sb.append(getId());
        sb.append(", description=");
        sb.append(getDescription());
        sb.append(", edition=");
        sb.append(getEdition());
        sb.append("}");

        return sb.toString();
    }

    public String toXmlString() {
        StringBundler sb = new StringBundler(13);

        sb.append("<model><model-name>");
        sb.append("com.erp.lead.model.Vertical");
        sb.append("</model-name>");

        sb.append(
            "<column><column-name>id</column-name><column-value><![CDATA[");
        sb.append(getId());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>description</column-name><column-value><![CDATA[");
        sb.append(getDescription());
        sb.append("]]></column-value></column>");
        sb.append(
            "<column><column-name>edition</column-name><column-value><![CDATA[");
        sb.append(getEdition());
        sb.append("]]></column-value></column>");

        sb.append("</model>");

        return sb.toString();
    }
}
