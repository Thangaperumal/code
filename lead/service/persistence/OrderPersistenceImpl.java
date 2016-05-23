package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchOrderException;
import com.erp.lead.model.Order;
import com.erp.lead.model.impl.OrderImpl;
import com.erp.lead.model.impl.OrderModelImpl;
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
 * The persistence implementation for the Order service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link OrderUtil} to access the Order persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see OrderPersistence
 * @see OrderUtil
 * @generated
 */
public class OrderPersistenceImpl extends BasePersistenceImpl<Order>
    implements OrderPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = OrderImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_ORDER_ = "SELECT order_ FROM Order order_";
    private static final String _SQL_COUNT_ORDER_ = "SELECT COUNT(order_) FROM Order order_";
    private static final String _ORDER_BY_ENTITY_ALIAS = "order_.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Order exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(OrderPersistenceImpl.class);
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
     * Caches the Order in the entity cache if it is enabled.
     *
     * @param order the Order to cache
     */
    public void cacheResult(Order order) {
        EntityCacheUtil.putResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderImpl.class, order.getPrimaryKey(), order);
    }

    /**
     * Caches the Orders in the entity cache if it is enabled.
     *
     * @param orders the Orders to cache
     */
    public void cacheResult(List<Order> orders) {
        for (Order order : orders) {
            if (EntityCacheUtil.getResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
                        OrderImpl.class, order.getPrimaryKey(), this) == null) {
                cacheResult(order);
            }
        }
    }

    /**
     * Clears the cache for all Orders.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(OrderImpl.class.getName());
        EntityCacheUtil.clearCache(OrderImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Order.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Order order) {
        EntityCacheUtil.removeResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderImpl.class, order.getPrimaryKey());
    }

    /**
     * Creates a new Order with the primary key. Does not add the Order to the database.
     *
     * @param id the primary key for the new Order
     * @return the new Order
     */
    public Order create(int id) {
        Order order = new OrderImpl();

        order.setNew(true);
        order.setPrimaryKey(id);

        return order;
    }

    /**
     * Removes the Order with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Order to remove
     * @return the Order that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Order with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Order to remove
     * @return the Order that was removed
     * @throws com.erp.lead.NoSuchOrderException if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order remove(int id) throws NoSuchOrderException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Order order = (Order) session.get(OrderImpl.class, new Integer(id));

            if (order == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(order);
        } catch (NoSuchOrderException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Order removeImpl(Order order) throws SystemException {
        order = toUnwrappedModel(order);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, order);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderImpl.class, order.getPrimaryKey());

        return order;
    }

    public Order updateImpl(com.erp.lead.model.Order order,
        boolean merge) throws SystemException {
        order = toUnwrappedModel(order);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, order, merge);

            order.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
            OrderImpl.class, order.getPrimaryKey(), order);

        return order;
    }

    protected Order toUnwrappedModel(Order order) {
        if (order instanceof OrderImpl) {
            return order;
        }

        OrderImpl orderImpl = new OrderImpl();

        orderImpl.setNew(order.isNew());
        orderImpl.setPrimaryKey(order.getPrimaryKey());

        orderImpl.setId(order.getId());
        orderImpl.setOrderNumber(order.getOrderNumber());
        orderImpl.setCustomerId(order.getCustomerId());
        orderImpl.setShipMethodId(order.getShipMethodId());
        orderImpl.setPromoId(order.getPromoId());
        orderImpl.setShipAddressId(order.getShipAddressId());
        orderImpl.setBillAddressId(order.getBillAddressId());
        orderImpl.setCreditCardId(order.getCreditCardId());
        orderImpl.setCertitaxTrxnId(order.getCertitaxTrxnId());
        orderImpl.setCurrencyId(order.getCurrencyId());
        orderImpl.setSubtotal(order.getSubtotal());
        orderImpl.setSubtotalVat(order.getSubtotalVat());
        orderImpl.setShipping(order.getShipping());
        orderImpl.setTax(order.getTax());
        orderImpl.setTotal(order.getTotal());
        orderImpl.setNoSignatureRequired(order.getNoSignatureRequired());
        orderImpl.setCreditCardApprovalCode(order.getCreditCardApprovalCode());
        orderImpl.setCreditCardRetries(order.getCreditCardRetries());
        orderImpl.setOrganizationId(order.getOrganizationId());
        orderImpl.setFormWhereEntered(order.getFormWhereEntered());
        orderImpl.setVertical(order.getVertical());
        orderImpl.setVatEstimationCountryCode(order.getVatEstimationCountryCode());
        orderImpl.setCreatedAt(order.getCreatedAt());
        orderImpl.setUpdatedAt(order.getUpdatedAt());
        orderImpl.setOrderedDate(order.getOrderedDate());
        orderImpl.setSalesrepId(order.getSalesrepId());
        orderImpl.setSiteId(order.getSiteId());
        orderImpl.setShippingHeaderAmount(order.getShippingHeaderAmount());
        orderImpl.setPayments(order.getPayments());
        orderImpl.setWireTransferId(order.getWireTransferId());
        orderImpl.setShowNoCostsOnSlip(order.getShowNoCostsOnSlip());
        orderImpl.setShippingInstructions(order.getShippingInstructions());
        orderImpl.setBisextorderid(order.getBisextorderid());
        orderImpl.setLanguageOfInterest(order.getLanguageOfInterest());
        orderImpl.setEcOrderId(order.getEcOrderId());
        orderImpl.setGiftMsg(order.getGiftMsg());
        orderImpl.setPaypalTransactionId(order.getPaypalTransactionId());
        orderImpl.setBillingAgreementId(order.getBillingAgreementId());
        orderImpl.setCheckOrMo(order.getCheckOrMo());
        orderImpl.setPackingInstructions(order.getPackingInstructions());
        orderImpl.setComplimentary(order.getComplimentary());
        orderImpl.setReadyForExport(order.getReadyForExport());
        orderImpl.setBisextproofxml(order.getBisextproofxml());
        orderImpl.setMaestro3dSecure(order.getMaestro3dSecure());
        orderImpl.setBisextcustomerip(order.getBisextcustomerip());
        orderImpl.setBisextcvvresult(order.getBisextcvvresult());
        orderImpl.setBisextavsresult(order.getBisextavsresult());
        orderImpl.setOrderNotes(order.getOrderNotes());
        orderImpl.setBisextfraudresult(order.getBisextfraudresult());
        orderImpl.setBisextfraudcode(order.getBisextfraudcode());
        orderImpl.setPoNumber(order.getPoNumber());
        orderImpl.setPaymentType(order.getPaymentType());
        orderImpl.setDropShip(order.getDropShip());
        orderImpl.setCustomerNumber(order.getCustomerNumber());
        orderImpl.setRepeatCustomer(order.getRepeatCustomer());
        orderImpl.setBisextattemptid(order.getBisextattemptid());
        orderImpl.setBisextpaymentproductid(order.getBisextpaymentproductid());
        orderImpl.setDirectDebitId(order.getDirectDebitId());
        orderImpl.setBisstoreid(order.getBisstoreid());
        orderImpl.setBisextpaymentmethodid(order.getBisextpaymentmethodid());
        orderImpl.setBisextstatusid(order.getBisextstatusid());
        orderImpl.setBisextamount(order.getBisextamount());
        orderImpl.setRtbtId(order.getRtbtId());
        orderImpl.setPaymentTermId(order.getPaymentTermId());
        orderImpl.setUnauthorisedOrder(order.getUnauthorisedOrder());
        orderImpl.setAmsStatus(order.getAmsStatus());
        orderImpl.setAddressOverride(order.getAddressOverride());
        orderImpl.setBillSameAsShip(order.getBillSameAsShip());
        orderImpl.setMasterGuid(order.getMasterGuid());
        orderImpl.setSaveCreditCard(order.getSaveCreditCard());
        orderImpl.setPriceListId(order.getPriceListId());
        orderImpl.setBisvendor(order.getBisvendor());
        orderImpl.setBisaddref(order.getBisaddref());
        orderImpl.setBisexteffortid(order.getBisexteffortid());
        orderImpl.setBisextpaymentreference(order.getBisextpaymentreference());
        orderImpl.setKcpResponseId(order.getKcpResponseId());
        orderImpl.setPromoOffer(order.getPromoOffer());
        orderImpl.setInstCustomer(order.getInstCustomer());
        orderImpl.setPartnerGuid(order.getPartnerGuid());
        orderImpl.setStartDate(order.getStartDate());
        orderImpl.setEndDate(order.getEndDate());

        return orderImpl;
    }

    /**
     * Finds the Order with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Order to find
     * @return the Order
     * @throws com.liferay.portal.NoSuchModelException if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Order with the primary key or throws a {@link com.erp.lead.NoSuchOrderException} if it could not be found.
     *
     * @param id the primary key of the Order to find
     * @return the Order
     * @throws com.erp.lead.NoSuchOrderException if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order findByPrimaryKey(int id)
        throws NoSuchOrderException, SystemException {
        Order order = fetchByPrimaryKey(id);

        if (order == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchOrderException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return order;
    }

    /**
     * Finds the Order with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Order to find
     * @return the Order, or <code>null</code> if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Order with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Order to find
     * @return the Order, or <code>null</code> if a Order with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Order fetchByPrimaryKey(int id) throws SystemException {
        Order order = (Order) EntityCacheUtil.getResult(OrderModelImpl.ENTITY_CACHE_ENABLED,
                OrderImpl.class, id, this);

        if (order == null) {
            Session session = null;

            try {
                session = openSession();

                order = (Order) session.get(OrderImpl.class, new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (order != null) {
                    cacheResult(order);
                }

                closeSession(session);
            }
        }

        return order;
    }

    /**
     * Finds all the Orders.
     *
     * @return the Orders
     * @throws SystemException if a system exception occurred
     */
    public List<Order> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Orders.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Orders to return
     * @param end the upper bound of the range of Orders to return (not inclusive)
     * @return the range of Orders
     * @throws SystemException if a system exception occurred
     */
    public List<Order> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Orders.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Orders to return
     * @param end the upper bound of the range of Orders to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Orders
     * @throws SystemException if a system exception occurred
     */
    public List<Order> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Order> list = (List<Order>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_ORDER_);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_ORDER_;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Order>) QueryUtil.list(q, getDialect(), start,
                            end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Order>) QueryUtil.list(q, getDialect(), start,
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
     * Removes all the Orders from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Order order : findAll()) {
            remove(order);
        }
    }

    /**
     * Counts all the Orders.
     *
     * @return the number of Orders
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

                Query q = session.createQuery(_SQL_COUNT_ORDER_);

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
     * Initializes the Order persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.Order")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Order>> listenersList = new ArrayList<ModelListener<Order>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Order>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(OrderImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
