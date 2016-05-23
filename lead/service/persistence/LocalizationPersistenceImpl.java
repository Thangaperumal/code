package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchLocalizationException;
import com.erp.lead.model.Localization;
import com.erp.lead.model.impl.LocalizationImpl;
import com.erp.lead.model.impl.LocalizationModelImpl;
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
 * The persistence implementation for the Localization service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link LocalizationUtil} to access the Localization persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see LocalizationPersistence
 * @see LocalizationUtil
 * @generated
 */
public class LocalizationPersistenceImpl extends BasePersistenceImpl<Localization>
    implements LocalizationPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = LocalizationImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_CODE = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByCode",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_CODE = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByCode", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_TYPECODE = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByTypeCode",
            new String[] {
                String.class.getName(), String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_TYPECODE = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByTypeCode",
            new String[] { String.class.getName(), String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_LOCALIZATION = "SELECT localization FROM Localization localization";
    private static final String _SQL_SELECT_LOCALIZATION_WHERE = "SELECT localization FROM Localization localization WHERE ";
    private static final String _SQL_COUNT_LOCALIZATION = "SELECT COUNT(localization) FROM Localization localization";
    private static final String _SQL_COUNT_LOCALIZATION_WHERE = "SELECT COUNT(localization) FROM Localization localization WHERE ";
    private static final String _FINDER_COLUMN_CODE_CODE_1 = "localization.code IS NULL";
    private static final String _FINDER_COLUMN_CODE_CODE_2 = "localization.code = ?";
    private static final String _FINDER_COLUMN_CODE_CODE_3 = "(localization.code IS NULL OR localization.code = ?)";
    private static final String _FINDER_COLUMN_TYPECODE_TYPE_1 = "localization.type IS NULL AND ";
    private static final String _FINDER_COLUMN_TYPECODE_TYPE_2 = "localization.type = ? AND ";
    private static final String _FINDER_COLUMN_TYPECODE_TYPE_3 = "(localization.type IS NULL OR localization.type = ?) AND ";
    private static final String _FINDER_COLUMN_TYPECODE_CODE_1 = "localization.code IS NULL";
    private static final String _FINDER_COLUMN_TYPECODE_CODE_2 = "localization.code = ?";
    private static final String _FINDER_COLUMN_TYPECODE_CODE_3 = "(localization.code IS NULL OR localization.code = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "localization.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Localization exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Localization exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(LocalizationPersistenceImpl.class);
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
     * Caches the Localization in the entity cache if it is enabled.
     *
     * @param localization the Localization to cache
     */
    public void cacheResult(Localization localization) {
        EntityCacheUtil.putResult(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationImpl.class, localization.getPrimaryKey(), localization);
    }

    /**
     * Caches the Localizations in the entity cache if it is enabled.
     *
     * @param localizations the Localizations to cache
     */
    public void cacheResult(List<Localization> localizations) {
        for (Localization localization : localizations) {
            if (EntityCacheUtil.getResult(
                        LocalizationModelImpl.ENTITY_CACHE_ENABLED,
                        LocalizationImpl.class, localization.getPrimaryKey(),
                        this) == null) {
                cacheResult(localization);
            }
        }
    }

    /**
     * Clears the cache for all Localizations.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(LocalizationImpl.class.getName());
        EntityCacheUtil.clearCache(LocalizationImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Localization.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Localization localization) {
        EntityCacheUtil.removeResult(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationImpl.class, localization.getPrimaryKey());
    }

    /**
     * Creates a new Localization with the primary key. Does not add the Localization to the database.
     *
     * @param id the primary key for the new Localization
     * @return the new Localization
     */
    public Localization create(int id) {
        Localization localization = new LocalizationImpl();

        localization.setNew(true);
        localization.setPrimaryKey(id);

        return localization;
    }

    /**
     * Removes the Localization with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Localization to remove
     * @return the Localization that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Localization with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Localization to remove
     * @return the Localization that was removed
     * @throws com.erp.lead.NoSuchLocalizationException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization remove(int id)
        throws NoSuchLocalizationException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Localization localization = (Localization) session.get(LocalizationImpl.class,
                    new Integer(id));

            if (localization == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(localization);
        } catch (NoSuchLocalizationException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Localization removeImpl(Localization localization)
        throws SystemException {
        localization = toUnwrappedModel(localization);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, localization);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationImpl.class, localization.getPrimaryKey());

        return localization;
    }

    public Localization updateImpl(
        com.rosettastone.cis.model.Localization localization, boolean merge)
        throws SystemException {
        localization = toUnwrappedModel(localization);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, localization, merge);

            localization.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
            LocalizationImpl.class, localization.getPrimaryKey(), localization);

        return localization;
    }

    protected Localization toUnwrappedModel(Localization localization) {
        if (localization instanceof LocalizationImpl) {
            return localization;
        }

        LocalizationImpl localizationImpl = new LocalizationImpl();

        localizationImpl.setNew(localization.isNew());
        localizationImpl.setPrimaryKey(localization.getPrimaryKey());

        localizationImpl.setId(localization.getId());
        localizationImpl.setCode(localization.getCode());
        localizationImpl.setType(localization.getType());
        localizationImpl.setDescription(localization.getDescription());
        localizationImpl.setStructureId(localization.getStructureId());

        return localizationImpl;
    }

    /**
     * Finds the Localization with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Localization to find
     * @return the Localization
     * @throws com.liferay.portal.NoSuchModelException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Localization with the primary key or throws a {@link com.erp.lead.NoSuchLocalizationException} if it could not be found.
     *
     * @param id the primary key of the Localization to find
     * @return the Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByPrimaryKey(int id)
        throws NoSuchLocalizationException, SystemException {
        Localization localization = fetchByPrimaryKey(id);

        if (localization == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchLocalizationException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return localization;
    }

    /**
     * Finds the Localization with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Localization to find
     * @return the Localization, or <code>null</code> if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Localization with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Localization to find
     * @return the Localization, or <code>null</code> if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization fetchByPrimaryKey(int id) throws SystemException {
        Localization localization = (Localization) EntityCacheUtil.getResult(LocalizationModelImpl.ENTITY_CACHE_ENABLED,
                LocalizationImpl.class, id, this);

        if (localization == null) {
            Session session = null;

            try {
                session = openSession();

                localization = (Localization) session.get(LocalizationImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (localization != null) {
                    cacheResult(localization);
                }

                closeSession(session);
            }
        }

        return localization;
    }

    /**
     * Finds all the Localizations where code = &#63;.
     *
     * @param code the code to search with
     * @return the matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByCode(String code) throws SystemException {
        return findByCode(code, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Localizations where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @return the range of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByCode(String code, int start, int end)
        throws SystemException {
        return findByCode(code, start, end, null);
    }

    /**
     * Finds an ordered range of all the Localizations where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByCode(String code, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                code,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Localization> list = (List<Localization>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_CODE,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_LOCALIZATION_WHERE);

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

                list = (List<Localization>) QueryUtil.list(q, getDialect(),
                        start, end);
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
     * Finds the first Localization in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a matching Localization could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByCode_First(String code,
        OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        List<Localization> list = findByCode(code, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchLocalizationException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Localization in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a matching Localization could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByCode_Last(String code,
        OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        int count = countByCode(code);

        List<Localization> list = findByCode(code, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchLocalizationException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Localizations before and after the current Localization in the ordered set where code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Localization
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization[] findByCode_PrevAndNext(int id, String code,
        OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        Localization localization = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Localization[] array = new LocalizationImpl[3];

            array[0] = getByCode_PrevAndNext(session, localization, code,
                    orderByComparator, true);

            array[1] = localization;

            array[2] = getByCode_PrevAndNext(session, localization, code,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Localization getByCode_PrevAndNext(Session session,
        Localization localization, String code,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_LOCALIZATION_WHERE);

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
            Object[] values = orderByComparator.getOrderByValues(localization);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Localization> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Localizations where type = &#63; and code = &#63;.
     *
     * @param type the type to search with
     * @param code the code to search with
     * @return the matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByTypeCode(String type, String code)
        throws SystemException {
        return findByTypeCode(type, code, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
            null);
    }

    /**
     * Finds a range of all the Localizations where type = &#63; and code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param type the type to search with
     * @param code the code to search with
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @return the range of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByTypeCode(String type, String code,
        int start, int end) throws SystemException {
        return findByTypeCode(type, code, start, end, null);
    }

    /**
     * Finds an ordered range of all the Localizations where type = &#63; and code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param type the type to search with
     * @param code the code to search with
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findByTypeCode(String type, String code,
        int start, int end, OrderByComparator orderByComparator)
        throws SystemException {
        Object[] finderArgs = new Object[] {
                type, code,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Localization> list = (List<Localization>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_TYPECODE,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(4 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_LOCALIZATION_WHERE);

            if (type == null) {
                query.append(_FINDER_COLUMN_TYPECODE_TYPE_1);
            } else {
                if (type.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_TYPECODE_TYPE_3);
                } else {
                    query.append(_FINDER_COLUMN_TYPECODE_TYPE_2);
                }
            }

            if (code == null) {
                query.append(_FINDER_COLUMN_TYPECODE_CODE_1);
            } else {
                if (code.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_TYPECODE_CODE_3);
                } else {
                    query.append(_FINDER_COLUMN_TYPECODE_CODE_2);
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

                if (type != null) {
                    qPos.add(type);
                }

                if (code != null) {
                    qPos.add(code);
                }

                list = (List<Localization>) QueryUtil.list(q, getDialect(),
                        start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_TYPECODE,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_TYPECODE,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Localization in the ordered set where type = &#63; and code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param type the type to search with
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a matching Localization could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByTypeCode_First(String type, String code,
        OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        List<Localization> list = findByTypeCode(type, code, 0, 1,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("type=");
            msg.append(type);

            msg.append(", code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchLocalizationException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Localization in the ordered set where type = &#63; and code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param type the type to search with
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a matching Localization could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization findByTypeCode_Last(String type, String code,
        OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        int count = countByTypeCode(type, code);

        List<Localization> list = findByTypeCode(type, code, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("type=");
            msg.append(type);

            msg.append(", code=");
            msg.append(code);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchLocalizationException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Localizations before and after the current Localization in the ordered set where type = &#63; and code = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Localization
     * @param type the type to search with
     * @param code the code to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Localization
     * @throws com.erp.lead.NoSuchLocalizationException if a Localization with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Localization[] findByTypeCode_PrevAndNext(int id, String type,
        String code, OrderByComparator orderByComparator)
        throws NoSuchLocalizationException, SystemException {
        Localization localization = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Localization[] array = new LocalizationImpl[3];

            array[0] = getByTypeCode_PrevAndNext(session, localization, type,
                    code, orderByComparator, true);

            array[1] = localization;

            array[2] = getByTypeCode_PrevAndNext(session, localization, type,
                    code, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Localization getByTypeCode_PrevAndNext(Session session,
        Localization localization, String type, String code,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_LOCALIZATION_WHERE);

        if (type == null) {
            query.append(_FINDER_COLUMN_TYPECODE_TYPE_1);
        } else {
            if (type.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_TYPECODE_TYPE_3);
            } else {
                query.append(_FINDER_COLUMN_TYPECODE_TYPE_2);
            }
        }

        if (code == null) {
            query.append(_FINDER_COLUMN_TYPECODE_CODE_1);
        } else {
            if (code.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_TYPECODE_CODE_3);
            } else {
                query.append(_FINDER_COLUMN_TYPECODE_CODE_2);
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

        if (type != null) {
            qPos.add(type);
        }

        if (code != null) {
            qPos.add(code);
        }

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(localization);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Localization> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Localizations.
     *
     * @return the Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Localizations.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @return the range of Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Localizations.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Localizations to return
     * @param end the upper bound of the range of Localizations to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Localizations
     * @throws SystemException if a system exception occurred
     */
    public List<Localization> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Localization> list = (List<Localization>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_LOCALIZATION);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_LOCALIZATION;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Localization>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Localization>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Localizations where code = &#63; from the database.
     *
     * @param code the code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByCode(String code) throws SystemException {
        for (Localization localization : findByCode(code)) {
            remove(localization);
        }
    }

    /**
     * Removes all the Localizations where type = &#63; and code = &#63; from the database.
     *
     * @param type the type to search with
     * @param code the code to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByTypeCode(String type, String code)
        throws SystemException {
        for (Localization localization : findByTypeCode(type, code)) {
            remove(localization);
        }
    }

    /**
     * Removes all the Localizations from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Localization localization : findAll()) {
            remove(localization);
        }
    }

    /**
     * Counts all the Localizations where code = &#63;.
     *
     * @param code the code to search with
     * @return the number of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public int countByCode(String code) throws SystemException {
        Object[] finderArgs = new Object[] { code };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_CODE,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_LOCALIZATION_WHERE);

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
     * Counts all the Localizations where type = &#63; and code = &#63;.
     *
     * @param type the type to search with
     * @param code the code to search with
     * @return the number of matching Localizations
     * @throws SystemException if a system exception occurred
     */
    public int countByTypeCode(String type, String code)
        throws SystemException {
        Object[] finderArgs = new Object[] { type, code };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_TYPECODE,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(3);

            query.append(_SQL_COUNT_LOCALIZATION_WHERE);

            if (type == null) {
                query.append(_FINDER_COLUMN_TYPECODE_TYPE_1);
            } else {
                if (type.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_TYPECODE_TYPE_3);
                } else {
                    query.append(_FINDER_COLUMN_TYPECODE_TYPE_2);
                }
            }

            if (code == null) {
                query.append(_FINDER_COLUMN_TYPECODE_CODE_1);
            } else {
                if (code.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_TYPECODE_CODE_3);
                } else {
                    query.append(_FINDER_COLUMN_TYPECODE_CODE_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (type != null) {
                    qPos.add(type);
                }

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

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_TYPECODE,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Localizations.
     *
     * @return the number of Localizations
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

                Query q = session.createQuery(_SQL_COUNT_LOCALIZATION);

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
     * Initializes the Localization persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.Localization")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Localization>> listenersList = new ArrayList<ModelListener<Localization>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Localization>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(LocalizationImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
