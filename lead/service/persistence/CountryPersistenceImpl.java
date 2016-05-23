package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchCountryException;
import com.erp.lead.model.Country;
import com.erp.lead.model.impl.CountryImpl;
import com.erp.lead.model.impl.CountryModelImpl;
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
 * The persistence implementation for the Country service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link CountryUtil} to access the Country persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see CountryPersistence
 * @see CountryUtil
 * @generated
 */
public class CountryPersistenceImpl extends BasePersistenceImpl<Country>
    implements CountryPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = CountryImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_CODE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCode",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_CODE = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByCode", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_ISO = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByISO",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_ISO = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByISO", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_COUNTRY = "SELECT country FROM Country country";
    private static final String _SQL_SELECT_COUNTRY_WHERE = "SELECT country FROM Country country WHERE ";
    private static final String _SQL_COUNT_COUNTRY = "SELECT COUNT(country) FROM Country country";
    private static final String _SQL_COUNT_COUNTRY_WHERE = "SELECT COUNT(country) FROM Country country WHERE ";
    private static final String _FINDER_COLUMN_CODE_CODE_1 = "country.code IS NULL";
    private static final String _FINDER_COLUMN_CODE_CODE_2 = "country.code = ?";
    private static final String _FINDER_COLUMN_CODE_CODE_3 = "(country.code IS NULL OR country.code = ?)";
    private static final String _FINDER_COLUMN_ISO_ISONUMERICCODE_1 = "country.isoNumericCode IS NULL";
    private static final String _FINDER_COLUMN_ISO_ISONUMERICCODE_2 = "country.isoNumericCode = ?";
    private static final String _FINDER_COLUMN_ISO_ISONUMERICCODE_3 = "(country.isoNumericCode IS NULL OR country.isoNumericCode = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "country.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Country exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Country exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(CountryPersistenceImpl.class);
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
     * Caches the Country in the entity cache if it is enabled.
     *
     * @param country the Country to cache
     */
    public void cacheResult(Country country) {
        EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryImpl.class, country.getPrimaryKey(), country);
    }

    /**
     * Caches the Countries in the entity cache if it is enabled.
     *
     * @param countries the Countries to cache
     */
    public void cacheResult(List<Country> countries) {
        for (Country country : countries) {
            if (EntityCacheUtil.getResult(
                        CountryModelImpl.ENTITY_CACHE_ENABLED,
                        CountryImpl.class, country.getPrimaryKey(), this) == null) {
                cacheResult(country);
            }
        }
    }

    /**
     * Clears the cache for all Countries.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(CountryImpl.class.getName());
        EntityCacheUtil.clearCache(CountryImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Country.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Country country) {
        EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryImpl.class, country.getPrimaryKey());
    }

    /**
     * Creates a new Country with the primary key. Does not add the Country to the database.
     *
     * @param id the primary key for the new Country
     * @return the new Country
     */
    public Country create(String id) {
        Country country = new CountryImpl();

        country.setNew(true);
        country.setPrimaryKey(id);

        return country;
    }

    /**
     * Removes the Country with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Country to remove
     * @return the Country that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove((String) primaryKey);
    }

    /**
     * Removes the Country with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Country to remove
     * @return the Country that was removed
     * @throws com.erp.lead.NoSuchCountryException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country remove(String id)
        throws NoSuchCountryException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Country country = (Country) session.get(CountryImpl.class, id);

            if (country == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(country);
        } catch (NoSuchCountryException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Country removeImpl(Country country) throws SystemException {
        country = toUnwrappedModel(country);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, country);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryImpl.class, country.getPrimaryKey());

        return country;
    }

    public Country updateImpl(com.rosettastone.cis.model.Country country,
        boolean merge) throws SystemException {
        country = toUnwrappedModel(country);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, country, merge);

            country.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
            CountryImpl.class, country.getPrimaryKey(), country);

        return country;
    }

    protected Country toUnwrappedModel(Country country) {
        if (country instanceof CountryImpl) {
            return country;
        }

        CountryImpl countryImpl = new CountryImpl();

        countryImpl.setNew(country.isNew());
        countryImpl.setPrimaryKey(country.getPrimaryKey());

        countryImpl.setId(country.getId());
        countryImpl.setCode(country.getCode());
        countryImpl.setDescriptionEn(country.getDescriptionEn());
        countryImpl.setRegion(country.getRegion());
        countryImpl.setIsoNumericCode(country.getIsoNumericCode());
        countryImpl.setEuCode(country.getEuCode());
        countryImpl.setActive(country.getActive());
        countryImpl.setDenied(country.getDenied());
        countryImpl.setRestricted(country.getRestricted());
        countryImpl.setOrganizationId(country.getOrganizationId());
        countryImpl.setDefaultCurrencyId(country.getDefaultCurrencyId());
        countryImpl.setWhyInactive(country.getWhyInactive());

        return countryImpl;
    }

    /**
     * Finds the Country with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Country to find
     * @return the Country
     * @throws com.liferay.portal.NoSuchModelException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey((String) primaryKey);
    }

    /**
     * Finds the Country with the primary key or throws a {@link com.erp.lead.NoSuchCountryException} if it could not be found.
     *
     * @param id the primary key of the Country to find
     * @return the Country
     * @throws com.erp.lead.NoSuchCountryException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByPrimaryKey(String id)
        throws NoSuchCountryException, SystemException {
        Country country = fetchByPrimaryKey(id);

        if (country == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchCountryException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return country;
    }

    /**
     * Finds the Country with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Country to find
     * @return the Country, or <code>null</code> if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey((String) primaryKey);
    }

    /**
     * Finds the Country with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Country to find
     * @return the Country, or <code>null</code> if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country fetchByPrimaryKey(String id) throws SystemException {
        Country country = (Country) EntityCacheUtil.getResult(CountryModelImpl.ENTITY_CACHE_ENABLED,
                CountryImpl.class, id, this);

        if (country == null) {
            Session session = null;

            try {
                session = openSession();

                country = (Country) session.get(CountryImpl.class, id);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (country != null) {
                    cacheResult(country);
                }

                closeSession(session);
            }
        }

        return country;
    }

    /**
     * Finds all the Countries where code = &#63;.
     *
     * @param code the code to search with
     * @return the matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByCode(String code) throws SystemException {
        return findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Countries where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @return the range of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByCode(String code, int start, int end)
        throws SystemException {
        return findByCode(code, start, end, null);
    }

    /**
     * Finds an ordered range of all the Countries where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByCode(String code, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                code,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Country> list = (List<Country>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODE,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_COUNTRY_WHERE);

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

                list = (List<Country>) QueryUtil.list(q, getDialect(), start,
                        end);
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
     * Finds the first Country in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Country
     * @throws com.erp.lead.NoSuchCountryException if a matching Country could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByCode_First(String code,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        List<Country> list = findByCode(code, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchCountryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Country in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Country
     * @throws com.erp.lead.NoSuchCountryException if a matching Country could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByCode_Last(String code,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        int count = countByCode(code);

        List<Country> list = findByCode(code, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchCountryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Countries before and after the current Country in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Country
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Country
     * @throws com.erp.lead.NoSuchCountryException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country[] findByCode_PrevAndNext(String id, String code,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        Country country = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Country[] array = new CountryImpl[3];

            array[0] = getByCode_PrevAndNext(session, country, code,
                    orderByComparator, true);

            array[1] = country;

            array[2] = getByCode_PrevAndNext(session, country, code,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Country getByCode_PrevAndNext(Session session, Country country,
        String code, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_COUNTRY_WHERE);

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
            Object[] values = orderByComparator.getOrderByValues(country);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Country> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Countries where isoNumericCode = &#63;.
     *
     * @param isoNumericCode the iso numeric code to search with
     * @return the matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByISO(String isoNumericCode)
        throws SystemException {
        return findByISO(isoNumericCode, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
            null);
    }

    /**
     * Finds a range of all the Countries where isoNumericCode = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param isoNumericCode the iso numeric code to search with
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @return the range of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByISO(String isoNumericCode, int start, int end)
        throws SystemException {
        return findByISO(isoNumericCode, start, end, null);
    }

    /**
     * Finds an ordered range of all the Countries where isoNumericCode = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param isoNumericCode the iso numeric code to search with
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findByISO(String isoNumericCode, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                isoNumericCode,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Country> list = (List<Country>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ISO,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_COUNTRY_WHERE);

            if (isoNumericCode == null) {
                query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_1);
            } else {
                if (isoNumericCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_2);
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

                if (isoNumericCode != null) {
                    qPos.add(isoNumericCode);
                }

                list = (List<Country>) QueryUtil.list(q, getDialect(), start,
                        end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ISO,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ISO,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Country in the ordered set where isoNumericCode = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param isoNumericCode the iso numeric code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Country
     * @throws com.erp.lead.NoSuchCountryException if a matching Country could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByISO_First(String isoNumericCode,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        List<Country> list = findByISO(isoNumericCode, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("isoNumericCode=");
            msg.append(isoNumericCode);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchCountryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Country in the ordered set where isoNumericCode = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param isoNumericCode the iso numeric code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Country
     * @throws com.erp.lead.NoSuchCountryException if a matching Country could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country findByISO_Last(String isoNumericCode,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        int count = countByISO(isoNumericCode);

        List<Country> list = findByISO(isoNumericCode, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("isoNumericCode=");
            msg.append(isoNumericCode);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchCountryException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Countries before and after the current Country in the ordered set where isoNumericCode = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Country
     * @param isoNumericCode the iso numeric code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Country
     * @throws com.erp.lead.NoSuchCountryException if a Country with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Country[] findByISO_PrevAndNext(String id, String isoNumericCode,
        OrderByComparator orderByComparator)
        throws NoSuchCountryException, SystemException {
        Country country = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Country[] array = new CountryImpl[3];

            array[0] = getByISO_PrevAndNext(session, country, isoNumericCode,
                    orderByComparator, true);

            array[1] = country;

            array[2] = getByISO_PrevAndNext(session, country, isoNumericCode,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Country getByISO_PrevAndNext(Session session, Country country,
        String isoNumericCode, OrderByComparator orderByComparator,
        boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_COUNTRY_WHERE);

        if (isoNumericCode == null) {
            query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_1);
        } else {
            if (isoNumericCode.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_3);
            } else {
                query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_2);
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

        if (isoNumericCode != null) {
            qPos.add(isoNumericCode);
        }

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(country);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Country> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Countries.
     *
     * @return the Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Countries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @return the range of Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Countries.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Countries to return
     * @param end the upper bound of the range of Countries to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Countries
     * @throws SystemException if a system exception occurred
     */
    public List<Country> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Country> list = (List<Country>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_COUNTRY);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_COUNTRY;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Country>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Country>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Countries where code = &#63; from the database.
     *
     * @param code the code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByCode(String code) throws SystemException {
        for (Country country : findByCode(code)) {
            remove(country);
        }
    }

    /**
     * Removes all the Countries where isoNumericCode = &#63; from the database.
     *
     * @param isoNumericCode the iso numeric code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByISO(String isoNumericCode) throws SystemException {
        for (Country country : findByISO(isoNumericCode)) {
            remove(country);
        }
    }

    /**
     * Removes all the Countries from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Country country : findAll()) {
            remove(country);
        }
    }

    /**
     * Counts all the Countries where code = &#63;.
     *
     * @param code the code to search with
     * @return the number of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public int countByCode(String code) throws SystemException {
        Object[] finderArgs = new Object[] { code };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODE,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_COUNTRY_WHERE);

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
     * Counts all the Countries where isoNumericCode = &#63;.
     *
     * @param isoNumericCode the iso numeric code to search with
     * @return the number of matching Countries
     * @throws SystemException if a system exception occurred
     */
    public int countByISO(String isoNumericCode) throws SystemException {
        Object[] finderArgs = new Object[] { isoNumericCode };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ISO,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_COUNTRY_WHERE);

            if (isoNumericCode == null) {
                query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_1);
            } else {
                if (isoNumericCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_ISO_ISONUMERICCODE_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (isoNumericCode != null) {
                    qPos.add(isoNumericCode);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ISO, finderArgs,
                    count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Countries.
     *
     * @return the number of Countries
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

                Query q = session.createQuery(_SQL_COUNT_COUNTRY);

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
     * Initializes the Country persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.Country")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Country>> listenersList = new ArrayList<ModelListener<Country>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Country>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(CountryImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
