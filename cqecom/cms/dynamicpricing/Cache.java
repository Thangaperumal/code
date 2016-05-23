package com.cqecom.cms.dynamicpricing;

import java.util.List;

import javax.jcr.RepositoryException;

public interface Cache {
	public List<PriceItem> get(String path) throws ItemNotExistException,
			RepositoryException;

	public void put(String path, List<PriceItem> priceItems)
			throws ItemNotExistException, RepositoryException;

	public String getProperty(String path, String propName) throws ItemNotExistException,
			RepositoryException;

	public void setProperty(String path, String propName, String propValue)
			throws ItemNotExistException, RepositoryException;
}
