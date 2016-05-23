package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchCustomerException;
import com.erp.lead.model.Customer;
import com.erp.lead.model.impl.CustomerImpl;
import com.erp.lead.model.impl.CustomerModelImpl;
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
 * The persistence implementation for the Customer service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link CustomerUtil} to access the Customer persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see CustomerPersistence
 * @see CustomerUtil
 * @generated
 */
public class CustomerPersistenceImpl extends BasePersistenceImpl<Customer>
    implements CustomerPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = CustomerImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_CUSTOMER = "SELECT customer FROM Customer customer";
    private static final String _SQL_COUNT_CUSTOMER = "SELECT COUNT(customer) FROM Customer customer";
    private static final String _ORDER_BY_ENTITY_ALIAS = "customer.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Customer exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(CustomerPersistenceImpl.class);
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
     * Caches the Customer in the entity cache if it is enabled.
     *
     * @param customer the Customer to cache
     */
    public void cacheResult(Customer customer) {
        EntityCacheUtil.putResult(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerImpl.class, customer.getPrimaryKey(), customer);
    }

    /**
     * Caches the Customers in the entity cache if it is enabled.
     *
     * @param customers the Customers to cache
     */
    public void cacheResult(List<Customer> customers) {
        for (Customer customer : customers) {
            if (EntityCacheUtil.getResult(
                        CustomerModelImpl.ENTITY_CACHE_ENABLED,
                        CustomerImpl.class, customer.getPrimaryKey(), this) == null) {
                cacheResult(customer);
            }
        }
    }

    /**
     * Clears the cache for all Customers.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(CustomerImpl.class.getName());
        EntityCacheUtil.clearCache(CustomerImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Customer.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Customer customer) {
        EntityCacheUtil.removeResult(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerImpl.class, customer.getPrimaryKey());
    }

    /**
     * Creates a new Customer with the primary key. Does not add the Customer to the database.
     *
     * @param id the primary key for the new Customer
     * @return the new Customer
     */
    public Customer create(int id) {
        Customer customer = new CustomerImpl();

        customer.setNew(true);
        customer.setPrimaryKey(id);

        return customer;
    }

    /**
     * Removes the Customer with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Customer to remove
     * @return the Customer that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Customer with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Customer to remove
     * @return the Customer that was removed
     * @throws com.erp.lead.NoSuchCustomerException if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer remove(int id)
        throws NoSuchCustomerException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Customer customer = (Customer) session.get(CustomerImpl.class,
                    new Integer(id));

            if (customer == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchCustomerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(customer);
        } catch (NoSuchCustomerException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Customer removeImpl(Customer customer) throws SystemException {
        customer = toUnwrappedModel(customer);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, customer);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerImpl.class, customer.getPrimaryKey());

        return customer;
    }

    public Customer updateImpl(com.erp.lead.model.Customer customer,
        boolean merge) throws SystemException {
        customer = toUnwrappedModel(customer);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, customer, merge);

            customer.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(CustomerModelImpl.ENTITY_CACHE_ENABLED,
            CustomerImpl.class, customer.getPrimaryKey(), customer);

        return customer;
    }

    protected Customer toUnwrappedModel(Customer customer) {
        if (customer instanceof CustomerImpl) {
            return customer;
        }

        CustomerImpl customerImpl = new CustomerImpl();

        customerImpl.setNew(customer.isNew());
        customerImpl.setPrimaryKey(customer.getPrimaryKey());

        customerImpl.setId(customer.getId());
        customerImpl.setFullName(customer.getFullName());
        customerImpl.setEmail(customer.getEmail());
        customerImpl.setPassword(customer.getPassword());
        customerImpl.setYouCanSpamMe(customer.getYouCanSpamMe());
        customerImpl.setAffiliatesCanSpamMe(customer.getAffiliatesCanSpamMe());
        customerImpl.setLeadCode(customer.getLeadCode());
        customerImpl.setLocale(customer.getLocale());
        customerImpl.setAffiliateCode(customer.getAffiliateCode());
        customerImpl.setAbSplit(customer.getAbSplit());
        customerImpl.setCreatedAt(customer.getCreatedAt());
        customerImpl.setUpdatedAt(customer.getUpdatedAt());
        customerImpl.setOrganizationName(customer.getOrganizationName());
        customerImpl.setFirstName(customer.getFirstName());
        customerImpl.setLastName(customer.getLastName());
        customerImpl.setPhoneNumber(customer.getPhoneNumber());
        customerImpl.setGenericLeadCode(customer.getGenericLeadCode());
        customerImpl.setDnisTelephoneNumber(customer.getDnisTelephoneNumber());
        customerImpl.setReferringDomain(customer.getReferringDomain());
        customerImpl.setGuid(customer.getGuid());
        customerImpl.setLastReferringDomain(customer.getLastReferringDomain());
        customerImpl.setLearnerEmail(customer.getLearnerEmail());

        return customerImpl;
    }

    /**
     * Finds the Customer with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Customer to find
     * @return the Customer
     * @throws com.liferay.portal.NoSuchModelException if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Customer with the primary key or throws a {@link com.erp.lead.NoSuchCustomerException} if it could not be found.
     *
     * @param id the primary key of the Customer to find
     * @return the Customer
     * @throws com.erp.lead.NoSuchCustomerException if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer findByPrimaryKey(int id)
        throws NoSuchCustomerException, SystemException {
        Customer customer = fetchByPrimaryKey(id);

        if (customer == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchCustomerException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return customer;
    }

    /**
     * Finds the Customer with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Customer to find
     * @return the Customer, or <code>null</code> if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Customer with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Customer to find
     * @return the Customer, or <code>null</code> if a Customer with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Customer fetchByPrimaryKey(int id) throws SystemException {
        Customer customer = (Customer) EntityCacheUtil.getResult(CustomerModelImpl.ENTITY_CACHE_ENABLED,
                CustomerImpl.class, id, this);

        if (customer == null) {
            Session session = null;

            try {
                session = openSession();

                customer = (Customer) session.get(CustomerImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (customer != null) {
                    cacheResult(customer);
                }

                closeSession(session);
            }
        }

        return customer;
    }

    /**
     * Finds all the Customers.
     *
     * @return the Customers
     * @throws SystemException if a system exception occurred
     */
    public List<Customer> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Customers.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Customers to return
     * @param end the upper bound of the range of Customers to return (not inclusive)
     * @return the range of Customers
     * @throws SystemException if a system exception occurred
     */
    public List<Customer> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Customers.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Customers to return
     * @param end the upper bound of the range of Customers to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Customers
     * @throws SystemException if a system exception occurred
     */
    public List<Customer> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Customer> list = (List<Customer>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_CUSTOMER);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_CUSTOMER;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Customer>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Customer>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Customers from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Customer customer : findAll()) {
            remove(customer);
        }
    }

    /**
     * Counts all the Customers.
     *
     * @return the number of Customers
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

                Query q = session.createQuery(_SQL_COUNT_CUSTOMER);

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
     * Initializes the Customer persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.Customer")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Customer>> listenersList = new ArrayList<ModelListener<Customer>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Customer>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(CustomerImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
