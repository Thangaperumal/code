package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchOrderLineException;
import com.erp.lead.model.OrderLine;
import com.erp.lead.model.impl.OrderLineImpl;
import com.erp.lead.model.impl.OrderLineModelImpl;
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
 * The persistence implementation for the OrderLine service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link OrderLineUtil} to access the OrderLine persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see OrderLinePersistence
 * @see OrderLineUtil
 * @generated
 */
public class OrderLinePersistenceImpl extends BasePersistenceImpl<OrderLine>
    implements OrderLinePersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = OrderLineImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_ORDERLINE = "SELECT orderLine FROM OrderLine orderLine";
    private static final String _SQL_COUNT_ORDERLINE = "SELECT COUNT(orderLine) FROM OrderLine orderLine";
    private static final String _ORDER_BY_ENTITY_ALIAS = "orderLine.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No OrderLine exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(OrderLinePersistenceImpl.class);
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
     * Caches the OrderLine in the entity cache if it is enabled.
     *
     * @param orderLine the OrderLine to cache
     */
    public void cacheResult(OrderLine orderLine) {
        EntityCacheUtil.putResult(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineImpl.class, orderLine.getPrimaryKey(), orderLine);
    }

    /**
     * Caches the OrderLines in the entity cache if it is enabled.
     *
     * @param orderLines the OrderLines to cache
     */
    public void cacheResult(List<OrderLine> orderLines) {
        for (OrderLine orderLine : orderLines) {
            if (EntityCacheUtil.getResult(
                        OrderLineModelImpl.ENTITY_CACHE_ENABLED,
                        OrderLineImpl.class, orderLine.getPrimaryKey(), this) == null) {
                cacheResult(orderLine);
            }
        }
    }

    /**
     * Clears the cache for all OrderLines.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(OrderLineImpl.class.getName());
        EntityCacheUtil.clearCache(OrderLineImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the OrderLine.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(OrderLine orderLine) {
        EntityCacheUtil.removeResult(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineImpl.class, orderLine.getPrimaryKey());
    }

    /**
     * Creates a new OrderLine with the primary key. Does not add the OrderLine to the database.
     *
     * @param id the primary key for the new OrderLine
     * @return the new OrderLine
     */
    public OrderLine create(int id) {
        OrderLine orderLine = new OrderLineImpl();

        orderLine.setNew(true);
        orderLine.setPrimaryKey(id);

        return orderLine;
    }

    /**
     * Removes the OrderLine with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the OrderLine to remove
     * @return the OrderLine that was removed
     * @throws com.liferay.portal.NoSuchModelException if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the OrderLine with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the OrderLine to remove
     * @return the OrderLine that was removed
     * @throws com.erp.lead.NoSuchOrderLineException if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine remove(int id)
        throws NoSuchOrderLineException, SystemException {
        Session session = null;

        try {
            session = openSession();

            OrderLine orderLine = (OrderLine) session.get(OrderLineImpl.class,
                    new Integer(id));

            if (orderLine == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchOrderLineException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(orderLine);
        } catch (NoSuchOrderLineException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected OrderLine removeImpl(OrderLine orderLine)
        throws SystemException {
        orderLine = toUnwrappedModel(orderLine);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, orderLine);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineImpl.class, orderLine.getPrimaryKey());

        return orderLine;
    }

    public OrderLine updateImpl(
        com.erp.lead.model.OrderLine orderLine, boolean merge)
        throws SystemException {
        orderLine = toUnwrappedModel(orderLine);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, orderLine, merge);

            orderLine.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
            OrderLineImpl.class, orderLine.getPrimaryKey(), orderLine);

        return orderLine;
    }

    protected OrderLine toUnwrappedModel(OrderLine orderLine) {
        if (orderLine instanceof OrderLineImpl) {
            return orderLine;
        }

        OrderLineImpl orderLineImpl = new OrderLineImpl();

        orderLineImpl.setNew(orderLine.isNew());
        orderLineImpl.setPrimaryKey(orderLine.getPrimaryKey());

        orderLineImpl.setId(orderLine.getId());
        orderLineImpl.setItemId(orderLine.getItemId());
        orderLineImpl.setOrderId(orderLine.getOrderId());
        orderLineImpl.setQuantity(orderLine.getQuantity());
        orderLineImpl.setCurrencyId(orderLine.getCurrencyId());
        orderLineImpl.setListPricePerItem(orderLine.getListPricePerItem());
        orderLineImpl.setYourPricePerItem(orderLine.getYourPricePerItem());
        orderLineImpl.setTax(orderLine.getTax());
        orderLineImpl.setCreatedAt(orderLine.getCreatedAt());
        orderLineImpl.setUpdatedAt(orderLine.getUpdatedAt());
        orderLineImpl.setShippingLineAmount(orderLine.getShippingLineAmount());
        orderLineImpl.setApplicationGuid(orderLine.getApplicationGuid());
        orderLineImpl.setEschoolGuid(orderLine.getEschoolGuid());
        orderLineImpl.setPremiumCommunityGuid(orderLine.getPremiumCommunityGuid());
        orderLineImpl.setUserGuid(orderLine.getUserGuid());
        orderLineImpl.setSerialNumber(orderLine.getSerialNumber());
        orderLineImpl.setActivationId(orderLine.getActivationId());
        orderLineImpl.setLotusGuid(orderLine.getLotusGuid());
        orderLineImpl.setLanguage(orderLine.getLanguage());
        orderLineImpl.setApplicationRefId(orderLine.getApplicationRefId());
        orderLineImpl.setEschoolRefId(orderLine.getEschoolRefId());
        orderLineImpl.setPremiumCommunityRefId(orderLine.getPremiumCommunityRefId());
        orderLineImpl.setLotusRefId(orderLine.getLotusRefId());
        orderLineImpl.setEschoolGroupSessionsGuid(orderLine.getEschoolGroupSessionsGuid());
        orderLineImpl.setEschoolOneOnOneSessionsGuid(orderLine.getEschoolOneOnOneSessionsGuid());
        orderLineImpl.setLanguageSwitchingGuid(orderLine.getLanguageSwitchingGuid());
        orderLineImpl.setEschoolGroupSessionsRefId(orderLine.getEschoolGroupSessionsRefId());
        orderLineImpl.setEschoolOneOnOneSessionsRefId(orderLine.getEschoolOneOnOneSessionsRefId());
        orderLineImpl.setLanguageSwitchingRefId(orderLine.getLanguageSwitchingRefId());
        orderLineImpl.setAutoRenew(orderLine.getAutoRenew());

        return orderLineImpl;
    }

    /**
     * Finds the OrderLine with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the OrderLine to find
     * @return the OrderLine
     * @throws com.liferay.portal.NoSuchModelException if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the OrderLine with the primary key or throws a {@link com.erp.lead.NoSuchOrderLineException} if it could not be found.
     *
     * @param id the primary key of the OrderLine to find
     * @return the OrderLine
     * @throws com.erp.lead.NoSuchOrderLineException if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine findByPrimaryKey(int id)
        throws NoSuchOrderLineException, SystemException {
        OrderLine orderLine = fetchByPrimaryKey(id);

        if (orderLine == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchOrderLineException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return orderLine;
    }

    /**
     * Finds the OrderLine with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the OrderLine to find
     * @return the OrderLine, or <code>null</code> if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the OrderLine with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the OrderLine to find
     * @return the OrderLine, or <code>null</code> if a OrderLine with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public OrderLine fetchByPrimaryKey(int id) throws SystemException {
        OrderLine orderLine = (OrderLine) EntityCacheUtil.getResult(OrderLineModelImpl.ENTITY_CACHE_ENABLED,
                OrderLineImpl.class, id, this);

        if (orderLine == null) {
            Session session = null;

            try {
                session = openSession();

                orderLine = (OrderLine) session.get(OrderLineImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (orderLine != null) {
                    cacheResult(orderLine);
                }

                closeSession(session);
            }
        }

        return orderLine;
    }

    /**
     * Finds all the OrderLines.
     *
     * @return the OrderLines
     * @throws SystemException if a system exception occurred
     */
    public List<OrderLine> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the OrderLines.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of OrderLines to return
     * @param end the upper bound of the range of OrderLines to return (not inclusive)
     * @return the range of OrderLines
     * @throws SystemException if a system exception occurred
     */
    public List<OrderLine> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the OrderLines.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of OrderLines to return
     * @param end the upper bound of the range of OrderLines to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of OrderLines
     * @throws SystemException if a system exception occurred
     */
    public List<OrderLine> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<OrderLine> list = (List<OrderLine>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_ORDERLINE);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_ORDERLINE;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<OrderLine>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<OrderLine>) QueryUtil.list(q, getDialect(),
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
     * Removes all the OrderLines from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (OrderLine orderLine : findAll()) {
            remove(orderLine);
        }
    }

    /**
     * Counts all the OrderLines.
     *
     * @return the number of OrderLines
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

                Query q = session.createQuery(_SQL_COUNT_ORDERLINE);

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
     * Initializes the OrderLine persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.OrderLine")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<OrderLine>> listenersList = new ArrayList<ModelListener<OrderLine>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<OrderLine>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(OrderLineImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
