package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchFormEmailTemplateException;
import com.erp.lead.model.FormEmailTemplate;
import com.erp.lead.model.impl.FormEmailTemplateImpl;
import com.erp.lead.model.impl.FormEmailTemplateModelImpl;
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
 * The persistence implementation for the Form Email Templates service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link FormEmailTemplateUtil} to access the Form Email Templates persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see FormEmailTemplatePersistence
 * @see FormEmailTemplateUtil
 * @generated
 */
public class FormEmailTemplatePersistenceImpl extends BasePersistenceImpl<FormEmailTemplate>
    implements FormEmailTemplatePersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = FormEmailTemplateImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateModelImpl.FINDER_CACHE_ENABLED,
            FINDER_CLASS_NAME_LIST, "countAll", new String[0]);
    private static final String _SQL_SELECT_FORMEMAILTEMPLATE = "SELECT formEmailTemplate FROM FormEmailTemplate formEmailTemplate";
    private static final String _SQL_COUNT_FORMEMAILTEMPLATE = "SELECT COUNT(formEmailTemplate) FROM FormEmailTemplate formEmailTemplate";
    private static final String _ORDER_BY_ENTITY_ALIAS = "formEmailTemplate.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No FormEmailTemplate exists with the primary key ";
    private static Log _log = LogFactoryUtil.getLog(FormEmailTemplatePersistenceImpl.class);
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
     * Caches the Form Email Templates in the entity cache if it is enabled.
     *
     * @param formEmailTemplate the Form Email Templates to cache
     */
    public void cacheResult(FormEmailTemplate formEmailTemplate) {
        EntityCacheUtil.putResult(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateImpl.class, formEmailTemplate.getPrimaryKey(),
            formEmailTemplate);
    }

    /**
     * Caches the Form Email Templateses in the entity cache if it is enabled.
     *
     * @param formEmailTemplates the Form Email Templateses to cache
     */
    public void cacheResult(List<FormEmailTemplate> formEmailTemplates) {
        for (FormEmailTemplate formEmailTemplate : formEmailTemplates) {
            if (EntityCacheUtil.getResult(
                        FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
                        FormEmailTemplateImpl.class,
                        formEmailTemplate.getPrimaryKey(), this) == null) {
                cacheResult(formEmailTemplate);
            }
        }
    }

    /**
     * Clears the cache for all Form Email Templateses.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(FormEmailTemplateImpl.class.getName());
        EntityCacheUtil.clearCache(FormEmailTemplateImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Form Email Templates.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(FormEmailTemplate formEmailTemplate) {
        EntityCacheUtil.removeResult(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateImpl.class, formEmailTemplate.getPrimaryKey());
    }

    /**
     * Creates a new Form Email Templates with the primary key. Does not add the Form Email Templates to the database.
     *
     * @param id the primary key for the new Form Email Templates
     * @return the new Form Email Templates
     */
    public FormEmailTemplate create(int id) {
        FormEmailTemplate formEmailTemplate = new FormEmailTemplateImpl();

        formEmailTemplate.setNew(true);
        formEmailTemplate.setPrimaryKey(id);

        return formEmailTemplate;
    }

    /**
     * Removes the Form Email Templates with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Form Email Templates to remove
     * @return the Form Email Templates that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Form Email Templates with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Form Email Templates to remove
     * @return the Form Email Templates that was removed
     * @throws com.erp.lead.NoSuchFormEmailTemplateException if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate remove(int id)
        throws NoSuchFormEmailTemplateException, SystemException {
        Session session = null;

        try {
            session = openSession();

            FormEmailTemplate formEmailTemplate = (FormEmailTemplate) session.get(FormEmailTemplateImpl.class,
                    new Integer(id));

            if (formEmailTemplate == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchFormEmailTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(formEmailTemplate);
        } catch (NoSuchFormEmailTemplateException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected FormEmailTemplate removeImpl(FormEmailTemplate formEmailTemplate)
        throws SystemException {
        formEmailTemplate = toUnwrappedModel(formEmailTemplate);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, formEmailTemplate);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateImpl.class, formEmailTemplate.getPrimaryKey());

        return formEmailTemplate;
    }

    public FormEmailTemplate updateImpl(
        com.rosettastone.cis.model.FormEmailTemplate formEmailTemplate,
        boolean merge) throws SystemException {
        formEmailTemplate = toUnwrappedModel(formEmailTemplate);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, formEmailTemplate, merge);

            formEmailTemplate.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
            FormEmailTemplateImpl.class, formEmailTemplate.getPrimaryKey(),
            formEmailTemplate);

        return formEmailTemplate;
    }

    protected FormEmailTemplate toUnwrappedModel(
        FormEmailTemplate formEmailTemplate) {
        if (formEmailTemplate instanceof FormEmailTemplateImpl) {
            return formEmailTemplate;
        }

        FormEmailTemplateImpl formEmailTemplateImpl = new FormEmailTemplateImpl();

        formEmailTemplateImpl.setNew(formEmailTemplate.isNew());
        formEmailTemplateImpl.setPrimaryKey(formEmailTemplate.getPrimaryKey());

        formEmailTemplateImpl.setId(formEmailTemplate.getId());
        formEmailTemplateImpl.setName(formEmailTemplate.getName());
        formEmailTemplateImpl.setSubject(formEmailTemplate.getSubject());
        formEmailTemplateImpl.setBody(formEmailTemplate.getBody());
        formEmailTemplateImpl.setSendEmailAsCustomer(formEmailTemplate.getSendEmailAsCustomer());

        return formEmailTemplateImpl;
    }

    /**
     * Finds the Form Email Templates with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Form Email Templates to find
     * @return the Form Email Templates
     * @throws com.liferay.portal.NoSuchModelException if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Email Templates with the primary key or throws a {@link com.erp.lead.NoSuchFormEmailTemplateException} if it could not be found.
     *
     * @param id the primary key of the Form Email Templates to find
     * @return the Form Email Templates
     * @throws com.erp.lead.NoSuchFormEmailTemplateException if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate findByPrimaryKey(int id)
        throws NoSuchFormEmailTemplateException, SystemException {
        FormEmailTemplate formEmailTemplate = fetchByPrimaryKey(id);

        if (formEmailTemplate == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchFormEmailTemplateException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return formEmailTemplate;
    }

    /**
     * Finds the Form Email Templates with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Form Email Templates to find
     * @return the Form Email Templates, or <code>null</code> if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form Email Templates with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Form Email Templates to find
     * @return the Form Email Templates, or <code>null</code> if a Form Email Templates with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public FormEmailTemplate fetchByPrimaryKey(int id)
        throws SystemException {
        FormEmailTemplate formEmailTemplate = (FormEmailTemplate) EntityCacheUtil.getResult(FormEmailTemplateModelImpl.ENTITY_CACHE_ENABLED,
                FormEmailTemplateImpl.class, id, this);

        if (formEmailTemplate == null) {
            Session session = null;

            try {
                session = openSession();

                formEmailTemplate = (FormEmailTemplate) session.get(FormEmailTemplateImpl.class,
                        new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (formEmailTemplate != null) {
                    cacheResult(formEmailTemplate);
                }

                closeSession(session);
            }
        }

        return formEmailTemplate;
    }

    /**
     * Finds all the Form Email Templateses.
     *
     * @return the Form Email Templateses
     * @throws SystemException if a system exception occurred
     */
    public List<FormEmailTemplate> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Form Email Templateses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Email Templateses to return
     * @param end the upper bound of the range of Form Email Templateses to return (not inclusive)
     * @return the range of Form Email Templateses
     * @throws SystemException if a system exception occurred
     */
    public List<FormEmailTemplate> findAll(int start, int end)
        throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Form Email Templateses.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Form Email Templateses to return
     * @param end the upper bound of the range of Form Email Templateses to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Form Email Templateses
     * @throws SystemException if a system exception occurred
     */
    public List<FormEmailTemplate> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<FormEmailTemplate> list = (List<FormEmailTemplate>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_FORMEMAILTEMPLATE);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_FORMEMAILTEMPLATE;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<FormEmailTemplate>) QueryUtil.list(q,
                            getDialect(), start, end, false);

                    Collections.sort(list);
                } else {
                    list = (List<FormEmailTemplate>) QueryUtil.list(q,
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
     * Removes all the Form Email Templateses from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (FormEmailTemplate formEmailTemplate : findAll()) {
            remove(formEmailTemplate);
        }
    }

    /**
     * Counts all the Form Email Templateses.
     *
     * @return the number of Form Email Templateses
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

                Query q = session.createQuery(_SQL_COUNT_FORMEMAILTEMPLATE);

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
     * Initializes the Form Email Templates persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.rosettastone.cis.model.FormEmailTemplate")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<FormEmailTemplate>> listenersList = new ArrayList<ModelListener<FormEmailTemplate>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<FormEmailTemplate>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(FormEmailTemplateImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
