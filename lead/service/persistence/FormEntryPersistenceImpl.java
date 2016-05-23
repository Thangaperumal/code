package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchFormEntryException;
import com.erp.lead.model.FormEntry;
import com.erp.lead.model.impl.FormEntryImpl;
import com.erp.lead.model.impl.FormEntryModelImpl;
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
 * The persistence implementation for the Form Entry service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link FormEntryUtil} to access the Form Entry persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see FormEntryPersistence
 * @see FormEntryUtil
 * @generated
 */
public class FormEntryPersistenceImpl extends BasePersistenceImpl<FormEntry>
    implements FormEntryPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = FormEntryImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_FORMID = new FinderPath(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByFormId",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_FORMID = new FinderPath(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByFormId", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_FORMENTRY = "SELECT formEntry FROM FormEntry formEntry";
    private static final String _SQL_SELECT_FORMENTRY_WHERE = "SELECT formEntry FROM FormEntry formEntry WHERE ";
    private static final String _SQL_COUNT_FORMENTRY = "SELECT COUNT(formEntry) FROM FormEntry formEntry";
    private static final String _SQL_COUNT_FORMENTRY_WHERE = "SELECT COUNT(formEntry) FROM FormEntry formEntry WHERE ";
    private static final String _FINDER_COLUMN_FORMID_FORMID_2 = "formEntry.formId = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "formEntry.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormEntry exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormEntry exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(FormEntryPersistenceImpl.class);
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
     * Caches the Form Entry in the entity cache if it is enabled.
     *
     * @param formEntry the Form Entry to cache
     */
    public void cacheResult(FormEntry formEntry) {
        EntityCacheUtil.putResult(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryImpl.class, formEntry.getPrimaryKey(), formEntry);
    }

    /**
     * Caches the Form Entries in the entity cache if it is enabled.
     *
     * @param formEntries the Form Entries to cache
     */
    public void cacheResult(List<FormEntry> formEntries) {
        for (FormEntry formEntry : formEntries) {
            if (EntityCacheUtil.getResult(
                        FormEntryModelImpl.ENTITY_CACHE_ENABLED,
                        FormEntryImpl.class, formEntry.getPrimaryKey(), this) == null) {
                cacheResult(formEntry);
            }
        }
    }

    /**
     * Clears the cache for all Form Entries.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(FormEntryImpl.class.getName());
        EntityCacheUtil.clearCache(FormEntryImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Form Entry.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(FormEntry formEntry) {
        EntityCacheUtil.removeResult(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryImpl.class, formEntry.getPrimaryKey());
    }

    /**
     * Creates a new Form Entry with the primary key. Does not add the Form Entry to the database.
     *
     * @param id the primary key for the new Form Entry
     * @return the new Form Entry
     */
    public FormEntry create(int id) {
        FormEntry formEntry = new FormEntryImpl();

        formEntry.setNew(true);
        formEntry.setPrimaryKey(id);

        return formEntry;
    }

    /**
     * Removes the Form Entry with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Form Entry to remove
     * @return the Form Entry that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Form Entry with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Form Entry to remove
     * @return the Form Entry that was removed
     * @throws com.erp.lead.NoSuchFormEntryException if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry remove(int id)
        throws NoSuchFormEntryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            FormEntry formEntry = (FormEntry) session.get(FormEntryImpl.class,
                    new Integer(id));

            if (formEntry == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchFormEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(formEntry);
        } catch (NoSuchFormEntryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormEntry removeImpl(FormEntry formEntry)
        throws SystemException {
        formEntry = toUnwrappedModel(formEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, formEntry);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryImpl.class, formEntry.getPrimaryKey());

        return formEntry;
    }

    public FormEntry updateImpl(
        com.rosettastone.cis.model.FormEntry formEntry, boolean merge)
        throws SystemException {
        formEntry = toUnwrappedModel(formEntry);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, formEntry, merge);

            formEntry.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
            FormEntryImpl.class, formEntry.getPrimaryKey(), formEntry);

        return formEntry;
    }

    protected FormEntry toUnwrappedModel(FormEntry formEntry) {
        if (formEntry instanceof FormEntryImpl) {
            return formEntry;
        }

        FormEntryImpl formEntryImpl = new FormEntryImpl();

        formEntryImpl.setNew(formEntry.isNew());
        formEntryImpl.setPrimaryKey(formEntry.getPrimaryKey());

        formEntryImpl.setId(formEntry.getId());
        formEntryImpl.setFormId(formEntry.getFormId());
        formEntryImpl.setFormUrl(formEntry.getFormUrl());
        formEntryImpl.setCreatedAt(formEntry.getCreatedAt());
        formEntryImpl.setUpdatedAt(formEntry.getUpdatedAt());

        return formEntryImpl;
    }

    /**
     * Finds the Form Entry with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Form Entry to find
     * @return the Form Entry
     * @throws com.liferay.portal.NoSuchModelException if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Entry with the primary key or throws a {@link com.erp.lead.NoSuchFormEntryException} if it could not be found.
     *
     * @param id the primary key of the Form Entry to find
     * @return the Form Entry
     * @throws com.erp.lead.NoSuchFormEntryException if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry findByPrimaryKey(int id)
        throws NoSuchFormEntryException, SystemException {
        FormEntry formEntry = fetchByPrimaryKey(id);

        if (formEntry == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchFormEntryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return formEntry;
    }

    /**
     * Finds the Form Entry with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Form Entry to find
     * @return the Form Entry, or <code>null</code> if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Entry with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Form Entry to find
     * @return the Form Entry, or <code>null</code> if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry fetchByPrimaryKey(int id) throws SystemException {
        FormEntry formEntry = (FormEntry) EntityCacheUtil.getResult(FormEntryModelImpl.ENTITY_CACHE_ENABLED,
                FormEntryImpl.class, id, this);

        if (formEntry == null) {
            Session session = null;

            try {
                session = openSession();

                formEntry = (FormEntry) session.get(FormEntryImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (formEntry != null) {
                    cacheResult(formEntry);
                }

                closeSession(session);
            }
        }

        return formEntry;
    }

    /**
     * Finds all the Form Entries where formId = &#63;.
     *
     * @param formId the form id to search with
     * @return the matching Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findByFormId(int formId) throws SystemException {
        return findByFormId(formId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Entries where formId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formId the form id to search with
     * @param start the lower bound of the range of Form Entries to return
     * @param end the upper bound of the range of Form Entries to return (not inclusive)
     * @return the range of matching Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findByFormId(int formId, int start, int end)
        throws SystemException {
        return findByFormId(formId, start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Entries where formId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formId the form id to search with
     * @param start the lower bound of the range of Form Entries to return
     * @param end the upper bound of the range of Form Entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findByFormId(int formId, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                formId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormEntry> list = (List<FormEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORMENTRY_WHERE);

            query.append(_FINDER_COLUMN_FORMID_FORMID_2);

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

                qPos.add(formId);

                list = (List<FormEntry>) QueryUtil.list(q, getDialect(), start,
                        end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FORMID,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMID,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form Entry in the ordered set where formId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formId the form id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form Entry
     * @throws com.erp.lead.NoSuchFormEntryException if a matching Form Entry could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry findByFormId_First(int formId,
        OrderByComparator orderByComparator)
        throws NoSuchFormEntryException, SystemException {
        List<FormEntry> list = findByFormId(formId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formId=");
            msg.append(formId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form Entry in the ordered set where formId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formId the form id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form Entry
     * @throws com.erp.lead.NoSuchFormEntryException if a matching Form Entry could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry findByFormId_Last(int formId,
        OrderByComparator orderByComparator)
        throws NoSuchFormEntryException, SystemException {
        int count = countByFormId(formId);

        List<FormEntry> list = findByFormId(formId, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formId=");
            msg.append(formId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormEntryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Form Entries before and after the current Form Entry in the ordered set where formId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form Entry
     * @param formId the form id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form Entry
     * @throws com.erp.lead.NoSuchFormEntryException if a Form Entry with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEntry[] findByFormId_PrevAndNext(int id, int formId,
        OrderByComparator orderByComparator)
        throws NoSuchFormEntryException, SystemException {
        FormEntry formEntry = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            FormEntry[] array = new FormEntryImpl[3];

            array[0] = getByFormId_PrevAndNext(session, formEntry, formId,
                    orderByComparator, true);

            array[1] = formEntry;

            array[2] = getByFormId_PrevAndNext(session, formEntry, formId,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormEntry getByFormId_PrevAndNext(Session session,
        FormEntry formEntry, int formId, OrderByComparator orderByComparator,
        boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORMENTRY_WHERE);

        query.append(_FINDER_COLUMN_FORMID_FORMID_2);

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

        qPos.add(formId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(formEntry);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<FormEntry> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Form Entries.
     *
     * @return the Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Entries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Entries to return
     * @param end the upper bound of the range of Form Entries to return (not inclusive)
     * @return the range of Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Entries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Entries to return
     * @param end the upper bound of the range of Form Entries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Form Entries
     * @throws SystemException if a system exception occurred
     */
    public List<FormEntry> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormEntry> list = (List<FormEntry>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_FORMENTRY);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_FORMENTRY;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<FormEntry>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<FormEntry>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Form Entries where formId = &#63; from the database.
     *
     * @param formId the form id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByFormId(int formId) throws SystemException {
        for (FormEntry formEntry : findByFormId(formId)) {
            remove(formEntry);
        }
    }

    /**
     * Removes all the Form Entries from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (FormEntry formEntry : findAll()) {
            remove(formEntry);
        }
    }

    /**
     * Counts all the Form Entries where formId = &#63;.
     *
     * @param formId the form id to search with
     * @return the number of matching Form Entries
     * @throws SystemException if a system exception occurred
     */
    public int countByFormId(int formId) throws SystemException {
        Object[] finderArgs = new Object[] { formId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMID,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORMENTRY_WHERE);

            query.append(_FINDER_COLUMN_FORMID_FORMID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(formId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMID,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Form Entries.
     *
     * @return the number of Form Entries
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

                Query q = session.createQuery(_SQL_COUNT_FORMENTRY);

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
     * Initializes the Form Entry persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.FormEntry")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<FormEntry>> listenersList = new ArrayList<ModelListener<FormEntry>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<FormEntry>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(FormEntryImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
