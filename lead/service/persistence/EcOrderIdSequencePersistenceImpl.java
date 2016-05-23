package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchEcOrderIdSequenceException;
import com.erp.lead.model.EcOrderIdSequence;
import com.erp.lead.model.impl.EcOrderIdSequenceImpl;
import com.erp.lead.model.impl.EcOrderIdSequenceModelImpl;
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
import com.liferay.portal.kernel.dao.orm.QueryUtil;
import com.liferay.portal.kernel.dao.orm.Session;
import com.liferay.portal.kernel.exception.SystemException;
import com.liferay.portal.kernel.log.Log;
import com.liferay.portal.kernel.log.LogFactoryUtil;
import com.liferay.portal.kernel.util.GetterUtil;
import com.liferay.portal.kernel.util.InstanceFactory;
import com.liferay.portal.kernel.util.OrderByComparator;
import com.liferay.portal.kernel.util.StringBundler;
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
 * The persistence implementation for the EcOrderIdSequence service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link EcOrderIdSequenceUtil} to access the EcOrderIdSequence persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see EcOrderIdSequencePersistence
 * @see EcOrderIdSequenceUtil
 * @generated
 */
public class EcOrderIdSequencePersistenceImpl extends BasePersistenceImpl<EcOrderIdSequence>
    implements EcOrderIdSequencePersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = EcOrderIdSequenceImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "countAll", new String[0]);
    private static final String _SQL_SELECT_ECORDERIDSEQUENCE = "SELECT ecOrderIdSequence FROM EcOrderIdSequence ecOrderIdSequence";
    private static final String _SQL_COUNT_ECORDERIDSEQUENCE = "SELECT COUNT(ecOrderIdSequence) FROM EcOrderIdSequence ecOrderIdSequence";
    private static final String _ORDER_BY_ENTITY_ALIAS = "ecOrderIdSequence.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No EcOrderIdSequence exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(EcOrderIdSequencePersistenceImpl.class);
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
     * Caches the EcOrderIdSequence in the entity cache if it is enabled.
     *
     * @param ecOrderIdSequence the EcOrderIdSequence to cache
     */
    public void cacheResult(EcOrderIdSequence ecOrderIdSequence) {
        EntityCacheUtil.putResult(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceImpl.class, ecOrderIdSequence.getPrimaryKey(),
            ecOrderIdSequence);
    }

    /**
     * Caches the EcOrderIdSequences in the entity cache if it is enabled.
     *
     * @param ecOrderIdSequences the EcOrderIdSequences to cache
     */
    public void cacheResult(List<EcOrderIdSequence> ecOrderIdSequences) {
        for (EcOrderIdSequence ecOrderIdSequence : ecOrderIdSequences) {
            if (EntityCacheUtil.getResult(
                        EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
                        EcOrderIdSequenceImpl.class,
                        ecOrderIdSequence.getPrimaryKey(), this) == null) {
                cacheResult(ecOrderIdSequence);
            }
        }
    }

    /**
     * Clears the cache for all EcOrderIdSequences.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(EcOrderIdSequenceImpl.class.getName());
        EntityCacheUtil.clearCache(EcOrderIdSequenceImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the EcOrderIdSequence.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(EcOrderIdSequence ecOrderIdSequence) {
        EntityCacheUtil.removeResult(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceImpl.class, ecOrderIdSequence.getPrimaryKey());
    }

    /**
     * Creates a new EcOrderIdSequence with the primary key. Does not add the EcOrderIdSequence to the database.
     *
     * @param id the primary key for the new EcOrderIdSequence
     * @return the new EcOrderIdSequence
     */
    public EcOrderIdSequence create(int id) {
        EcOrderIdSequence ecOrderIdSequence = new EcOrderIdSequenceImpl();

        ecOrderIdSequence.setNew(true);
        ecOrderIdSequence.setPrimaryKey(id);

        return ecOrderIdSequence;
    }

    /**
     * Removes the EcOrderIdSequence with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the EcOrderIdSequence to remove
     * @return the EcOrderIdSequence that was removed
     * @throws com.liferay.portal.NoSuchModelException if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the EcOrderIdSequence with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the EcOrderIdSequence to remove
     * @return the EcOrderIdSequence that was removed
     * @throws com.erp.lead.NoSuchEcOrderIdSequenceException if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence remove(int id)
        throws NoSuchEcOrderIdSequenceException, SystemException {
        Session session = null;

        try {
            session = openSession();

            EcOrderIdSequence ecOrderIdSequence = (EcOrderIdSequence) session.get(EcOrderIdSequenceImpl.class,
                    new Integer(id));

            if (ecOrderIdSequence == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchEcOrderIdSequenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(ecOrderIdSequence);
        } catch (NoSuchEcOrderIdSequenceException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected EcOrderIdSequence removeImpl(EcOrderIdSequence ecOrderIdSequence)
        throws SystemException {
        ecOrderIdSequence = toUnwrappedModel(ecOrderIdSequence);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, ecOrderIdSequence);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceImpl.class, ecOrderIdSequence.getPrimaryKey());

        return ecOrderIdSequence;
    }

    public EcOrderIdSequence updateImpl(
        com.rosettastone.cis.model.EcOrderIdSequence ecOrderIdSequence,
        boolean merge) throws SystemException {
        ecOrderIdSequence = toUnwrappedModel(ecOrderIdSequence);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, ecOrderIdSequence, merge);

            ecOrderIdSequence.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
            EcOrderIdSequenceImpl.class, ecOrderIdSequence.getPrimaryKey(),
            ecOrderIdSequence);

        return ecOrderIdSequence;
    }

    protected EcOrderIdSequence toUnwrappedModel(
        EcOrderIdSequence ecOrderIdSequence) {
        if (ecOrderIdSequence instanceof EcOrderIdSequenceImpl) {
            return ecOrderIdSequence;
        }

        EcOrderIdSequenceImpl ecOrderIdSequenceImpl = new EcOrderIdSequenceImpl();

        ecOrderIdSequenceImpl.setNew(ecOrderIdSequence.isNew());
        ecOrderIdSequenceImpl.setPrimaryKey(ecOrderIdSequence.getPrimaryKey());

        ecOrderIdSequenceImpl.setId(ecOrderIdSequence.getId());

        return ecOrderIdSequenceImpl;
    }

    /**
     * Finds the EcOrderIdSequence with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the EcOrderIdSequence to find
     * @return the EcOrderIdSequence
     * @throws com.liferay.portal.NoSuchModelException if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the EcOrderIdSequence with the primary key or throws a {@link com.erp.lead.NoSuchEcOrderIdSequenceException} if it could not be found.
     *
     * @param id the primary key of the EcOrderIdSequence to find
     * @return the EcOrderIdSequence
     * @throws com.erp.lead.NoSuchEcOrderIdSequenceException if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence findByPrimaryKey(int id)
        throws NoSuchEcOrderIdSequenceException, SystemException {
        EcOrderIdSequence ecOrderIdSequence = fetchByPrimaryKey(id);

        if (ecOrderIdSequence == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchEcOrderIdSequenceException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return ecOrderIdSequence;
    }

    /**
     * Finds the EcOrderIdSequence with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the EcOrderIdSequence to find
     * @return the EcOrderIdSequence, or <code>null</code> if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the EcOrderIdSequence with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the EcOrderIdSequence to find
     * @return the EcOrderIdSequence, or <code>null</code> if a EcOrderIdSequence with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public EcOrderIdSequence fetchByPrimaryKey(int id)
        throws SystemException {
        EcOrderIdSequence ecOrderIdSequence = (EcOrderIdSequence) EntityCacheUtil.getResult(EcOrderIdSequenceModelImpl.ENTITY_CACHE_ENABLED,
                EcOrderIdSequenceImpl.class, id, this);

        if (ecOrderIdSequence == null) {
            Session session = null;

            try {
                session = openSession();

                ecOrderIdSequence = (EcOrderIdSequence) session.get(EcOrderIdSequenceImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (ecOrderIdSequence != null) {
                    cacheResult(ecOrderIdSequence);
                }

                closeSession(session);
            }
        }

        return ecOrderIdSequence;
    }

    /**
     * Finds all the EcOrderIdSequences.
     *
     * @return the EcOrderIdSequences
     * @throws SystemException if a system exception occurred
     */
    public List<EcOrderIdSequence> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the EcOrderIdSequences.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of EcOrderIdSequences to return
     * @param end the upper bound of the range of EcOrderIdSequences to return (not inclusive)
     * @return the range of EcOrderIdSequences
     * @throws SystemException if a system exception occurred
     */
    public List<EcOrderIdSequence> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the EcOrderIdSequences.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of EcOrderIdSequences to return
     * @param end the upper bound of the range of EcOrderIdSequences to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of EcOrderIdSequences
     * @throws SystemException if a system exception occurred
     */
    public List<EcOrderIdSequence> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<EcOrderIdSequence> list = (List<EcOrderIdSequence>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_ECORDERIDSEQUENCE);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_ECORDERIDSEQUENCE;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<EcOrderIdSequence>) QueryUtil.list(q,
                            getDialect(), start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<EcOrderIdSequence>) QueryUtil.list(q,
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
     * Removes all the EcOrderIdSequences from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (EcOrderIdSequence ecOrderIdSequence : findAll()) {
            remove(ecOrderIdSequence);
        }
    }

    /**
     * Counts all the EcOrderIdSequences.
     *
     * @return the number of EcOrderIdSequences
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

                Query q = session.createQuery(_SQL_COUNT_ECORDERIDSEQUENCE);

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
     * Initializes the EcOrderIdSequence persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.EcOrderIdSequence")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<EcOrderIdSequence>> listenersList = new ArrayList<ModelListener<EcOrderIdSequence>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<EcOrderIdSequence>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(EcOrderIdSequenceImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
