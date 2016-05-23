package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchVerticalException;
import com.erp.lead.model.Vertical;
import com.erp.lead.model.impl.VerticalImpl;
import com.erp.lead.model.impl.VerticalModelImpl;
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
 * The persistence implementation for the Vertical service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link VerticalUtil} to access the Vertical persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see VerticalPersistence
 * @see VerticalUtil
 * @generated
 */
public class VerticalPersistenceImpl extends BasePersistenceImpl<Vertical>
    implements VerticalPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = VerticalImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_DESCRIPTION = new FinderPath(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByDescription",
            new String[] {
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_DESCRIPTION = new FinderPath(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByDescription", new String[] { String.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_VERTICAL = "SELECT vertical FROM Vertical vertical";
    private static final String _SQL_SELECT_VERTICAL_WHERE = "SELECT vertical FROM Vertical vertical WHERE ";
    private static final String _SQL_COUNT_VERTICAL = "SELECT COUNT(vertical) FROM Vertical vertical";
    private static final String _SQL_COUNT_VERTICAL_WHERE = "SELECT COUNT(vertical) FROM Vertical vertical WHERE ";
    private static final String _FINDER_COLUMN_DESCRIPTION_DESCRIPTION_1 = "vertical.description IS NULL";
    private static final String _FINDER_COLUMN_DESCRIPTION_DESCRIPTION_2 = "vertical.description = ?";
    private static final String _FINDER_COLUMN_DESCRIPTION_DESCRIPTION_3 = "(vertical.description IS NULL OR vertical.description = ?)";
    private static final String _ORDER_BY_ENTITY_ALIAS = "vertical.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Vertical exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Vertical exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(VerticalPersistenceImpl.class);
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
     * Caches the Vertical in the entity cache if it is enabled.
     *
     * @param vertical the Vertical to cache
     */
    public void cacheResult(Vertical vertical) {
        EntityCacheUtil.putResult(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalImpl.class, vertical.getPrimaryKey(), vertical);
    }

    /**
     * Caches the Verticals in the entity cache if it is enabled.
     *
     * @param verticals the Verticals to cache
     */
    public void cacheResult(List<Vertical> verticals) {
        for (Vertical vertical : verticals) {
            if (EntityCacheUtil.getResult(
                        VerticalModelImpl.ENTITY_CACHE_ENABLED,
                        VerticalImpl.class, vertical.getPrimaryKey(), this) == null) {
                cacheResult(vertical);
            }
        }
    }

    /**
     * Clears the cache for all Verticals.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(VerticalImpl.class.getName());
        EntityCacheUtil.clearCache(VerticalImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Vertical.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Vertical vertical) {
        EntityCacheUtil.removeResult(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalImpl.class, vertical.getPrimaryKey());
    }

    /**
     * Creates a new Vertical with the primary key. Does not add the Vertical to the database.
     *
     * @param id the primary key for the new Vertical
     * @return the new Vertical
     */
    public Vertical create(int id) {
        Vertical vertical = new VerticalImpl();

        vertical.setNew(true);
        vertical.setPrimaryKey(id);

        return vertical;
    }

    /**
     * Removes the Vertical with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Vertical to remove
     * @return the Vertical that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Vertical with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Vertical to remove
     * @return the Vertical that was removed
     * @throws com.erp.lead.NoSuchVerticalException if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical remove(int id)
        throws NoSuchVerticalException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Vertical vertical = (Vertical) session.get(VerticalImpl.class,
                    new Integer(id));

            if (vertical == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchVerticalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(vertical);
        } catch (NoSuchVerticalException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Vertical removeImpl(Vertical vertical) throws SystemException {
        vertical = toUnwrappedModel(vertical);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, vertical);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalImpl.class, vertical.getPrimaryKey());

        return vertical;
    }

    public Vertical updateImpl(com.rosettastone.cis.model.Vertical vertical,
        boolean merge) throws SystemException {
        vertical = toUnwrappedModel(vertical);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, vertical, merge);

            vertical.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(VerticalModelImpl.ENTITY_CACHE_ENABLED,
            VerticalImpl.class, vertical.getPrimaryKey(), vertical);

        return vertical;
    }

    protected Vertical toUnwrappedModel(Vertical vertical) {
        if (vertical instanceof VerticalImpl) {
            return vertical;
        }

        VerticalImpl verticalImpl = new VerticalImpl();

        verticalImpl.setNew(vertical.isNew());
        verticalImpl.setPrimaryKey(vertical.getPrimaryKey());

        verticalImpl.setId(vertical.getId());
        verticalImpl.setDescription(vertical.getDescription());
        verticalImpl.setEdition(vertical.getEdition());

        return verticalImpl;
    }

    /**
     * Finds the Vertical with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Vertical to find
     * @return the Vertical
     * @throws com.liferay.portal.NoSuchModelException if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Vertical with the primary key or throws a {@link com.erp.lead.NoSuchVerticalException} if it could not be found.
     *
     * @param id the primary key of the Vertical to find
     * @return the Vertical
     * @throws com.erp.lead.NoSuchVerticalException if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical findByPrimaryKey(int id)
        throws NoSuchVerticalException, SystemException {
        Vertical vertical = fetchByPrimaryKey(id);

        if (vertical == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchVerticalException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return vertical;
    }

    /**
     * Finds the Vertical with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Vertical to find
     * @return the Vertical, or <code>null</code> if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Vertical with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Vertical to find
     * @return the Vertical, or <code>null</code> if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical fetchByPrimaryKey(int id) throws SystemException {
        Vertical vertical = (Vertical) EntityCacheUtil.getResult(VerticalModelImpl.ENTITY_CACHE_ENABLED,
                VerticalImpl.class, id, this);

        if (vertical == null) {
            Session session = null;

            try {
                session = openSession();

                vertical = (Vertical) session.get(VerticalImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (vertical != null) {
                    cacheResult(vertical);
                }

                closeSession(session);
            }
        }

        return vertical;
    }

    /**
     * Finds all the Verticals where description = &#63;.
     *
     * @param description the description to search with
     * @return the matching Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findByDescription(String description)
        throws SystemException {
        return findByDescription(description, QueryUtil.ALL_POS,
            QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Verticals where description = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param description the description to search with
     * @param start the lower bound of the range of Verticals to return
     * @param end the upper bound of the range of Verticals to return (not inclusive)
     * @return the range of matching Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findByDescription(String description, int start,
        int end) throws SystemException {
        return findByDescription(description, start, end, null);
    }

    /**
     * Finds an ordered range of all the Verticals where description = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param description the description to search with
     * @param start the lower bound of the range of Verticals to return
     * @param end the upper bound of the range of Verticals to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findByDescription(String description, int start,
        int end, OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                description,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Vertical> list = (List<Vertical>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DESCRIPTION,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_VERTICAL_WHERE);

            if (description == null) {
                query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_1);
            } else {
                if (description.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_3);
                } else {
                    query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_2);
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

                if (description != null) {
                    qPos.add(description);
                }

                list = (List<Vertical>) QueryUtil.list(q, getDialect(), start,
                        end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DESCRIPTION,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DESCRIPTION,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Vertical in the ordered set where description = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param description the description to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Vertical
     * @throws com.erp.lead.NoSuchVerticalException if a matching Vertical could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical findByDescription_First(String description,
        OrderByComparator orderByComparator)
        throws NoSuchVerticalException, SystemException {
        List<Vertical> list = findByDescription(description, 0, 1,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("description=");
            msg.append(description);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchVerticalException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Vertical in the ordered set where description = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param description the description to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Vertical
     * @throws com.erp.lead.NoSuchVerticalException if a matching Vertical could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical findByDescription_Last(String description,
        OrderByComparator orderByComparator)
        throws NoSuchVerticalException, SystemException {
        int count = countByDescription(description);

        List<Vertical> list = findByDescription(description, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("description=");
            msg.append(description);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchVerticalException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Verticals before and after the current Vertical in the ordered set where description = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Vertical
     * @param description the description to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Vertical
     * @throws com.erp.lead.NoSuchVerticalException if a Vertical with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Vertical[] findByDescription_PrevAndNext(int id, String description,
        OrderByComparator orderByComparator)
        throws NoSuchVerticalException, SystemException {
        Vertical vertical = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Vertical[] array = new VerticalImpl[3];

            array[0] = getByDescription_PrevAndNext(session, vertical,
                    description, orderByComparator, true);

            array[1] = vertical;

            array[2] = getByDescription_PrevAndNext(session, vertical,
                    description, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Vertical getByDescription_PrevAndNext(Session session,
        Vertical vertical, String description,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_VERTICAL_WHERE);

        if (description == null) {
            query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_1);
        } else {
            if (description.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_3);
            } else {
                query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_2);
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

        if (description != null) {
            qPos.add(description);
        }

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(vertical);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Vertical> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Verticals.
     *
     * @return the Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Verticals.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Verticals to return
     * @param end the upper bound of the range of Verticals to return (not inclusive)
     * @return the range of Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Verticals.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Verticals to return
     * @param end the upper bound of the range of Verticals to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Verticals
     * @throws SystemException if a system exception occurred
     */
    public List<Vertical> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Vertical> list = (List<Vertical>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_VERTICAL);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_VERTICAL;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Vertical>) QueryUtil.list(q, getDialect(),
                            start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Vertical>) QueryUtil.list(q, getDialect(),
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
     * Removes all the Verticals where description = &#63; from the database.
     *
     * @param description the description to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByDescription(String description)
        throws SystemException {
        for (Vertical vertical : findByDescription(description)) {
            remove(vertical);
        }
    }

    /**
     * Removes all the Verticals from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Vertical vertical : findAll()) {
            remove(vertical);
        }
    }

    /**
     * Counts all the Verticals where description = &#63;.
     *
     * @param description the description to search with
     * @return the number of matching Verticals
     * @throws SystemException if a system exception occurred
     */
    public int countByDescription(String description) throws SystemException {
        Object[] finderArgs = new Object[] { description };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DESCRIPTION,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_VERTICAL_WHERE);

            if (description == null) {
                query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_1);
            } else {
                if (description.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_3);
                } else {
                    query.append(_FINDER_COLUMN_DESCRIPTION_DESCRIPTION_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (description != null) {
                    qPos.add(description);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DESCRIPTION,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Verticals.
     *
     * @return the number of Verticals
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

                Query q = session.createQuery(_SQL_COUNT_VERTICAL);

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
     * Initializes the Vertical persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.Vertical")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Vertical>> listenersList = new ArrayList<ModelListener<Vertical>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Vertical>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(VerticalImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
