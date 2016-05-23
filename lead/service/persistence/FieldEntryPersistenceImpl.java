package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchFieldEntryException;
import com.erp.lead.model.FieldEntry;
import com.erp.lead.model.impl.FieldEntryImpl;
import com.erp.lead.model.impl.FieldEntryModelImpl;
import com.erp.lead.service.persistence.AddressPersistence;
import com.erp.lead.service.persistence.CountryPersistence;
import com.erp.lead.service.persistence.CustomerPersistence;
import com.erp.lead.service.persistence.DemoItemPersistence;
import com.erp.lead.service.persistence.EcOrderIdSequencePersistence;
import com.erp.lead.service.persistence.FieldEntryPersistence;
import com.erp.lead.service.persistence.FormEmailTemplatePersistence;
import com.erp.lead.service.persistence.FormEntryPersistence;
import com.erp.lead.service.persistence.FormExportLogPersistence;
import com.erp.lead.service.persistence.FormPersistence;
import com.erp.lead.service.persistence.LocalizationPersistence;
import com.erp.lead.service.persistence.OrderLinePersistence;
import com.erp.lead.service.persistence.OrderPersistence;
import com.erp.lead.service.persistence.PromoPersistence;
import com.erp.lead.service.persistence.SitePersistence;
import com.erp.lead.service.persistence.VerticalPersistence;
import com.liferay.portal.NoSuchModelException;
import com.liferay.portal.kernel.annotation.BeanReference;
import com.liferay.portal.kernel.cache.CacheRegistryUtil;
import com.liferay.portal.kernel.dao.orm.EntityCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderCacheUtil;
import com.liferay.portal.kernel.dao.orm.FinderPath;
import com.liferay.portal.kernel.dao.orm.Query;
import com.liferay.portal.kernel.dao.orm.QueryPos;
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
import com.liferay.portal.kernel.util.StringPool;
import com.liferay.portal.kernel.util.StringUtil;
import com.liferay.portal.model.ModelListener;
import com.liferay.portal.service.persistence.BatchSessionUtil;
import com.liferay.portal.service.persistence.ResourcePersistence;
import com.liferay.portal.service.persistence.UserPersistence;
import com.liferay.portal.service.persistence.impl.BasePersistenceImpl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The persistence implementation for the Field Entry service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link FieldEntryUtil} to access the Field Entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see FieldEntryPersistence
 * @see FieldEntryUtil
 * @generated
 */
public class FieldEntryPersistenceImpl extends BasePersistenceImpl<FieldEntry>
    implements FieldEntryPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = FieldEntryImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_FORMENTRYID = new FinderPath(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByFormEntryId",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_FORMENTRYID = new FinderPath(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByFormEntryId", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_FIELDENTRY = "SELECT fieldEntry FROM FieldEntry fieldEntry";
    private static final String _SQL_SELECT_FIELDENTRY_WHERE = "SELECT fieldEntry FROM FieldEntry fieldEntry WHERE ";
    private static final String _SQL_COUNT_FIELDENTRY = "SELECT COUNT(fieldEntry) FROM FieldEntry fieldEntry";
    private static final String _SQL_COUNT_FIELDENTRY_WHERE = "SELECT COUNT(fieldEntry) FROM FieldEntry fieldEntry WHERE ";
    private static final String _FINDER_COLUMN_FORMENTRYID_FORMENTRYID_2 = "fieldEntry.formEntryId = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "fieldEntry.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FieldEntry exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FieldEntry exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(FieldEntryPersistenceImpl.class);
    @BeanReference(type = FormPersistence.class)
    protected FormPersistence formPersistence;
    @BeanReference(type = FormEntryPersistence.class)
    protected FormEntryPersistence formEntryPersistence;
    @BeanReference(type = FieldEntryPersistence.class)
    protected FieldEntryPersistence fieldEntryPersistence;
    @BeanReference(type = FormExportLogPersistence.class)
    protected FormExportLogPersistence formExportLogPersistence;
    @BeanReference(type = FormEmailTemplatePersistence.class)
    protected FormEmailTemplatePersistence formEmailTemplatePersistence;
    @BeanReference(type = SitePersistence.class)
    protected SitePersistence sitePersistence;
    @BeanReference(type = OrderPersistence.class)
    protected OrderPersistence orderPersistence;
    @BeanReference(type = AddressPersistence.class)
    protected AddressPersistence addressPersistence;
    @BeanReference(type = CustomerPersistence.class)
    protected CustomerPersistence customerPersistence;
    @BeanReference(type = OrderLinePersistence.class)
    protected OrderLinePersistence orderLinePersistence;
    @BeanReference(type = PromoPersistence.class)
    protected PromoPersistence promoPersistence;
    @BeanReference(type = VerticalPersistence.class)
    protected VerticalPersistence verticalPersistence;
    @BeanReference(type = DemoItemPersistence.class)
    protected DemoItemPersistence demoItemPersistence;
    @BeanReference(type = LocalizationPersistence.class)
    protected LocalizationPersistence localizationPersistence;
    @BeanReference(type = CountryPersistence.class)
    protected CountryPersistence countryPersistence;
    @BeanReference(type = EcOrderIdSequencePersistence.class)
    protected EcOrderIdSequencePersistence ecOrderIdSequencePersistence;
    @BeanReference(type = ResourcePersistence.class)
    protected ResourcePersistence resourcePersistence;
    @BeanReference(type = UserPersistence.class)
    protected UserPersistence userPersistence;

    /**
     * Caches the Field Entry in the entity cache if it is enabled.
     *
     * @param fieldEntry the Field Entry to cache
     */
    public void cacheResult(FieldEntry fieldEntry) {
        EntityCacheUtil.putResult(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryImpl.class, fieldEntry.getPrimaryKey(), fieldEntry);
    }

    /**
     * Caches the Field Entries in the entity cache if it is enabled.
     *
     * @param fieldEntries the Field Entries to cache
     */
    public void cacheResult(List<FieldEntry> fieldEntries) {
        for (FieldEntry fieldEntry : fieldEntries) {
            if (EntityCacheUtil.getResult(
                        FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
                        FieldEntryImpl.class, fieldEntry.getPrimaryKey(), this) == null) {
                cacheResult(fieldEntry);
            }
        }
    }

    /**
     * Clears the cache for all Field Entries.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(FieldEntryImpl.class.getName());
        EntityCacheUtil.clearCache(FieldEntryImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Field Entry.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(FieldEntry fieldEntry) {
        EntityCacheUtil.removeResult(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryImpl.class, fieldEntry.getPrimaryKey());
    }

    /**
     * Creates a new Field Entry with the primary key. Does not add the Field Entry to the database.
     *
     * @param id the primary key for the new Field Entry
     * @return the new Field Entry
     */
    public FieldEntry create(int id) {
        FieldEntry fieldEntry = new FieldEntryImpl();

        fieldEntry.setNew(true);
        fieldEntry.setPrimaryKey(id);

        return fieldEntry;
    }

    /**
     * Removes the Field Entry with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Field Entry to remove
     * @return the Field Entry that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Field Entry with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Field Entry to remove
     * @return the Field Entry that was removed
     * @throws com.erp.lead.NoSuchFieldEntryException if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry remove(int id)
        throws NoSuchFieldEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            FieldEntry fieldEntry = (FieldEntry) session.get(FieldEntryImpl.class,
                    new Integer(id));

            if (fieldEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchFieldEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(fieldEntry);
        } catch (NoSuchFieldEntryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FieldEntry removeImpl(FieldEntry fieldEntry)
        throws SystemException {
        fieldEntry = toUnwrappedModel(fieldEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, fieldEntry);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryImpl.class, fieldEntry.getPrimaryKey());

        return fieldEntry;
    }

    public FieldEntry updateImpl(
        com.erp.lead.model.FieldEntry fieldEntry, boolean merge)
        throws SystemException {
        fieldEntry = toUnwrappedModel(fieldEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, fieldEntry, merge);

            fieldEntry.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
            FieldEntryImpl.class, fieldEntry.getPrimaryKey(), fieldEntry);

        return fieldEntry;
    }

    protected FieldEntry toUnwrappedModel(FieldEntry fieldEntry) {
        if (fieldEntry instanceof FieldEntryImpl) {
            return fieldEntry;
        }

        FieldEntryImpl fieldEntryImpl = new FieldEntryImpl();

        fieldEntryImpl.setNew(fieldEntry.isNew());
        fieldEntryImpl.setPrimaryKey(fieldEntry.getPrimaryKey());

        fieldEntryImpl.setId(fieldEntry.getId());
        fieldEntryImpl.setFormEntryId(fieldEntry.getFormEntryId());
        fieldEntryImpl.setName(fieldEntry.getName());
        fieldEntryImpl.setValue(fieldEntry.getValue());

        return fieldEntryImpl;
    }

    /**
     * Finds the Field Entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Field Entry to find
     * @return the Field Entry
     * @throws com.liferay.portal.NoSuchModelException if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Field Entry with the primary key or throws a {@link com.erp.lead.NoSuchFieldEntryException} if it could not be found.
     *
     * @param id the primary key of the Field Entry to find
     * @return the Field Entry
     * @throws com.erp.lead.NoSuchFieldEntryException if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry findByPrimaryKey(int id)
        throws NoSuchFieldEntryException, SystemException {
        FieldEntry fieldEntry = fetchByPrimaryKey(id);

        if (fieldEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchFieldEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return fieldEntry;
    }

    /**
     * Finds the Field Entry with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Field Entry to find
     * @return the Field Entry, or <code>null</code> if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Field Entry with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Field Entry to find
     * @return the Field Entry, or <code>null</code> if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry fetchByPrimaryKey(int id) throws SystemException {
        FieldEntry fieldEntry = (FieldEntry) EntityCacheUtil.getResult(FieldEntryModelImpl.ENTITY_CACHE_ENABLED,
                FieldEntryImpl.class, id, this);

        if (fieldEntry == null) {
            Session session = null;

            try {
                session = openSession();

                fieldEntry = (FieldEntry) session.get(FieldEntryImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (fieldEntry != null) {
                    cacheResult(fieldEntry);
                }

                closeSession(session);
            }
        }

        return fieldEntry;
    }

    /**
     * Finds all the Field Entries where formEntryId = &#63;.
     *
     * @param formEntryId the form entry id to search with
     * @return the matching Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findByFormEntryId(int formEntryId)
        throws SystemException {
        return findByFormEntryId(formEntryId, QueryUtil.ALL_POS,
            QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Field Entries where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Field Entries to return
     * @param end the upper bound of the range of Field Entries to return (not inclusive)
     * @return the range of matching Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findByFormEntryId(int formEntryId, int start,
        int end) throws SystemException {
        return findByFormEntryId(formEntryId, start, end, null);
    }

    /**
     * Finds an ordered range of all the Field Entries where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Field Entries to return
     * @param end the upper bound of the range of Field Entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findByFormEntryId(int formEntryId, int start,
        int end, OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                formEntryId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FieldEntry> list = (List<FieldEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMENTRYID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FIELDENTRY_WHERE);

            query.append(_FINDER_COLUMN_FORMENTRYID_FORMENTRYID_2);

            if (orderByComparator != null) {
                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(formEntryId);

                list = (List<FieldEntry>) QueryUtil.list(q, getDialect(),
                        start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FORMENTRYID,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMENTRYID,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Field Entry in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Field Entry
     * @throws com.erp.lead.NoSuchFieldEntryException if a matching Field Entry could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry findByFormEntryId_First(int formEntryId,
        OrderByComparator orderByComparator)
        throws NoSuchFieldEntryException, SystemException {
        List<FieldEntry> list = findByFormEntryId(formEntryId, 0, 1,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFieldEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Field Entry in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Field Entry
     * @throws com.erp.lead.NoSuchFieldEntryException if a matching Field Entry could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry findByFormEntryId_Last(int formEntryId,
        OrderByComparator orderByComparator)
        throws NoSuchFieldEntryException, SystemException {
        int count = countByFormEntryId(formEntryId);

        List<FieldEntry> list = findByFormEntryId(formEntryId, count - 1,
                count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFieldEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Field Entries before and after the current Field Entry in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Field Entry
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Field Entry
     * @throws com.erp.lead.NoSuchFieldEntryException if a Field Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FieldEntry[] findByFormEntryId_PrevAndNext(int id, int formEntryId,
        OrderByComparator orderByComparator)
        throws NoSuchFieldEntryException, SystemException {
        FieldEntry fieldEntry = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            FieldEntry[] array = new FieldEntryImpl[3];

            array[0] = getByFormEntryId_PrevAndNext(session, fieldEntry,
                    formEntryId, orderByComparator, true);

            array[1] = fieldEntry;

            array[2] = getByFormEntryId_PrevAndNext(session, fieldEntry,
                    formEntryId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FieldEntry getByFormEntryId_PrevAndNext(Session session,
        FieldEntry fieldEntry, int formEntryId,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FIELDENTRY_WHERE);

        query.append(_FINDER_COLUMN_FORMENTRYID_FORMENTRYID_2);

        if (orderByComparator != null) {
            String[] orderByFields = orderByComparator.getOrderByFields();

            if (orderByFields.length > 0) {
                query.append(WHERE_AND);
            }

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN_HAS_NEXT);
                    } else {
                        query.append(WHERE_LESSER_THAN_HAS_NEXT);
                    }
                } else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(WHERE_GREATER_THAN);
                    } else {
                        query.append(WHERE_LESSER_THAN);
                    }
                }
            }

            query.append(ORDER_BY_CLAUSE);

            for (int i = 0; i < orderByFields.length; i++) {
                query.append(_ORDER_BY_ENTITY_ALIAS);
                query.append(orderByFields[i]);

                if ((i + 1) < orderByFields.length) {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC_HAS_NEXT);
                    } else {
                        query.append(ORDER_BY_DESC_HAS_NEXT);
                    }
                } else {
                    if (orderByComparator.isAscending() ^ previous) {
                        query.append(ORDER_BY_ASC);
                    } else {
                        query.append(ORDER_BY_DESC);
                    }
                }
            }
        }

        String sql = query.toString();

        Query q = session.createQuery(sql);

        q.setFirstResult(0);
        q.setMaxResults(2);

        QueryPos qPos = QueryPos.getInstance(q);

        qPos.add(formEntryId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(fieldEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<FieldEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Field Entries.
     *
     * @return the Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Field Entries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Field Entries to return
     * @param end the upper bound of the range of Field Entries to return (not inclusive)
     * @return the range of Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Field Entries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Field Entries to return
     * @param end the upper bound of the range of Field Entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Field Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FieldEntry> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FieldEntry> list = (List<FieldEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_FIELDENTRY);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_FIELDENTRY;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<FieldEntry>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<FieldEntry>) QueryUtil.list(q, getDialect(),
                            start, end);
                }
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_ALL,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_ALL, finderArgs,
                        list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Removes all the Field Entries where formEntryId = &#63; from the database.
     *
     * @param formEntryId the form entry id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByFormEntryId(int formEntryId) throws SystemException {
        for (FieldEntry fieldEntry : findByFormEntryId(formEntryId)) {
            remove(fieldEntry);
        }
    }

    /**
     * Removes all the Field Entries from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (FieldEntry fieldEntry : findAll()) {
            remove(fieldEntry);
        }
    }

    /**
     * Counts all the Field Entries where formEntryId = &#63;.
     *
     * @param formEntryId the form entry id to search with
     * @return the number of matching Field Entries
     * @throws SystemException if a system exception occurred
     */
    public int countByFormEntryId(int formEntryId) throws SystemException {
        Object[] finderArgs = new Object[] { formEntryId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMENTRYID,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FIELDENTRY_WHERE);

            query.append(_FINDER_COLUMN_FORMENTRYID_FORMENTRYID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(formEntryId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMENTRYID,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Field Entries.
     *
     * @return the number of Field Entries
     * @throws SystemException if a system exception occurred
     */
    public int countAll() throws SystemException {
        Object[] finderArgs = new Object[0];

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_ALL,
                finderArgs, this);

        if (count == null) {
            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(_SQL_COUNT_FIELDENTRY);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_ALL, finderArgs,
                    count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Initializes the Field Entry persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.FieldEntry")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<FieldEntry>> listenersList = new ArrayList<ModelListener<FieldEntry>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<FieldEntry>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(FieldEntryImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
