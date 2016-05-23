package com.cqecom.cms.services.pageSearch;

import javax.jcr.RepositoryException;
import javax.jcr.query.QueryManager;

public interface SessionTemplate<T> {

    T doInSession(QueryManager queryManager) throws RepositoryException;

}
