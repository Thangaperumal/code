package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchFormExportLogException;
import com.erp.lead.model.FormExportLog;
import com.erp.lead.model.impl.FormExportLogImpl;
import com.erp.lead.model.impl.FormExportLogModelImpl;
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
 * The persistence implementation for the Form Export Logs service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link FormExportLogUtil} to access the Form Export Logs persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see FormExportLogPersistence
 * @see FormExportLogUtil
 * @generated
 */
public class FormExportLogPersistenceImpl extends BasePersistenceImpl<FormExportLog>
    implements FormExportLogPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = FormExportLogImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_FORMENTRYID = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "findByFormEntryId",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_FORMENTRYID = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "countByFormEntryId",
            new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_EXPORTERFORMENTRYID = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "findByExporterFormEntryId",
            new String[] {
                String.class.getName(), Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_EXPORTERFORMENTRYID = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "countByExporterFormEntryId",
            new String[] { String.class.getName(), Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "countAll", new String[0]);
    private static final String _SQL_SELECT_FORMEXPORTLOG = "SELECT formExportLog FROM FormExportLog formExportLog";
    private static final String _SQL_SELECT_FORMEXPORTLOG_WHERE = "SELECT formExportLog FROM FormExportLog formExportLog WHERE ";
    private static final String _SQL_COUNT_FORMEXPORTLOG = "SELECT COUNT(formExportLog) FROM FormExportLog formExportLog";
    private static final String _SQL_COUNT_FORMEXPORTLOG_WHERE = "SELECT COUNT(formExportLog) FROM FormExportLog formExportLog WHERE ";
    private static final String _FINDER_COLUMN_FORMENTRYID_FORMENTRYID_2 = "formExportLog.formEntryId = ?";
    private static final String _FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_1 = "formExportLog.exporter IS NULL AND ";
    private static final String _FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_2 = "formExportLog.exporter = ? AND ";
    private static final String _FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_3 = "(formExportLog.exporter IS NULL OR formExportLog.exporter = ?) AND ";
    private static final String _FINDER_COLUMN_EXPORTERFORMENTRYID_FORMENTRYID_2 =
        "formExportLog.formEntryId = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "formExportLog.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormExportLog exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No FormExportLog exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(FormExportLogPersistenceImpl.class);
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
     * Caches the Form Export Logs in the entity cache if it is enabled.
     *
     * @param formExportLog the Form Export Logs to cache
     */
    public void cacheResult(FormExportLog formExportLog) {
        EntityCacheUtil.putResult(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogImpl.class, formExportLog.getPrimaryKey(),
            formExportLog);
    }

    /**
     * Caches the Form Export Logses in the entity cache if it is enabled.
     *
     * @param formExportLogs the Form Export Logses to cache
     */
    public void cacheResult(List<FormExportLog> formExportLogs) {
        for (FormExportLog formExportLog : formExportLogs) {
            if (EntityCacheUtil.getResult(
                        FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
                        FormExportLogImpl.class, formExportLog.getPrimaryKey(),
                        this) == null) {
                cacheResult(formExportLog);
            }
        }
    }

    /**
     * Clears the cache for all Form Export Logses.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(FormExportLogImpl.class.getName());
        EntityCacheUtil.clearCache(FormExportLogImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Form Export Logs.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(FormExportLog formExportLog) {
        EntityCacheUtil.removeResult(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogImpl.class, formExportLog.getPrimaryKey());
    }

    /**
     * Creates a new Form Export Logs with the primary key. Does not add the Form Export Logs to the database.
     *
     * @param id the primary key for the new Form Export Logs
     * @return the new Form Export Logs
     */
    public FormExportLog create(int id) {
        FormExportLog formExportLog = new FormExportLogImpl();

        formExportLog.setNew(true);
        formExportLog.setPrimaryKey(id);

        return formExportLog;
    }

    /**
     * Removes the Form Export Logs with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Form Export Logs to remove
     * @return the Form Export Logs that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Form Export Logs with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Form Export Logs to remove
     * @return the Form Export Logs that was removed
     * @throws com.erp.lead.NoSuchFormExportLogException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog remove(int id)
        throws NoSuchFormExportLogException, SystemException {
        Session session = null;

        try {
            session = openSession();

            FormExportLog formExportLog = (FormExportLog) session.get(FormExportLogImpl.class,
                    new Integer(id));

            if (formExportLog == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchFormExportLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(formExportLog);
        } catch (NoSuchFormExportLogException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormExportLog removeImpl(FormExportLog formExportLog)
        throws SystemException {
        formExportLog = toUnwrappedModel(formExportLog);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, formExportLog);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogImpl.class, formExportLog.getPrimaryKey());

        return formExportLog;
    }

    public FormExportLog updateImpl(
        com.erp.lead.model.FormExportLog formExportLog, boolean merge)
        throws SystemException {
        formExportLog = toUnwrappedModel(formExportLog);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, formExportLog, merge);

            formExportLog.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
            FormExportLogImpl.class, formExportLog.getPrimaryKey(),
            formExportLog);

        return formExportLog;
    }

    protected FormExportLog toUnwrappedModel(FormExportLog formExportLog) {
        if (formExportLog instanceof FormExportLogImpl) {
            return formExportLog;
        }

        FormExportLogImpl formExportLogImpl = new FormExportLogImpl();

        formExportLogImpl.setNew(formExportLog.isNew());
        formExportLogImpl.setPrimaryKey(formExportLog.getPrimaryKey());

        formExportLogImpl.setId(formExportLog.getId());
        formExportLogImpl.setFormEntryId(formExportLog.getFormEntryId());
        formExportLogImpl.setExporter(formExportLog.getExporter());
        formExportLogImpl.setSuccessful(formExportLog.getSuccessful());
        formExportLogImpl.setExternalId(formExportLog.getExternalId());
        formExportLogImpl.setCreatedAt(formExportLog.getCreatedAt());
        formExportLogImpl.setText(formExportLog.getText());

        return formExportLogImpl;
    }

    /**
     * Finds the Form Export Logs with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Form Export Logs to find
     * @return the Form Export Logs
     * @throws com.liferay.portal.NoSuchModelException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Export Logs with the primary key or throws a {@link com.erp.lead.NoSuchFormExportLogException} if it could not be found.
     *
     * @param id the primary key of the Form Export Logs to find
     * @return the Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByPrimaryKey(int id)
        throws NoSuchFormExportLogException, SystemException {
        FormExportLog formExportLog = fetchByPrimaryKey(id);

        if (formExportLog == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchFormExportLogException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return formExportLog;
    }

    /**
     * Finds the Form Export Logs with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Form Export Logs to find
     * @return the Form Export Logs, or <code>null</code> if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Export Logs with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Form Export Logs to find
     * @return the Form Export Logs, or <code>null</code> if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog fetchByPrimaryKey(int id) throws SystemException {
        FormExportLog formExportLog = (FormExportLog) EntityCacheUtil.getResult(FormExportLogModelImpl.ENTITY_CACHE_ENABLED,
                FormExportLogImpl.class, id, this);

        if (formExportLog == null) {
            Session session = null;

            try {
                session = openSession();

                formExportLog = (FormExportLog) session.get(FormExportLogImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (formExportLog != null) {
                    cacheResult(formExportLog);
                }

                closeSession(session);
            }
        }

        return formExportLog;
    }

    /**
     * Finds all the Form Export Logses where formEntryId = &#63;.
     *
     * @param formEntryId the form entry id to search with
     * @return the matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByFormEntryId(int formEntryId)
        throws SystemException {
        return findByFormEntryId(formEntryId, QueryUtil.ALL_POS,
            QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Export Logses where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @return the range of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByFormEntryId(int formEntryId, int start,
        int end) throws SystemException {
        return findByFormEntryId(formEntryId, start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Export Logses where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByFormEntryId(int formEntryId, int start,
        int end, OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                formEntryId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormExportLog> list = (List<FormExportLog>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMENTRYID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORMEXPORTLOG_WHERE);

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

                list = (List<FormExportLog>) QueryUtil.list(q, getDialect(),
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
     * Finds the first Form Export Logs in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a matching Form Export Logs could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByFormEntryId_First(int formEntryId,
        OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        List<FormExportLog> list = findByFormEntryId(formEntryId, 0, 1,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormExportLogException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form Export Logs in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a matching Form Export Logs could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByFormEntryId_Last(int formEntryId,
        OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        int count = countByFormEntryId(formEntryId);

        List<FormExportLog> list = findByFormEntryId(formEntryId, count - 1,
                count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormExportLogException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Form Export Logses before and after the current Form Export Logs in the ordered set where formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form Export Logs
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog[] findByFormEntryId_PrevAndNext(int id,
        int formEntryId, OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        FormExportLog formExportLog = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            FormExportLog[] array = new FormExportLogImpl[3];

            array[0] = getByFormEntryId_PrevAndNext(session, formExportLog,
                    formEntryId, orderByComparator, true);

            array[1] = formExportLog;

            array[2] = getByFormEntryId_PrevAndNext(session, formExportLog,
                    formEntryId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormExportLog getByFormEntryId_PrevAndNext(Session session,
        FormExportLog formExportLog, int formEntryId,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORMEXPORTLOG_WHERE);

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
            Object[] values = orderByComparator.getOrderByValues(formExportLog);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<FormExportLog> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Form Export Logses where exporter = &#63; and formEntryId = &#63;.
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @return the matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByExporterFormEntryId(String exporter,
        int formEntryId) throws SystemException {
        return findByExporterFormEntryId(exporter, formEntryId,
            QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Export Logses where exporter = &#63; and formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @return the range of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByExporterFormEntryId(String exporter,
        int formEntryId, int start, int end) throws SystemException {
        return findByExporterFormEntryId(exporter, formEntryId, start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Export Logses where exporter = &#63; and formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findByExporterFormEntryId(String exporter,
        int formEntryId, int start, int end, OrderByComparator orderByComparator)
        throws SystemException {
        Object[] finderArgs = new Object[] {
                exporter, formEntryId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormExportLog> list = (List<FormExportLog>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_EXPORTERFORMENTRYID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(4 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_FORMEXPORTLOG_WHERE);

            if (exporter == null) {
                query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_1);
            } else {
                if (exporter.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_3);
                } else {
                    query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_2);
                }
            }

            query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_FORMENTRYID_2);

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

                if (exporter != null) {
                    qPos.add(exporter);
                }

                qPos.add(formEntryId);

                list = (List<FormExportLog>) QueryUtil.list(q, getDialect(),
                        start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_EXPORTERFORMENTRYID,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_EXPORTERFORMENTRYID,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form Export Logs in the ordered set where exporter = &#63; and formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a matching Form Export Logs could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByExporterFormEntryId_First(String exporter,
        int formEntryId, OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        List<FormExportLog> list = findByExporterFormEntryId(exporter,
                formEntryId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("exporter=");
            msg.append(exporter);

            msg.append(", formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormExportLogException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form Export Logs in the ordered set where exporter = &#63; and formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a matching Form Export Logs could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog findByExporterFormEntryId_Last(String exporter,
        int formEntryId, OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        int count = countByExporterFormEntryId(exporter, formEntryId);

        List<FormExportLog> list = findByExporterFormEntryId(exporter,
                formEntryId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("exporter=");
            msg.append(exporter);

            msg.append(", formEntryId=");
            msg.append(formEntryId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormExportLogException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Form Export Logses before and after the current Form Export Logs in the ordered set where exporter = &#63; and formEntryId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form Export Logs
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form Export Logs
     * @throws com.erp.lead.NoSuchFormExportLogException if a Form Export Logs with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormExportLog[] findByExporterFormEntryId_PrevAndNext(int id,
        String exporter, int formEntryId, OrderByComparator orderByComparator)
        throws NoSuchFormExportLogException, SystemException {
        FormExportLog formExportLog = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            FormExportLog[] array = new FormExportLogImpl[3];

            array[0] = getByExporterFormEntryId_PrevAndNext(session,
                    formExportLog, exporter, formEntryId, orderByComparator,
                    true);

            array[1] = formExportLog;

            array[2] = getByExporterFormEntryId_PrevAndNext(session,
                    formExportLog, exporter, formEntryId, orderByComparator,
                    false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormExportLog getByExporterFormEntryId_PrevAndNext(
        Session session, FormExportLog formExportLog, String exporter,
        int formEntryId, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORMEXPORTLOG_WHERE);

        if (exporter == null) {
            query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_1);
        } else {
            if (exporter.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_3);
            } else {
                query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_2);
            }
        }

        query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_FORMENTRYID_2);

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

        if (exporter != null) {
            qPos.add(exporter);
        }

        qPos.add(formEntryId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(formExportLog);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<FormExportLog> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Form Export Logses.
     *
     * @return the Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Export Logses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @return the range of Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Export Logses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Export Logses to return
     * @param end the upper bound of the range of Form Export Logses to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public List<FormExportLog> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormExportLog> list = (List<FormExportLog>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_FORMEXPORTLOG);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_FORMEXPORTLOG;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<FormExportLog>) QueryUtil.list(q,
                            getDialect(), start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<FormExportLog>) QueryUtil.list(q,
                            getDialect(), start, end);
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
     * Removes all the Form Export Logses where formEntryId = &#63; from the database.
     *
     * @param formEntryId the form entry id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByFormEntryId(int formEntryId) throws SystemException {
        for (FormExportLog formExportLog : findByFormEntryId(formEntryId)) {
            remove(formExportLog);
        }
    }

    /**
     * Removes all the Form Export Logses where exporter = &#63; and formEntryId = &#63; from the database.
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByExporterFormEntryId(String exporter, int formEntryId)
        throws SystemException {
        for (FormExportLog formExportLog : findByExporterFormEntryId(exporter,
                formEntryId)) {
            remove(formExportLog);
        }
    }

    /**
     * Removes all the Form Export Logses from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (FormExportLog formExportLog : findAll()) {
            remove(formExportLog);
        }
    }

    /**
     * Counts all the Form Export Logses where formEntryId = &#63;.
     *
     * @param formEntryId the form entry id to search with
     * @return the number of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public int countByFormEntryId(int formEntryId) throws SystemException {
        Object[] finderArgs = new Object[] { formEntryId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMENTRYID,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORMEXPORTLOG_WHERE);

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
     * Counts all the Form Export Logses where exporter = &#63; and formEntryId = &#63;.
     *
     * @param exporter the exporter to search with
     * @param formEntryId the form entry id to search with
     * @return the number of matching Form Export Logses
     * @throws SystemException if a system exception occurred
     */
    public int countByExporterFormEntryId(String exporter, int formEntryId)
        throws SystemException {
        Object[] finderArgs = new Object[] { exporter, formEntryId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_EXPORTERFORMENTRYID,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(3);

            query.append(_SQL_COUNT_FORMEXPORTLOG_WHERE);

            if (exporter == null) {
                query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_1);
            } else {
                if (exporter.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_3);
                } else {
                    query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_EXPORTER_2);
                }
            }

            query.append(_FINDER_COLUMN_EXPORTERFORMENTRYID_FORMENTRYID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (exporter != null) {
                    qPos.add(exporter);
                }

                qPos.add(formEntryId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_EXPORTERFORMENTRYID,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Form Export Logses.
     *
     * @return the number of Form Export Logses
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

                Query q = session.createQuery(_SQL_COUNT_FORMEXPORTLOG);

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
     * Initializes the Form Export Logs persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.FormExportLog")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<FormExportLog>> listenersList = new ArrayList<ModelListener<FormExportLog>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<FormExportLog>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(FormExportLogImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
