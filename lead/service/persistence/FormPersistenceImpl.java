package com.erp.lead.service.persistence;

import com.erp.lead.NoSuchFormException;
import com.erp.lead.model.Form;
import com.erp.lead.model.impl.FormImpl;
import com.erp.lead.model.impl.FormModelImpl;
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
 * The persistence implementation for the Form service.
 *
 * <p>
 * Never modify or reference this class directly. Always use {@link FormUtil} to access the Form persistence. Modify <code>service.xml</code> and rerun ServiceBuilder to regenerate this class.
 * </p>
 *
 * <p>
 * Caching information and settings can be found in <code>portal.properties</code>
 * </p>
 *
 * @author Thangaperumal
 * @see FormPersistence
 * @see FormUtil
 * @generated
 */
public class FormPersistenceImpl extends BasePersistenceImpl<Form>
    implements FormPersistence {
    public static final String FINDER_CLASS_NAME_ENTITY = FormImpl.class.getName();
    public static final String FINDER_CLASS_NAME_LIST = FINDER_CLASS_NAME_ENTITY +
        ".List";
    public static final FinderPath FINDER_PATH_FIND_BY_FORMCODESITEID = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByFormCodeSiteId",
            new String[] {
                String.class.getName(), Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_FORMCODESITEID = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByFormCodeSiteId",
            new String[] { String.class.getName(), Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_FORMCODESITEIDVERTICAL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByFormCodeSiteIdVertical",
            new String[] {
                String.class.getName(), Integer.class.getName(),
                String.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_FORMCODESITEIDVERTICAL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByFormCodeSiteIdVertical",
            new String[] {
                String.class.getName(), Integer.class.getName(),
                String.class.getName()
            });
    public static final FinderPath FINDER_PATH_FIND_BY_DOVETAIL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByDovetail",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_DOVETAIL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByDovetail", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_DEMOCD = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByDemoCD",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_DEMOCD = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByDemoCD", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_AUTOREPLY = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByAutoReply",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_AUTOREPLY = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByAutoReply", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_BY_ELOQUA = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findByEloqua",
            new String[] {
                Integer.class.getName(),
                
            "java.lang.Integer", "java.lang.Integer",
                "com.liferay.portal.kernel.util.OrderByComparator"
            });
    public static final FinderPath FINDER_PATH_COUNT_BY_ELOQUA = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countByEloqua", new String[] { Integer.class.getName() });
    public static final FinderPath FINDER_PATH_FIND_ALL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "findAll", new String[0]);
    public static final FinderPath FINDER_PATH_COUNT_ALL = new FinderPath(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormModelImpl.FINDER_CACHE_ENABLED, FINDER_CLASS_NAME_LIST,
            "countAll", new String[0]);
    private static final String _SQL_SELECT_FORM = "SELECT form FROM Form form";
    private static final String _SQL_SELECT_FORM_WHERE = "SELECT form FROM Form form WHERE ";
    private static final String _SQL_COUNT_FORM = "SELECT COUNT(form) FROM Form form";
    private static final String _SQL_COUNT_FORM_WHERE = "SELECT COUNT(form) FROM Form form WHERE ";
    private static final String _FINDER_COLUMN_FORMCODESITEID_FORMCODE_1 = "form.formCode IS NULL AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEID_FORMCODE_2 = "form.formCode = ? AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEID_FORMCODE_3 = "(form.formCode IS NULL OR form.formCode = ?) AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEID_SITEID_2 = "form.siteId = ?";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_1 =
        "form.formCode IS NULL AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_2 =
        "form.formCode = ? AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_3 =
        "(form.formCode IS NULL OR form.formCode = ?) AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_SITEID_2 = "form.siteId = ? AND ";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_1 =
        "form.vertical IS NULL";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_2 =
        "form.vertical = ?";
    private static final String _FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_3 =
        "(form.vertical IS NULL OR form.vertical = ?)";
    private static final String _FINDER_COLUMN_DOVETAIL_DOVETAIL_2 = "form.dovetail = ?";
    private static final String _FINDER_COLUMN_DEMOCD_DEMOCD_2 = "form.demoCd = ?";
    private static final String _FINDER_COLUMN_AUTOREPLY_AUTOREPLY_2 = "form.autoReply = ?";
    private static final String _FINDER_COLUMN_ELOQUA_ELOQUA_2 = "form.eloqua = ?";
    private static final String _ORDER_BY_ENTITY_ALIAS = "form.";
    private static final String _NO_SUCH_ENTITY_WITH_PRIMARY_KEY = "No Form exists with the primary key ";
    private static final String _NO_SUCH_ENTITY_WITH_KEY = "No Form exists with the key {";
    private static Log _log = LogFactoryUtil.getLog(FormPersistenceImpl.class);
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
     * Caches the Form in the entity cache if it is enabled.
     *
     * @param form the Form to cache
     */
    public void cacheResult(Form form) {
        EntityCacheUtil.putResult(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormImpl.class, form.getPrimaryKey(), form);
    }

    /**
     * Caches the Forms in the entity cache if it is enabled.
     *
     * @param forms the Forms to cache
     */
    public void cacheResult(List<Form> forms) {
        for (Form form : forms) {
            if (EntityCacheUtil.getResult(FormModelImpl.ENTITY_CACHE_ENABLED,
                        FormImpl.class, form.getPrimaryKey(), this) == null) {
                cacheResult(form);
            }
        }
    }

    /**
     * Clears the cache for all Forms.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache() {
        CacheRegistryUtil.clear(FormImpl.class.getName());
        EntityCacheUtil.clearCache(FormImpl.class.getName());
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);
    }

    /**
     * Clears the cache for the Form.
     *
     * <p>
     * The {@link com.liferay.portal.kernel.dao.orm.EntityCache} and {@link com.liferay.portal.kernel.dao.orm.FinderCache} are both cleared by this method.
     * </p>
     */
    public void clearCache(Form form) {
        EntityCacheUtil.removeResult(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormImpl.class, form.getPrimaryKey());
    }

    /**
     * Creates a new Form with the primary key. Does not add the Form to the database.
     *
     * @param id the primary key for the new Form
     * @return the new Form
     */
    public Form create(int id) {
        Form form = new FormImpl();

        form.setNew(true);
        form.setPrimaryKey(id);

        return form;
    }

    /**
     * Removes the Form with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param primaryKey the primary key of the Form to remove
     * @return the Form that was removed
     * @throws com.liferay.portal.NoSuchModelException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form remove(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return remove(((Integer) primaryKey).intValue());
    }

    /**
     * Removes the Form with the primary key from the database. Also notifies the appropriate model listeners.
     *
     * @param id the primary key of the Form to remove
     * @return the Form that was removed
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form remove(int id) throws NoSuchFormException, SystemException {
        Session session = null;

        try {
            session = openSession();

            Form form = (Form) session.get(FormImpl.class, new Integer(id));

            if (form == null) {
                if (_log.isWarnEnabled()) {
                    _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
                }

                throw new NoSuchFormException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                    id);
            }

            return remove(form);
        } catch (NoSuchFormException nsee) {
            throw nsee;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form removeImpl(Form form) throws SystemException {
        form = toUnwrappedModel(form);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.delete(session, form);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.removeResult(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormImpl.class, form.getPrimaryKey());

        return form;
    }

    public Form updateImpl(com.erp.lead.model.Form form, boolean merge)
        throws SystemException {
        form = toUnwrappedModel(form);

        Session session = null;

        try {
            session = openSession();

            BatchSessionUtil.update(session, form, merge);

            form.setNew(false);
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }

        FinderCacheUtil.clearCache(FINDER_CLASS_NAME_LIST);

        EntityCacheUtil.putResult(FormModelImpl.ENTITY_CACHE_ENABLED,
            FormImpl.class, form.getPrimaryKey(), form);

        return form;
    }

    protected Form toUnwrappedModel(Form form) {
        if (form instanceof FormImpl) {
            return form;
        }

        FormImpl formImpl = new FormImpl();

        formImpl.setNew(form.isNew());
        formImpl.setPrimaryKey(form.getPrimaryKey());

        formImpl.setId(form.getId());
        formImpl.setSiteId(form.getSiteId());
        formImpl.setFormCode(form.getFormCode());
        formImpl.setSalesforce(form.getSalesforce());
        formImpl.setDovetail(form.getDovetail());
        formImpl.setDemoCd(form.getDemoCd());
        formImpl.setEmail(form.getEmail());
        formImpl.setCreatedAt(form.getCreatedAt());
        formImpl.setUpdatedAt(form.getUpdatedAt());
        formImpl.setVertical(form.getVertical());
        formImpl.setTrialOsub(form.getTrialOsub());
        formImpl.setFormEmailTemplateId(form.getFormEmailTemplateId());
        formImpl.setOsubCancellation(form.getOsubCancellation());
        formImpl.setBusinessOwner(form.getBusinessOwner());
        formImpl.setEloqua(form.getEloqua());
        formImpl.setTrialOsubFollowup(form.getTrialOsubFollowup());
        formImpl.setAutoReply(form.getAutoReply());
        formImpl.setPure360(form.getPure360());

        return formImpl;
    }

    /**
     * Finds the Form with the primary key or throws a {@link com.liferay.portal.NoSuchModelException} if it could not be found.
     *
     * @param primaryKey the primary key of the Form to find
     * @return the Form
     * @throws com.liferay.portal.NoSuchModelException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByPrimaryKey(Serializable primaryKey)
        throws NoSuchModelException, SystemException {
        return findByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form with the primary key or throws a {@link com.erp.lead.NoSuchFormException} if it could not be found.
     *
     * @param id the primary key of the Form to find
     * @return the Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByPrimaryKey(int id)
        throws NoSuchFormException, SystemException {
        Form form = fetchByPrimaryKey(id);

        if (form == null) {
            if (_log.isWarnEnabled()) {
                _log.warn(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY + id);
            }

            throw new NoSuchFormException(_NO_SUCH_ENTITY_WITH_PRIMARY_KEY +
                id);
        }

        return form;
    }

    /**
     * Finds the Form with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param primaryKey the primary key of the Form to find
     * @return the Form, or <code>null</code> if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form fetchByPrimaryKey(Serializable primaryKey)
        throws SystemException {
        return fetchByPrimaryKey(((Integer) primaryKey).intValue());
    }

    /**
     * Finds the Form with the primary key or returns <code>null</code> if it could not be found.
     *
     * @param id the primary key of the Form to find
     * @return the Form, or <code>null</code> if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form fetchByPrimaryKey(int id) throws SystemException {
        Form form = (Form) EntityCacheUtil.getResult(FormModelImpl.ENTITY_CACHE_ENABLED,
                FormImpl.class, id, this);

        if (form == null) {
            Session session = null;

            try {
                session = openSession();

                form = (Form) session.get(FormImpl.class, new Integer(id));
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (form != null) {
                    cacheResult(form);
                }

                closeSession(session);
            }
        }

        return form;
    }

    /**
     * Finds all the Forms where formCode = &#63; and siteId = &#63;.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteId(String formCode, int siteId)
        throws SystemException {
        return findByFormCodeSiteId(formCode, siteId, QueryUtil.ALL_POS,
            QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Forms where formCode = &#63; and siteId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteId(String formCode, int siteId,
        int start, int end) throws SystemException {
        return findByFormCodeSiteId(formCode, siteId, start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms where formCode = &#63; and siteId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteId(String formCode, int siteId,
        int start, int end, OrderByComparator orderByComparator)
        throws SystemException {
        Object[] finderArgs = new Object[] {
                formCode, siteId,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMCODESITEID,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(4 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(3);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            if (formCode == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_1);
            } else {
                if (formCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_2);
                }
            }

            query.append(_FINDER_COLUMN_FORMCODESITEID_SITEID_2);

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

                if (formCode != null) {
                    qPos.add(formCode);
                }

                qPos.add(siteId);

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FORMCODESITEID,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMCODESITEID,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where formCode = &#63; and siteId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByFormCodeSiteId_First(String formCode, int siteId,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByFormCodeSiteId(formCode, siteId, 0, 1,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formCode=");
            msg.append(formCode);

            msg.append(", siteId=");
            msg.append(siteId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where formCode = &#63; and siteId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByFormCodeSiteId_Last(String formCode, int siteId,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByFormCodeSiteId(formCode, siteId);

        List<Form> list = findByFormCodeSiteId(formCode, siteId, count - 1,
                count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(6);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formCode=");
            msg.append(formCode);

            msg.append(", siteId=");
            msg.append(siteId);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where formCode = &#63; and siteId = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByFormCodeSiteId_PrevAndNext(int id, String formCode,
        int siteId, OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByFormCodeSiteId_PrevAndNext(session, form, formCode,
                    siteId, orderByComparator, true);

            array[1] = form;

            array[2] = getByFormCodeSiteId_PrevAndNext(session, form, formCode,
                    siteId, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByFormCodeSiteId_PrevAndNext(Session session, Form form,
        String formCode, int siteId, OrderByComparator orderByComparator,
        boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        if (formCode == null) {
            query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_1);
        } else {
            if (formCode.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_3);
            } else {
                query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_2);
            }
        }

        query.append(_FINDER_COLUMN_FORMCODESITEID_SITEID_2);

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

        if (formCode != null) {
            qPos.add(formCode);
        }

        qPos.add(siteId);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteIdVertical(String formCode, int siteId,
        String vertical) throws SystemException {
        return findByFormCodeSiteIdVertical(formCode, siteId, vertical,
            QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Forms where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteIdVertical(String formCode, int siteId,
        String vertical, int start, int end) throws SystemException {
        return findByFormCodeSiteIdVertical(formCode, siteId, vertical, start,
            end, null);
    }

    /**
     * Finds an ordered range of all the Forms where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByFormCodeSiteIdVertical(String formCode, int siteId,
        String vertical, int start, int end, OrderByComparator orderByComparator)
        throws SystemException {
        Object[] finderArgs = new Object[] {
                formCode, siteId, vertical,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_FORMCODESITEIDVERTICAL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(5 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(4);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            if (formCode == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_1);
            } else {
                if (formCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_2);
                }
            }

            query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_SITEID_2);

            if (vertical == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_1);
            } else {
                if (vertical.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_2);
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

                if (formCode != null) {
                    qPos.add(formCode);
                }

                qPos.add(siteId);

                if (vertical != null) {
                    qPos.add(vertical);
                }

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_FORMCODESITEIDVERTICAL,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_FORMCODESITEIDVERTICAL,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByFormCodeSiteIdVertical_First(String formCode, int siteId,
        String vertical, OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByFormCodeSiteIdVertical(formCode, siteId,
                vertical, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(8);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formCode=");
            msg.append(formCode);

            msg.append(", siteId=");
            msg.append(siteId);

            msg.append(", vertical=");
            msg.append(vertical);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByFormCodeSiteIdVertical_Last(String formCode, int siteId,
        String vertical, OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByFormCodeSiteIdVertical(formCode, siteId, vertical);

        List<Form> list = findByFormCodeSiteIdVertical(formCode, siteId,
                vertical, count - 1, count, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(8);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("formCode=");
            msg.append(formCode);

            msg.append(", siteId=");
            msg.append(siteId);

            msg.append(", vertical=");
            msg.append(vertical);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByFormCodeSiteIdVertical_PrevAndNext(int id,
        String formCode, int siteId, String vertical,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByFormCodeSiteIdVertical_PrevAndNext(session, form,
                    formCode, siteId, vertical, orderByComparator, true);

            array[1] = form;

            array[2] = getByFormCodeSiteIdVertical_PrevAndNext(session, form,
                    formCode, siteId, vertical, orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByFormCodeSiteIdVertical_PrevAndNext(Session session,
        Form form, String formCode, int siteId, String vertical,
        OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        if (formCode == null) {
            query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_1);
        } else {
            if (formCode.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_3);
            } else {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_2);
            }
        }

        query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_SITEID_2);

        if (vertical == null) {
            query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_1);
        } else {
            if (vertical.equals(StringPool.BLANK)) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_3);
            } else {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_2);
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

        if (formCode != null) {
            qPos.add(formCode);
        }

        qPos.add(siteId);

        if (vertical != null) {
            qPos.add(vertical);
        }

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms where dovetail = &#63;.
     *
     * @param dovetail the dovetail to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDovetail(int dovetail) throws SystemException {
        return findByDovetail(dovetail, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
            null);
    }

    /**
     * Finds a range of all the Forms where dovetail = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param dovetail the dovetail to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDovetail(int dovetail, int start, int end)
        throws SystemException {
        return findByDovetail(dovetail, start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms where dovetail = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param dovetail the dovetail to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDovetail(int dovetail, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                dovetail,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DOVETAIL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            query.append(_FINDER_COLUMN_DOVETAIL_DOVETAIL_2);

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

                qPos.add(dovetail);

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DOVETAIL,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DOVETAIL,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where dovetail = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param dovetail the dovetail to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByDovetail_First(int dovetail,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByDovetail(dovetail, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("dovetail=");
            msg.append(dovetail);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where dovetail = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param dovetail the dovetail to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByDovetail_Last(int dovetail,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByDovetail(dovetail);

        List<Form> list = findByDovetail(dovetail, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("dovetail=");
            msg.append(dovetail);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where dovetail = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param dovetail the dovetail to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByDovetail_PrevAndNext(int id, int dovetail,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByDovetail_PrevAndNext(session, form, dovetail,
                    orderByComparator, true);

            array[1] = form;

            array[2] = getByDovetail_PrevAndNext(session, form, dovetail,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByDovetail_PrevAndNext(Session session, Form form,
        int dovetail, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        query.append(_FINDER_COLUMN_DOVETAIL_DOVETAIL_2);

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

        qPos.add(dovetail);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms where demoCd = &#63;.
     *
     * @param demoCd the demo cd to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDemoCD(int demoCd) throws SystemException {
        return findByDemoCD(demoCd, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Forms where demoCd = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param demoCd the demo cd to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDemoCD(int demoCd, int start, int end)
        throws SystemException {
        return findByDemoCD(demoCd, start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms where demoCd = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param demoCd the demo cd to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByDemoCD(int demoCd, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                demoCd,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_DEMOCD,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            query.append(_FINDER_COLUMN_DEMOCD_DEMOCD_2);

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

                qPos.add(demoCd);

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_DEMOCD,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_DEMOCD,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where demoCd = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param demoCd the demo cd to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByDemoCD_First(int demoCd,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByDemoCD(demoCd, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("demoCd=");
            msg.append(demoCd);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where demoCd = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param demoCd the demo cd to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByDemoCD_Last(int demoCd,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByDemoCD(demoCd);

        List<Form> list = findByDemoCD(demoCd, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("demoCd=");
            msg.append(demoCd);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where demoCd = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param demoCd the demo cd to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByDemoCD_PrevAndNext(int id, int demoCd,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByDemoCD_PrevAndNext(session, form, demoCd,
                    orderByComparator, true);

            array[1] = form;

            array[2] = getByDemoCD_PrevAndNext(session, form, demoCd,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByDemoCD_PrevAndNext(Session session, Form form,
        int demoCd, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        query.append(_FINDER_COLUMN_DEMOCD_DEMOCD_2);

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

        qPos.add(demoCd);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms where autoReply = &#63;.
     *
     * @param autoReply the auto reply to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByAutoReply(int autoReply) throws SystemException {
        return findByAutoReply(autoReply, QueryUtil.ALL_POS, QueryUtil.ALL_POS,
            null);
    }

    /**
     * Finds a range of all the Forms where autoReply = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param autoReply the auto reply to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByAutoReply(int autoReply, int start, int end)
        throws SystemException {
        return findByAutoReply(autoReply, start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms where autoReply = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param autoReply the auto reply to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByAutoReply(int autoReply, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                autoReply,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_AUTOREPLY,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            query.append(_FINDER_COLUMN_AUTOREPLY_AUTOREPLY_2);

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

                qPos.add(autoReply);

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_AUTOREPLY,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_AUTOREPLY,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where autoReply = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param autoReply the auto reply to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByAutoReply_First(int autoReply,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByAutoReply(autoReply, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("autoReply=");
            msg.append(autoReply);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where autoReply = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param autoReply the auto reply to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByAutoReply_Last(int autoReply,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByAutoReply(autoReply);

        List<Form> list = findByAutoReply(autoReply, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("autoReply=");
            msg.append(autoReply);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where autoReply = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param autoReply the auto reply to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByAutoReply_PrevAndNext(int id, int autoReply,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByAutoReply_PrevAndNext(session, form, autoReply,
                    orderByComparator, true);

            array[1] = form;

            array[2] = getByAutoReply_PrevAndNext(session, form, autoReply,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByAutoReply_PrevAndNext(Session session, Form form,
        int autoReply, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        query.append(_FINDER_COLUMN_AUTOREPLY_AUTOREPLY_2);

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

        qPos.add(autoReply);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms where eloqua = &#63;.
     *
     * @param eloqua the eloqua to search with
     * @return the matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByEloqua(int eloqua) throws SystemException {
        return findByEloqua(eloqua, QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Forms where eloqua = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param eloqua the eloqua to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByEloqua(int eloqua, int start, int end)
        throws SystemException {
        return findByEloqua(eloqua, start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms where eloqua = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param eloqua the eloqua to search with
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findByEloqua(int eloqua, int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                eloqua,
                
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_BY_ELOQUA,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;

            if (orderByComparator != null) {
                query = new StringBundler(3 +
                        (orderByComparator.getOrderByFields().length * 3));
            } else {
                query = new StringBundler(2);
            }

            query.append(_SQL_SELECT_FORM_WHERE);

            query.append(_FINDER_COLUMN_ELOQUA_ELOQUA_2);

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

                qPos.add(eloqua);

                list = (List<Form>) QueryUtil.list(q, getDialect(), start, end);
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (list == null) {
                    FinderCacheUtil.removeResult(FINDER_PATH_FIND_BY_ELOQUA,
                        finderArgs);
                } else {
                    cacheResult(list);

                    FinderCacheUtil.putResult(FINDER_PATH_FIND_BY_ELOQUA,
                        finderArgs, list);
                }

                closeSession(session);
            }
        }

        return list;
    }

    /**
     * Finds the first Form in the ordered set where eloqua = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param eloqua the eloqua to search with
     * @param orderByComparator the comparator to order the set by
     * @return the first matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByEloqua_First(int eloqua,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        List<Form> list = findByEloqua(eloqua, 0, 1, orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("eloqua=");
            msg.append(eloqua);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the last Form in the ordered set where eloqua = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param eloqua the eloqua to search with
     * @param orderByComparator the comparator to order the set by
     * @return the last matching Form
     * @throws com.erp.lead.NoSuchFormException if a matching Form could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form findByEloqua_Last(int eloqua,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        int count = countByEloqua(eloqua);

        List<Form> list = findByEloqua(eloqua, count - 1, count,
                orderByComparator);

        if (list.isEmpty()) {
            StringBundler msg = new StringBundler(4);

            msg.append(_NO_SUCH_ENTITY_WITH_KEY);

            msg.append("eloqua=");
            msg.append(eloqua);

            msg.append(StringPool.CLOSE_CURLY_BRACE);

            throw new NoSuchFormException(msg.toString());
        } else {
            return list.get(0);
        }
    }

    /**
     * Finds the Forms before and after the current Form in the ordered set where eloqua = &#63;.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param id the primary key of the current Form
     * @param eloqua the eloqua to search with
     * @param orderByComparator the comparator to order the set by
     * @return the previous, current, and next Form
     * @throws com.erp.lead.NoSuchFormException if a Form with the primary key could not be found
     * @throws SystemException if a system exception occurred
     */
    public Form[] findByEloqua_PrevAndNext(int id, int eloqua,
        OrderByComparator orderByComparator)
        throws NoSuchFormException, SystemException {
        Form form = findByPrimaryKey(id);

        Session session = null;

        try {
            session = openSession();

            Form[] array = new FormImpl[3];

            array[0] = getByEloqua_PrevAndNext(session, form, eloqua,
                    orderByComparator, true);

            array[1] = form;

            array[2] = getByEloqua_PrevAndNext(session, form, eloqua,
                    orderByComparator, false);

            return array;
        } catch (Exception e) {
            throw processException(e);
        } finally {
            closeSession(session);
        }
    }

    protected Form getByEloqua_PrevAndNext(Session session, Form form,
        int eloqua, OrderByComparator orderByComparator, boolean previous) {
        StringBundler query = null;

        if (orderByComparator != null) {
            query = new StringBundler(6 +
                    (orderByComparator.getOrderByFields().length * 6));
        } else {
            query = new StringBundler(3);
        }

        query.append(_SQL_SELECT_FORM_WHERE);

        query.append(_FINDER_COLUMN_ELOQUA_ELOQUA_2);

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

        qPos.add(eloqua);

        if (orderByComparator != null) {
            Object[] values = orderByComparator.getOrderByValues(form);

            for (Object value : values) {
                qPos.add(value);
            }
        }

        List<Form> list = q.list();

        if (list.size() == 2) {
            return list.get(1);
        } else {
            return null;
        }
    }

    /**
     * Finds all the Forms.
     *
     * @return the Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findAll() throws SystemException {
        return findAll(QueryUtil.ALL_POS, QueryUtil.ALL_POS, null);
    }

    /**
     * Finds a range of all the Forms.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @return the range of Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findAll(int start, int end) throws SystemException {
        return findAll(start, end, null);
    }

    /**
     * Finds an ordered range of all the Forms.
     *
     * <p>
     * Useful when paginating results. Returns a maximum of <code>end - start</code> instances. <code>start</code> and <code>end</code> are not primary keys, they are indexes in the result set. Thus, <code>0</code> refers to the first result in the set. Setting both <code>start</code> and <code>end</code> to {@link com.liferay.portal.kernel.dao.orm.QueryUtil#ALL_POS} will return the full result set.
     * </p>
     *
     * @param start the lower bound of the range of Forms to return
     * @param end the upper bound of the range of Forms to return (not inclusive)
     * @param orderByComparator the comparator to order the results by
     * @return the ordered range of Forms
     * @throws SystemException if a system exception occurred
     */
    public List<Form> findAll(int start, int end,
        OrderByComparator orderByComparator) throws SystemException {
        Object[] finderArgs = new Object[] {
                String.valueOf(start), String.valueOf(end),
                String.valueOf(orderByComparator)
            };

        List<Form> list = (List<Form>) FinderCacheUtil.getResult(FINDER_PATH_FIND_ALL,
                finderArgs, this);

        if (list == null) {
            StringBundler query = null;
            String sql = null;

            if (orderByComparator != null) {
                query = new StringBundler(2 +
                        (orderByComparator.getOrderByFields().length * 3));

                query.append(_SQL_SELECT_FORM);

                appendOrderByComparator(query, _ORDER_BY_ENTITY_ALIAS,
                    orderByComparator);

                sql = query.toString();
            } else {
                sql = _SQL_SELECT_FORM;
            }

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                if (orderByComparator == null) {
                    list = (List<Form>) QueryUtil.list(q, getDialect(), start,
                            end, false);

                    Collections.sort(list);
                } else {
                    list = (List<Form>) QueryUtil.list(q, getDialect(), start,
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
     * Removes all the Forms where formCode = &#63; and siteId = &#63; from the database.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByFormCodeSiteId(String formCode, int siteId)
        throws SystemException {
        for (Form form : findByFormCodeSiteId(formCode, siteId)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms where formCode = &#63; and siteId = &#63; and vertical = &#63; from the database.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByFormCodeSiteIdVertical(String formCode, int siteId,
        String vertical) throws SystemException {
        for (Form form : findByFormCodeSiteIdVertical(formCode, siteId, vertical)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms where dovetail = &#63; from the database.
     *
     * @param dovetail the dovetail to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByDovetail(int dovetail) throws SystemException {
        for (Form form : findByDovetail(dovetail)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms where demoCd = &#63; from the database.
     *
     * @param demoCd the demo cd to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByDemoCD(int demoCd) throws SystemException {
        for (Form form : findByDemoCD(demoCd)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms where autoReply = &#63; from the database.
     *
     * @param autoReply the auto reply to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByAutoReply(int autoReply) throws SystemException {
        for (Form form : findByAutoReply(autoReply)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms where eloqua = &#63; from the database.
     *
     * @param eloqua the eloqua to search with
     * @throws SystemException if a system exception occurred
     */
    public void removeByEloqua(int eloqua) throws SystemException {
        for (Form form : findByEloqua(eloqua)) {
            remove(form);
        }
    }

    /**
     * Removes all the Forms from the database.
     *
     * @throws SystemException if a system exception occurred
     */
    public void removeAll() throws SystemException {
        for (Form form : findAll()) {
            remove(form);
        }
    }

    /**
     * Counts all the Forms where formCode = &#63; and siteId = &#63;.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByFormCodeSiteId(String formCode, int siteId)
        throws SystemException {
        Object[] finderArgs = new Object[] { formCode, siteId };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMCODESITEID,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(3);

            query.append(_SQL_COUNT_FORM_WHERE);

            if (formCode == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_1);
            } else {
                if (formCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEID_FORMCODE_2);
                }
            }

            query.append(_FINDER_COLUMN_FORMCODESITEID_SITEID_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (formCode != null) {
                    qPos.add(formCode);
                }

                qPos.add(siteId);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMCODESITEID,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms where formCode = &#63; and siteId = &#63; and vertical = &#63;.
     *
     * @param formCode the form code to search with
     * @param siteId the site id to search with
     * @param vertical the vertical to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByFormCodeSiteIdVertical(String formCode, int siteId,
        String vertical) throws SystemException {
        Object[] finderArgs = new Object[] { formCode, siteId, vertical };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_FORMCODESITEIDVERTICAL,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(4);

            query.append(_SQL_COUNT_FORM_WHERE);

            if (formCode == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_1);
            } else {
                if (formCode.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_FORMCODE_2);
                }
            }

            query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_SITEID_2);

            if (vertical == null) {
                query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_1);
            } else {
                if (vertical.equals(StringPool.BLANK)) {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_3);
                } else {
                    query.append(_FINDER_COLUMN_FORMCODESITEIDVERTICAL_VERTICAL_2);
                }
            }

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                if (formCode != null) {
                    qPos.add(formCode);
                }

                qPos.add(siteId);

                if (vertical != null) {
                    qPos.add(vertical);
                }

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_FORMCODESITEIDVERTICAL,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms where dovetail = &#63;.
     *
     * @param dovetail the dovetail to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByDovetail(int dovetail) throws SystemException {
        Object[] finderArgs = new Object[] { dovetail };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DOVETAIL,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORM_WHERE);

            query.append(_FINDER_COLUMN_DOVETAIL_DOVETAIL_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(dovetail);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DOVETAIL,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms where demoCd = &#63;.
     *
     * @param demoCd the demo cd to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByDemoCD(int demoCd) throws SystemException {
        Object[] finderArgs = new Object[] { demoCd };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_DEMOCD,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORM_WHERE);

            query.append(_FINDER_COLUMN_DEMOCD_DEMOCD_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(demoCd);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_DEMOCD,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms where autoReply = &#63;.
     *
     * @param autoReply the auto reply to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByAutoReply(int autoReply) throws SystemException {
        Object[] finderArgs = new Object[] { autoReply };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_AUTOREPLY,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORM_WHERE);

            query.append(_FINDER_COLUMN_AUTOREPLY_AUTOREPLY_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(autoReply);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_AUTOREPLY,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms where eloqua = &#63;.
     *
     * @param eloqua the eloqua to search with
     * @return the number of matching Forms
     * @throws SystemException if a system exception occurred
     */
    public int countByEloqua(int eloqua) throws SystemException {
        Object[] finderArgs = new Object[] { eloqua };

        Long count = (Long) FinderCacheUtil.getResult(FINDER_PATH_COUNT_BY_ELOQUA,
                finderArgs, this);

        if (count == null) {
            StringBundler query = new StringBundler(2);

            query.append(_SQL_COUNT_FORM_WHERE);

            query.append(_FINDER_COLUMN_ELOQUA_ELOQUA_2);

            String sql = query.toString();

            Session session = null;

            try {
                session = openSession();

                Query q = session.createQuery(sql);

                QueryPos qPos = QueryPos.getInstance(q);

                qPos.add(eloqua);

                count = (Long) q.uniqueResult();
            } catch (Exception e) {
                throw processException(e);
            } finally {
                if (count == null) {
                    count = Long.valueOf(0);
                }

                FinderCacheUtil.putResult(FINDER_PATH_COUNT_BY_ELOQUA,
                    finderArgs, count);

                closeSession(session);
            }
        }

        return count.intValue();
    }

    /**
     * Counts all the Forms.
     *
     * @return the number of Forms
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

                Query q = session.createQuery(_SQL_COUNT_FORM);

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
     * Initializes the Form persistence.
     */
    public void afterPropertiesSet() {
        String[] listenerClassNames = StringUtil.split(GetterUtil.getString(
                    com.liferay.util.service.ServiceProps.get(
                        "value.object.listener.com.erp.lead.model.Form")));

        if (listenerClassNames.length > 0) {
            try {
                List<ModelListener<Form>> listenersList = new ArrayList<ModelListener<Form>>();

                for (String listenerClassName : listenerClassNames) {
                    listenersList.add((ModelListener<Form>) InstanceFactory.newInstance(
                            listenerClassName));
                }

                listeners = listenersList.toArray(new ModelListener[listenersList.size()]);
            } catch (Exception e) {
                _log.error(e);
            }
        }
    }

    public void destroy() {
        EntityCacheUtil.removeCache(FormImpl.class.getName());
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_ENTITY);
        FinderCacheUtil.removeCache(FINDER_CLASS_NAME_LIST);
    }
}
