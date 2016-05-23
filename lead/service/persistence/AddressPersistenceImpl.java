package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchAddressException;
import com.erp.lead.model.Address;
import com.erp.lead.model.impl.AddressImpl;
import com.erp.lead.model.impl.AddressModelImpl;
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
 * The persistence implementation for the Address service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link AddressUtil} to access the Address persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see AddressPersistence
 * @see AddressUtil
 * @generated
 */
public class AddressPersistenceImpl extends BasePersistenceImpl<Address>
    implements AddressPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = AddressImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_ADDRESS = "SELECT address FROM Address address";
    private static final String _SQL_COUNT_ADDRESS = "SELECT COUNT(address) FROM Address address";
    private static final String _ORDER_BY_ENTITY_ALIAS = "address.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Address exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(AddressPersistenceImpl.class);
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
     * Caches the Address in the entity cache if it is enabled.
     *
     * @param address the Address to cache
     */
    public void cacheResult(Address address) {
        EntityCacheUtil.putResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressImpl.class, address.getPrimaryKey(), address);
    }

    /**
     * Caches the Addresses in the entity cache if it is enabled.
     *
     * @param addresses the Addresses to cache
     */
    public void cacheResult(List<Address> addresses) {
        for (Address address : addresses) {
            if (EntityCacheUtil.getResult(
                        AddressModelImpl.ENTITY_CACHE_ENABLED,
                        AddressImpl.class, address.getPrimaryKey(), this) == null) {
                cacheResult(address);
            }
        }
    }

    /**
     * Clears the cache for all Addresses.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(AddressImpl.class.getName());
        EntityCacheUtil.clearCache(AddressImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Address.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Address address) {
        EntityCacheUtil.removeResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressImpl.class, address.getPrimaryKey());
    }

    /**
     * Creates a new Address with the primary key. Does not add the Address to the database.
     *
     * @param id the primary key for the new Address
     * @return the new Address
     */
    public Address create(int id) {
        Address address = new AddressImpl();

        address.setNew(true);
        address.setPrimaryKey(id);

        return address;
    }

    /**
     * Removes the Address with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Address to remove
     * @return the Address that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Address with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Address to remove
     * @return the Address that was removed
     * @throws com.erp.lead.NoSuchAddressException if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address remove(int id)
        throws NoSuchAddressException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Address address = (Address) session.get(AddressImpl.class,
                    new Integer(id));

            if (address == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(address);
        } catch (NoSuchAddressException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Address removeImpl(Address address) throws SystemException {
        address = toUnwrappedModel(address);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, address);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressImpl.class, address.getPrimaryKey());

        return address;
    }

    public Address updateImpl(com.erp.lead.model.Address address,
        boolean merge) throws SystemException {
        address = toUnwrappedModel(address);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, address, merge);

            address.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
            AddressImpl.class, address.getPrimaryKey(), address);

        return address;
    }

    protected Address toUnwrappedModel(Address address) {
        if (address instanceof AddressImpl) {
            return address;
        }

        AddressImpl addressImpl = new AddressImpl();

        addressImpl.setNew(address.isNew());
        addressImpl.setPrimaryKey(address.getPrimaryKey());

        addressImpl.setId(address.getId());
        addressImpl.setFullName(address.getFullName());
        addressImpl.setAddress1(address.getAddress1());
        addressImpl.setAddress2(address.getAddress2());
        addressImpl.setAddress3(address.getAddress3());
        addressImpl.setAddress4(address.getAddress4());
        addressImpl.setCity(address.getCity());
        addressImpl.setCounty(address.getCounty());
        addressImpl.setState(address.getState());
        addressImpl.setProvince(address.getProvince());
        addressImpl.setPostalCode(address.getPostalCode());
        addressImpl.setCountryCode(address.getCountryCode());
        addressImpl.setPhoneNumber(address.getPhoneNumber());
        addressImpl.setCreatedAt(address.getCreatedAt());
        addressImpl.setUpdatedAt(address.getUpdatedAt());
        addressImpl.setPage(address.getPage());
        addressImpl.setFirstName(address.getFirstName());
        addressImpl.setLastName(address.getLastName());
        addressImpl.setFirstNamePhonetic(address.getFirstNamePhonetic());
        addressImpl.setLastNamePhonetic(address.getLastNamePhonetic());
        addressImpl.setZip4(address.getZip4());

        return addressImpl;
    }

    /**
     * Finds the Address with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Address to find
     * @return the Address
     * @throws com.liferay.portal.NoSuchModelException if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Address with the primary key or throws a {@link com.erp.lead.NoSuchAddressException} if it could not be found.
     *
     * @param id the primary key of the Address to find
     * @return the Address
     * @throws com.erp.lead.NoSuchAddressException if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address findByPrimaryKey(int id)
        throws NoSuchAddressException, SystemException {
        Address address = fetchByPrimaryKey(id);

        if (address == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchAddressException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return address;
    }

    /**
     * Finds the Address with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Address to find
     * @return the Address, or <code>null</code> if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Address with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Address to find
     * @return the Address, or <code>null</code> if a Address with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Address fetchByPrimaryKey(int id) throws SystemException {
        Address address = (Address) EntityCacheUtil.getResult(AddressModelImpl.ENTITY_CACHE_ENABLED,
                AddressImpl.class, id, this);

        if (address == null) {
            Session session = null;

            try {
                session = openSession();

                address = (Address) session.get(AddressImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (address != null) {
                    cacheResult(address);
                }

                closeSession(session);
            }
        }

        return address;
    }

    /**
     * Finds all the Addresses.
     *
     * @return the Addresses
     * @throws SystemException if a system exception occurred
     */
    public List<Address> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Addresses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Addresses to return
     * @param end the upper bound of the range of Addresses to return (not inclusive)
     * @return the range of Addresses
     * @throws SystemException if a system exception occurred
     */
    public List<Address> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Addresses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Addresses to return
     * @param end the upper bound of the range of Addresses to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Addresses
     * @throws SystemException if a system exception occurred
     */
    public List<Address> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Address> list = (List<Address>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_ADDRESS);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_ADDRESS;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Address>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Address>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Addresses from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Address address : findAll()) {
            remove(address);
        }
    }

    /**
     * Counts all the Addresses.
     *
     * @return the number of Addresses
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

                Query q = session.createQuery(_SQL_COUNT_ADDRESS);

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
     * Initializes the Address persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.Address")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Address>> listenersList = new ArrayList<ModelListener<Address>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Address>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(AddressImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
