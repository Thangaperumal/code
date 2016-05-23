package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchPromoException;
import com.erp.lead.model.Promo;
import com.erp.lead.model.impl.PromoImpl;
import com.erp.lead.model.impl.PromoModelImpl;
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
 * The persistence implementation for the Promo service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link PromoUtil} to access the Promo persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see PromoPersistence
 * @see PromoUtil
 * @generated
 */
public class PromoPersistenceImpl extends BasePersistenceImpl<Promo>
    implements PromoPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = PromoImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_CODE = new FinderPath(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCode",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_CODE = new FinderPath(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByCode", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_PROMO = "SELECT promo FROM Promo promo";
    private static final String _SQL_SELECT_PROMO_WHERE = "SELECT promo FROM Promo promo WHERE ";
    private static final String _SQL_COUNT_PROMO = "SELECT COUNT(promo) FROM Promo promo";
    private static final String _SQL_COUNT_PROMO_WHERE = "SELECT COUNT(promo) FROM Promo promo WHERE ";
    private static final String _FINDER_COLUMN_CODE_CODE_1 = "promo.code IS NULL";
    private static final String _FINDER_COLUMN_CODE_CODE_2 = "promo.code = ?";
    private static final String _FINDER_COLUMN_CODE_CODE_3 = "(promo.code IS NULL OR promo.code = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "promo.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Promo exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Promo exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(PromoPersistenceImpl.class);
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
     * Caches the Promo in the entity cache if it is enabled.
     *
     * @param promo the Promo to cache
     */
    public void cacheResult(Promo promo) {
        EntityCacheUtil.putResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoImpl.class, promo.getPrimaryKey(), promo);
    }

    /**
     * Caches the Promos in the entity cache if it is enabled.
     *
     * @param promos the Promos to cache
     */
    public void cacheResult(List<Promo> promos) {
        for (Promo promo : promos) {
            if (EntityCacheUtil.getResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
                        PromoImpl.class, promo.getPrimaryKey(), this) == null) {
                cacheResult(promo);
            }
        }
    }

    /**
     * Clears the cache for all Promos.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(PromoImpl.class.getName());
        EntityCacheUtil.clearCache(PromoImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Promo.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Promo promo) {
        EntityCacheUtil.removeResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoImpl.class, promo.getPrimaryKey());
    }

    /**
     * Creates a new Promo with the primary key. Does not add the Promo to the database.
     *
     * @param id the primary key for the new Promo
     * @return the new Promo
     */
    public Promo create(int id) {
        Promo promo = new PromoImpl();

        promo.setNew(true);
        promo.setPrimaryKey(id);

        return promo;
    }

    /**
     * Removes the Promo with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Promo to remove
     * @return the Promo that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Promo with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Promo to remove
     * @return the Promo that was removed
     * @throws com.erp.lead.NoSuchPromoException if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo remove(int id) throws NoSuchPromoException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Promo promo = (Promo) session.get(PromoImpl.class, new Integer(id));

            if (promo == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchPromoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(promo);
        } catch (NoSuchPromoException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Promo removeImpl(Promo promo) throws SystemException {
        promo = toUnwrappedModel(promo);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, promo);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoImpl.class, promo.getPrimaryKey());

        return promo;
    }

    public Promo updateImpl(com.erp.lead.model.Promo promo,
        boolean merge) throws SystemException {
        promo = toUnwrappedModel(promo);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, promo, merge);

            promo.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
            PromoImpl.class, promo.getPrimaryKey(), promo);

        return promo;
    }

    protected Promo toUnwrappedModel(Promo promo) {
        if (promo instanceof PromoImpl) {
            return promo;
        }

        PromoImpl promoImpl = new PromoImpl();

        promoImpl.setNew(promo.isNew());
        promoImpl.setPrimaryKey(promo.getPrimaryKey());

        promoImpl.setId(promo.getId());
        promoImpl.setCode(promo.getCode());
        promoImpl.setDescriptionEn(promo.getDescriptionEn());
        promoImpl.setCurrencyId(promo.getCurrencyId());
        promoImpl.setActive(promo.getActive());
        promoImpl.setAutomatic(promo.getAutomatic());
        promoImpl.setAllowInHSStore(promo.getAllowInHSStore());
        promoImpl.setAllowInINDStore(promo.getAllowInINDStore());
        promoImpl.setGenericLead(promo.getGenericLead());
        promoImpl.setSpecificLead(promo.getSpecificLead());
        promoImpl.setEndDate(promo.getEndDate());
        promoImpl.setStartDate(promo.getStartDate());

        return promoImpl;
    }

    /**
     * Finds the Promo with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Promo to find
     * @return the Promo
     * @throws com.liferay.portal.NoSuchModelException if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Promo with the primary key or throws a {@link com.erp.lead.NoSuchPromoException} if it could not be found.
     *
     * @param id the primary key of the Promo to find
     * @return the Promo
     * @throws com.erp.lead.NoSuchPromoException if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo findByPrimaryKey(int id)
        throws NoSuchPromoException, SystemException {
        Promo promo = fetchByPrimaryKey(id);

        if (promo == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchPromoException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return promo;
    }

    /**
     * Finds the Promo with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Promo to find
     * @return the Promo, or <code>null</code> if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Promo with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Promo to find
     * @return the Promo, or <code>null</code> if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo fetchByPrimaryKey(int id) throws SystemException {
        Promo promo = (Promo) EntityCacheUtil.getResult(PromoModelImpl.ENTITY_CACHE_ENABLED,
                PromoImpl.class, id, this);

        if (promo == null) {
            Session session = null;

            try {
                session = openSession();

                promo = (Promo) session.get(PromoImpl.class, new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (promo != null) {
                    cacheResult(promo);
                }

                closeSession(session);
            }
        }

        return promo;
    }

    /**
     * Finds all the Promos where code = &#63;.
     *
     * @param code the code to search with
     * @return the matching Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findByCode(String code) throws SystemException {
        return findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Promos where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Promos to return
     * @param end the upper bound of the range of Promos to return (not inclusive)
     * @return the range of matching Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findByCode(String code, int start, int end)
        throws SystemException {
        return findByCode(code, start, end, null);
    }

    /**
     * Finds an ordered range of all the Promos where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Promos to return
     * @param end the upper bound of the range of Promos to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findByCode(String code, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                code,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Promo> list = (List<Promo>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODE,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_PROMO_WHERE);

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

                list = (List<Promo>) QueryUtil.list(q, getDialect(), start, end);
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
     * Finds the first Promo in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Promo
     * @throws com.erp.lead.NoSuchPromoException if a matching Promo could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo findByCode_First(String code,
        OrderByComparator orderByComparator)
        throws NoSuchPromoException, SystemException {
        List<Promo> list = findByCode(code, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchPromoException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Promo in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Promo
     * @throws com.erp.lead.NoSuchPromoException if a matching Promo could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo findByCode_Last(String code,
        OrderByComparator orderByComparator)
        throws NoSuchPromoException, SystemException {
        int count = countByCode(code);

        List<Promo> list = findByCode(code, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchPromoException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Promos before and after the current Promo in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Promo
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Promo
     * @throws com.erp.lead.NoSuchPromoException if a Promo with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Promo[] findByCode_PrevAndNext(int id, String code,
        OrderByComparator orderByComparator)
        throws NoSuchPromoException, SystemException {
        Promo promo = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Promo[] array = new PromoImpl[3];

            array[0] = getByCode_PrevAndNext(session, promo, code,
                    orderByComparator, true);

            array[1] = promo;

            array[2] = getByCode_PrevAndNext(session, promo, code,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Promo getByCode_PrevAndNext(Session session, Promo promo,
        String code, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_PROMO_WHERE);

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
            Object[] values = orderByComparator.getOrderByValues(promo);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Promo> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Promos.
     *
     * @return the Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Promos.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Promos to return
     * @param end the upper bound of the range of Promos to return (not inclusive)
     * @return the range of Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Promos.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Promos to return
     * @param end the upper bound of the range of Promos to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Promos
     * @throws SystemException if a system exception occurred
     */
    public List<Promo> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Promo> list = (List<Promo>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_PROMO);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_PROMO;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Promo>) QueryUtil.list(q, getDialect(), start,
                            end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Promo>) QueryUtil.list(q, getDialect(), start,
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
     * Removes all the Promos where code = &#63; from the database.
     *
     * @param code the code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByCode(String code) throws SystemException {
        for (Promo promo : findByCode(code)) {
            remove(promo);
        }
    }

    /**
     * Removes all the Promos from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Promo promo : findAll()) {
            remove(promo);
        }
    }

    /**
     * Counts all the Promos where code = &#63;.
     *
     * @param code the code to search with
     * @return the number of matching Promos
     * @throws SystemException if a system exception occurred
     */
    public int countByCode(String code) throws SystemException {
        Object[] finderArgs = new Object[] { code };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODE,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_PROMO_WHERE);

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
     * Counts all the Promos.
     *
     * @return the number of Promos
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

                Query q = session.createQuery(_SQL_COUNT_PROMO);

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
     * Initializes the Promo persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.Promo")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Promo>> listenersList = new ArrayList<ModelListener<Promo>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Promo>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(PromoImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
