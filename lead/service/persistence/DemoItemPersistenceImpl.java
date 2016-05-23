package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchDemoItemException;
import com.erp.lead.model.DemoItem;
import com.erp.lead.model.impl.DemoItemImpl;
import com.erp.lead.model.impl.DemoItemModelImpl;
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
 * The persistence implementation for the DemoItem service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link DemoItemUtil} to access the DemoItem persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see DemoItemPersistence
 * @see DemoItemUtil
 * @generated
 */
public class DemoItemPersistenceImpl extends BasePersistenceImpl<DemoItem>
    implements DemoItemPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = DemoItemImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_ALLIDS = new FinderPath(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByAllIds",
            new String[] {
                Integer.class.getName(), Integer.class.getName(),
                Integer.class.getName(), Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_ALLIDS = new FinderPath(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByAllIds",
            new String[] {
                Integer.class.getName(), Integer.class.getName(),
                Integer.class.getName(), Integer.class.getName()
            });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_DEMOITEM = "SELECT demoItem FROM DemoItem demoItem";
    private static final String _SQL_SELECT_DEMOITEM_WHERE = "SELECT demoItem FROM DemoItem demoItem WHERE ";
    private static final String _SQL_COUNT_DEMOITEM = "SELECT COUNT(demoItem) FROM DemoItem demoItem";
    private static final String _SQL_COUNT_DEMOITEM_WHERE = "SELECT COUNT(demoItem) FROM DemoItem demoItem WHERE ";
    private static final String _FINDER_COLUMN_ALLIDS_ORGANIZATIONID_2 = "demoItem.organizationId = ? AND ";
    private static final String _FINDER_COLUMN_ALLIDS_VERTICALID_2 = "demoItem.verticalId = ? AND ";
    private static final String _FINDER_COLUMN_ALLIDS_LOCALIZATIONID_2 = "demoItem.localizationId = ? AND ";
    private static final String _FINDER_COLUMN_ALLIDS_LANGUAGEID_2 = "demoItem.languageId = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "demoItem.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No DemoItem exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No DemoItem exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(DemoItemPersistenceImpl.class);
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
     * Caches the DemoItem in the entity cache if it is enabled.
     *
     * @param demoItem the DemoItem to cache
     */
    public void cacheResult(DemoItem demoItem) {
        EntityCacheUtil.putResult(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemImpl.class, demoItem.getPrimaryKey(), demoItem);
    }

    /**
     * Caches the DemoItems in the entity cache if it is enabled.
     *
     * @param demoItems the DemoItems to cache
     */
    public void cacheResult(List<DemoItem> demoItems) {
        for (DemoItem demoItem : demoItems) {
            if (EntityCacheUtil.getResult(
                        DemoItemModelImpl.ENTITY_CACHE_ENABLED,
                        DemoItemImpl.class, demoItem.getPrimaryKey(), this) == null) {
                cacheResult(demoItem);
            }
        }
    }

    /**
     * Clears the cache for all DemoItems.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(DemoItemImpl.class.getName());
        EntityCacheUtil.clearCache(DemoItemImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the DemoItem.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(DemoItem demoItem) {
        EntityCacheUtil.removeResult(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemImpl.class, demoItem.getPrimaryKey());
    }

    /**
     * Creates a new DemoItem with the primary key. Does not add the DemoItem to the database.
     *
     * @param id the primary key for the new DemoItem
     * @return the new DemoItem
     */
    public DemoItem create(int id) {
        DemoItem demoItem = new DemoItemImpl();

        demoItem.setNew(true);
        demoItem.setPrimaryKey(id);

        return demoItem;
    }

    /**
     * Removes the DemoItem with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the DemoItem to remove
     * @return the DemoItem that was removed
     * @throws com.liferay.portal.NoSuchModelException if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the DemoItem with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the DemoItem to remove
     * @return the DemoItem that was removed
     * @throws com.erp.lead.NoSuchDemoItemException if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem remove(int id)
        throws NoSuchDemoItemException, SystemException {
        Session session = null;

        try {
            session = openSession();

            DemoItem demoItem = (DemoItem) session.get(DemoItemImpl.class,
                    new Integer(id));

            if (demoItem == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchDemoItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(demoItem);
        } catch (NoSuchDemoItemException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected DemoItem removeImpl(DemoItem demoItem) throws SystemException {
        demoItem = toUnwrappedModel(demoItem);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, demoItem);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemImpl.class, demoItem.getPrimaryKey());

        return demoItem;
    }

    public DemoItem updateImpl(com.erp.lead.model.DemoItem demoItem,
        boolean merge) throws SystemException {
        demoItem = toUnwrappedModel(demoItem);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, demoItem, merge);

            demoItem.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
            DemoItemImpl.class, demoItem.getPrimaryKey(), demoItem);

        return demoItem;
    }

    protected DemoItem toUnwrappedModel(DemoItem demoItem) {
        if (demoItem instanceof DemoItemImpl) {
            return demoItem;
        }

        DemoItemImpl demoItemImpl = new DemoItemImpl();

        demoItemImpl.setNew(demoItem.isNew());
        demoItemImpl.setPrimaryKey(demoItem.getPrimaryKey());

        demoItemImpl.setId(demoItem.getId());
        demoItemImpl.setLanguageId(demoItem.getLanguageId());
        demoItemImpl.setInventoryItemId(demoItem.getInventoryItemId());
        demoItemImpl.setOrganizationId(demoItem.getOrganizationId());
        demoItemImpl.setVerticalId(demoItem.getVerticalId());
        demoItemImpl.setLocalizationId(demoItem.getLocalizationId());

        return demoItemImpl;
    }

    /**
     * Finds the DemoItem with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the DemoItem to find
     * @return the DemoItem
     * @throws com.liferay.portal.NoSuchModelException if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the DemoItem with the primary key or throws a {@link com.erp.lead.NoSuchDemoItemException} if it could not be found.
     *
     * @param id the primary key of the DemoItem to find
     * @return the DemoItem
     * @throws com.erp.lead.NoSuchDemoItemException if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem findByPrimaryKey(int id)
        throws NoSuchDemoItemException, SystemException {
        DemoItem demoItem = fetchByPrimaryKey(id);

        if (demoItem == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchDemoItemException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return demoItem;
    }

    /**
     * Finds the DemoItem with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the DemoItem to find
     * @return the DemoItem, or <code>null</code> if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the DemoItem with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the DemoItem to find
     * @return the DemoItem, or <code>null</code> if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem fetchByPrimaryKey(int id) throws SystemException {
        DemoItem demoItem = (DemoItem) EntityCacheUtil.getResult(DemoItemModelImpl.ENTITY_CACHE_ENABLED,
                DemoItemImpl.class, id, this);

        if (demoItem == null) {
            Session session = null;

            try {
                session = openSession();

                demoItem = (DemoItem) session.get(DemoItemImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (demoItem != null) {
                    cacheResult(demoItem);
                }

                closeSession(session);
            }
        }

        return demoItem;
    }

    /**
     * Finds all the DemoItems where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @return the matching DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findByAllIds(int organizationId, int verticalId,
        int localizationId, int languageId) throws SystemException {
        return findByAllIds(organizationId, verticalId, localizationId,
            languageId, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the DemoItems where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @param start the lower bound of the range of DemoItems to return
     * @param end the upper bound of the range of DemoItems to return (not inclusive)
     * @return the range of matching DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findByAllIds(int organizationId, int verticalId,
        int localizationId, int languageId, int start, int end)
        throws SystemException {
        return findByAllIds(organizationId, verticalId, localizationId,
            languageId, start, end, null);
    }

    /**
     * Finds an ordered range of all the DemoItems where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @param start the lower bound of the range of DemoItems to return
     * @param end the upper bound of the range of DemoItems to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findByAllIds(int organizationId, int verticalId,
        int localizationId, int languageId, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                organizationId, verticalId, localizationId, languageId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<DemoItem> list = (List<DemoItem>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ALLIDS,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(6 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(5);
            }

            query.append(_SQL_SELECT_DEMOITEM_WHERE);

            query.append(_FINDER_COLUMN_ALLIDS_ORGANIZATIONID_2);

            query.append(_FINDER_COLUMN_ALLIDS_VERTICALID_2);

            query.append(_FINDER_COLUMN_ALLIDS_LOCALIZATIONID_2);

            query.append(_FINDER_COLUMN_ALLIDS_LANGUAGEID_2);

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

                qPos.add(organizationId);

                qPos.add(verticalId);

                qPos.add(localizationId);

                qPos.add(languageId);

                list = (List<DemoItem>) QueryUtil.list(q, getDialect(), start,
                        end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ALLIDS,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ALLIDS,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first DemoItem in the ordered set where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching DemoItem
     * @throws com.erp.lead.NoSuchDemoItemException if a matching DemoItem could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem findByAllIds_First(int organizationId, int verticalId,
        int localizationId, int languageId, OrderByComparator orderByComparator)
        throws NoSuchDemoItemException, SystemException {
        List<DemoItem> list = findByAllIds(organizationId, verticalId,
                localizationId, languageId, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(10);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("organizationId=");
            msg.append(organizationId);

            msg.append(", verticalId=");
            msg.append(verticalId);

            msg.append(", localizationId=");
            msg.append(localizationId);

            msg.append(", languageId=");
            msg.append(languageId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchDemoItemException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last DemoItem in the ordered set where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching DemoItem
     * @throws com.erp.lead.NoSuchDemoItemException if a matching DemoItem could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem findByAllIds_Last(int organizationId, int verticalId,
        int localizationId, int languageId, OrderByComparator orderByComparator)
        throws NoSuchDemoItemException, SystemException {
        int count = countByAllIds(organizationId, verticalId, localizationId,
                languageId);

        List<DemoItem> list = findByAllIds(organizationId, verticalId,
                localizationId, languageId, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(10);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("organizationId=");
            msg.append(organizationId);

            msg.append(", verticalId=");
            msg.append(verticalId);

            msg.append(", localizationId=");
            msg.append(localizationId);

            msg.append(", languageId=");
            msg.append(languageId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchDemoItemException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the DemoItems before and after the current DemoItem in the ordered set where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current DemoItem
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next DemoItem
     * @throws com.erp.lead.NoSuchDemoItemException if a DemoItem with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public DemoItem[] findByAllIds_PrevAndNext(int id, int organizationId,
        int verticalId, int localizationId, int languageId,
        OrderByComparator orderByComparator)
        throws NoSuchDemoItemException, SystemException {
        DemoItem demoItem = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            DemoItem[] array = new DemoItemImpl[3];

            array[0] = getByAllIds_PrevAndNext(session, demoItem,
                    organizationId, verticalId, localizationId, languageId,
                    orderByComparator, true);

            array[1] = demoItem;

            array[2] = getByAllIds_PrevAndNext(session, demoItem,
                    organizationId, verticalId, localizationId, languageId,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected DemoItem getByAllIds_PrevAndNext(Session session,
        DemoItem demoItem, int organizationId, int verticalId,
        int localizationId, int languageId,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_DEMOITEM_WHERE);

        query.append(_FINDER_COLUMN_ALLIDS_ORGANIZATIONID_2);

        query.append(_FINDER_COLUMN_ALLIDS_VERTICALID_2);

        query.append(_FINDER_COLUMN_ALLIDS_LOCALIZATIONID_2);

        query.append(_FINDER_COLUMN_ALLIDS_LANGUAGEID_2);

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

        qPos.add(organizationId);

        qPos.add(verticalId);

        qPos.add(localizationId);

        qPos.add(languageId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(demoItem);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<DemoItem> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the DemoItems.
     *
     * @return the DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the DemoItems.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of DemoItems to return
     * @param end the upper bound of the range of DemoItems to return (not inclusive)
     * @return the range of DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the DemoItems.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of DemoItems to return
     * @param end the upper bound of the range of DemoItems to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of DemoItems
     * @throws SystemException if a system exception occurred
     */
    public List<DemoItem> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<DemoItem> list = (List<DemoItem>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_DEMOITEM);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_DEMOITEM;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<DemoItem>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<DemoItem>) QueryUtil.list(q, getDialect(),
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
     * Removes all the DemoItems where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63; from the database.
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByAllIds(int organizationId, int verticalId,
        int localizationId, int languageId) throws SystemException {
        for (DemoItem demoItem : findByAllIds(organizationId, verticalId,
                localizationId, languageId)) {
            remove(demoItem);
        }
    }

    /**
     * Removes all the DemoItems from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (DemoItem demoItem : findAll()) {
            remove(demoItem);
        }
    }

    /**
     * Counts all the DemoItems where organizationId = &#63; and verticalId = &#63; and localizationId = &#63; and languageId = &#63;.
     *
     * @param organizationId the organization id to search with
     * @param verticalId the vertical id to search with
     * @param localizationId the localization id to search with
     * @param languageId the language id to search with
     * @return the number of matching DemoItems
     * @throws SystemException if a system exception occurred
     */
    public int countByAllIds(int organizationId, int verticalId,
        int localizationId, int languageId) throws SystemException {
        Object[] finderArgs = new Object[] {
                organizationId, verticalId, localizationId, languageId
            };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ALLIDS,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(5);

            query.append(_SQL_COUNT_DEMOITEM_WHERE);

            query.append(_FINDER_COLUMN_ALLIDS_ORGANIZATIONID_2);

            query.append(_FINDER_COLUMN_ALLIDS_VERTICALID_2);

            query.append(_FINDER_COLUMN_ALLIDS_LOCALIZATIONID_2);

            query.append(_FINDER_COLUMN_ALLIDS_LANGUAGEID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(organizationId);

                qPos.add(verticalId);

                qPos.add(localizationId);

                qPos.add(languageId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ALLIDS,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the DemoItems.
     *
     * @return the number of DemoItems
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

                Query q = session.createQuery(_SQL_COUNT_DEMOITEM);

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
     * Initializes the DemoItem persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.DemoItem")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<DemoItem>> listenersList = new ArrayList<ModelListener<DemoItem>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<DemoItem>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(DemoItemImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
