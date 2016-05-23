package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchSiteException;
import com.erp.lead.model.Site;
import com.erp.lead.model.impl.SiteImpl;
import com.erp.lead.model.impl.SiteModelImpl;
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
 * The persistence implementation for the Site service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link SiteUtil} to access the Site persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see SitePersistence
 * @see SiteUtil
 * @generated
 */
public class SitePersistenceImpl extends BasePersistenceImpl<Site>
    implements SitePersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = SiteImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_CODE = new FinderPath(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCode",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_CODE = new FinderPath(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByCode", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_SITE = "SELECT site FROM Site site";
    private static final String _SQL_SELECT_SITE_WHERE = "SELECT site FROM Site site WHERE ";
    private static final String _SQL_COUNT_SITE = "SELECT COUNT(site) FROM Site site";
    private static final String _SQL_COUNT_SITE_WHERE = "SELECT COUNT(site) FROM Site site WHERE ";
    private static final String _FINDER_COLUMN_CODE_CODE_1 = "site.code IS NULL";
    private static final String _FINDER_COLUMN_CODE_CODE_2 = "site.code = ?";
    private static final String _FINDER_COLUMN_CODE_CODE_3 = "(site.code IS NULL OR site.code = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "site.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Site exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Site exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(SitePersistenceImpl.class);
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
     * Caches the Site in the entity cache if it is enabled.
     *
     * @param site the Site to cache
     */
    public void cacheResult(Site site) {
        EntityCacheUtil.putResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteImpl.class, site.getPrimaryKey(), site);
    }

    /**
     * Caches the Sites in the entity cache if it is enabled.
     *
     * @param sites the Sites to cache
     */
    public void cacheResult(List<Site> sites) {
        for (Site site : sites) {
            if (EntityCacheUtil.getResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
                        SiteImpl.class, site.getPrimaryKey(), this) == null) {
                cacheResult(site);
            }
        }
    }

    /**
     * Clears the cache for all Sites.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(SiteImpl.class.getName());
        EntityCacheUtil.clearCache(SiteImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Site.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Site site) {
        EntityCacheUtil.removeResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteImpl.class, site.getPrimaryKey());
    }

    /**
     * Creates a new Site with the primary key. Does not add the Site to the database.
     *
     * @param id the primary key for the new Site
     * @return the new Site
     */
    public Site create(int id) {
        Site site = new SiteImpl();

        site.setNew(true);
        site.setPrimaryKey(id);

        return site;
    }

    /**
     * Removes the Site with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Site to remove
     * @return the Site that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Site with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Site to remove
     * @return the Site that was removed
     * @throws com.erp.lead.NoSuchSiteException if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site remove(int id) throws NoSuchSiteException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Site site = (Site) session.get(SiteImpl.class, new Integer(id));

            if (site == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchSiteException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(site);
        } catch (NoSuchSiteException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Site removeImpl(Site site) throws SystemException {
        site = toUnwrappedModel(site);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, site);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteImpl.class, site.getPrimaryKey());

        return site;
    }

    public Site updateImpl(com.rosettastone.cis.model.Site site, boolean merge)
        throws SystemException {
        site = toUnwrappedModel(site);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, site, merge);

            site.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
            SiteImpl.class, site.getPrimaryKey(), site);

        return site;
    }

    protected Site toUnwrappedModel(Site site) {
        if (site instanceof SiteImpl) {
            return site;
        }

        SiteImpl siteImpl = new SiteImpl();

        siteImpl.setNew(site.isNew());
        siteImpl.setPrimaryKey(site.getPrimaryKey());

        siteImpl.setId(site.getId());
        siteImpl.setCode(site.getCode());
        siteImpl.setDescription(site.getDescription());
        siteImpl.setCurrencyId(site.getCurrencyId());
        siteImpl.setOrganizationId(site.getOrganizationId());
        siteImpl.setCountryId(site.getCountryId());
        siteImpl.setUrl(site.getUrl());
        siteImpl.setCallCenter(site.getCallCenter());
        siteImpl.setLanguageIso2(site.getLanguageIso2());
        siteImpl.setLocalizationId(site.getLocalizationId());

        return siteImpl;
    }

    /**
     * Finds the Site with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Site to find
     * @return the Site
     * @throws com.liferay.portal.NoSuchModelException if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Site with the primary key or throws a {@link com.erp.lead.NoSuchSiteException} if it could not be found.
     *
     * @param id the primary key of the Site to find
     * @return the Site
     * @throws com.erp.lead.NoSuchSiteException if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site findByPrimaryKey(int id)
        throws NoSuchSiteException, SystemException {
        Site site = fetchByPrimaryKey(id);

        if (site == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchSiteException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return site;
    }

    /**
     * Finds the Site with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Site to find
     * @return the Site, or <code>null</code> if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Site with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Site to find
     * @return the Site, or <code>null</code> if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site fetchByPrimaryKey(int id) throws SystemException {
        Site site = (Site) EntityCacheUtil.getResult(SiteModelImpl.ENTITY_CACHE_ENABLED,
                SiteImpl.class, id, this);

        if (site == null) {
            Session session = null;

            try {
                session = openSession();

                site = (Site) session.get(SiteImpl.class, new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (site != null) {
                    cacheResult(site);
                }

                closeSession(session);
            }
        }

        return site;
    }

    /**
     * Finds all the Sites where code = &#63;.
     *
     * @param code the code to search with
     * @return the matching Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findByCode(String code) throws SystemException {
        return findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Sites where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Sites to return
     * @param end the upper bound of the range of Sites to return (not inclusive)
     * @return the range of matching Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findByCode(String code, int start, int end)
        throws SystemException {
        return findByCode(code, start, end, null);
    }

    /**
     * Finds an ordered range of all the Sites where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Sites to return
     * @param end the upper bound of the range of Sites to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findByCode(String code, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                code,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Site> list = (List<Site>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODE,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_SITE_WHERE);

            if (code == null) {
                query.append(_FINDER_COLUMN_CODE_CODE_1);
            } else {
                if (code.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_CODE_CODE_3);
                } else {
                    query.append(_FINDER_COLUMN_CODE_CODE_2);
                }
            }

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

                if (code != null) {
                    qPos.add(code);
                }

                list = (List<Site>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_CODE,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_CODE,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Site in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Site
     * @throws com.erp.lead.NoSuchSiteException if a matching Site could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site findByCode_First(String code,
        OrderByComparator orderByComparator)
        throws NoSuchSiteException, SystemException {
        List<Site> list = findByCode(code, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchSiteException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Site in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Site
     * @throws com.erp.lead.NoSuchSiteException if a matching Site could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site findByCode_Last(String code, OrderByComparator orderByComparator)
        throws NoSuchSiteException, SystemException {
        int count = countByCode(code);

        List<Site> list = findByCode(code, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchSiteException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Sites before and after the current Site in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Site
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Site
     * @throws com.erp.lead.NoSuchSiteException if a Site with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Site[] findByCode_PrevAndNext(int id, String code,
        OrderByComparator orderByComparator)
        throws NoSuchSiteException, SystemException {
        Site site = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Site[] array = new SiteImpl[3];

            array[0] = getByCode_PrevAndNext(session, site, code,
                    orderByComparator, true);

            array[1] = site;

            array[2] = getByCode_PrevAndNext(session, site, code,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Site getByCode_PrevAndNext(Session session, Site site,
        String code, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_SITE_WHERE);

        if (code == null) {
            query.append(_FINDER_COLUMN_CODE_CODE_1);
        } else {
            if (code.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_CODE_CODE_3);
            } else {
                query.append(_FINDER_COLUMN_CODE_CODE_2);
            }
        }

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

        if (code != null) {
            qPos.add(code);
        }

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(site);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Site> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Sites.
     *
     * @return the Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Sites.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Sites to return
     * @param end the upper bound of the range of Sites to return (not inclusive)
     * @return the range of Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Sites.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Sites to return
     * @param end the upper bound of the range of Sites to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Sites
     * @throws SystemException if a system exception occurred
     */
    public List<Site> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Site> list = (List<Site>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_SITE);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_SITE;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Site>) QueryUtil.list(q, getDialect(), start,
                            end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Site>) QueryUtil.list(q, getDialect(), start,
                            end);
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
     * Removes all the Sites where code = &#63; from the database.
     *
     * @param code the code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByCode(String code) throws SystemException {
        for (Site site : findByCode(code)) {
            remove(site);
        }
    }

    /**
     * Removes all the Sites from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Site site : findAll()) {
            remove(site);
        }
    }

    /**
     * Counts all the Sites where code = &#63;.
     *
     * @param code the code to search with
     * @return the number of matching Sites
     * @throws SystemException if a system exception occurred
     */
    public int countByCode(String code) throws SystemException {
        Object[] finderArgs = new Object[] { code };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODE,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_SITE_WHERE);

            if (code == null) {
                query.append(_FINDER_COLUMN_CODE_CODE_1);
            } else {
                if (code.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_CODE_CODE_3);
                } else {
                    query.append(_FINDER_COLUMN_CODE_CODE_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (code != null) {
                    qPos.add(code);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_CODE,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Sites.
     *
     * @return the number of Sites
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

                Query q = session.createQuery(_SQL_COUNT_SITE);

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
     * Initializes the Site persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.Site")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Site>> listenersList = new ArrayList<ModelListener<Site>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Site>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(SiteImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
