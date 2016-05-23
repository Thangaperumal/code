package com.cqecom.cms.dynamicpricing;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.RepositoryException;
import javax.jcr.Session;

import org.apache.sling.jcr.api.SlingRepository;

public class NodeCache implements Cache {

	private Node currentNode;

	private SlingRepository repository;

	public NodeCache(Node currentNode, SlingRepository repository) {
		this.currentNode = currentNode;
		this.repository = repository;
	}

	@Override
	public List<PriceItem> get(String path) throws ItemNotExistException,
			RepositoryException {
		Session session = null;
		List<PriceItem> priceItems = new ArrayList<PriceItem>();
		try {
			session = this.currentNode.getSession();
			Node node = session.getNode(path);
			if (node.hasNodes()) {
				NodeIterator iterator = node.getNodes();
				while (iterator.hasNext()) {
					Node level = iterator.nextNode();
					PriceItem priceItem = new PriceItem();
					priceItem.setLevel(level.getName());
					priceItem.setPrice(level.getProperty("price").getValue()
							.toString());
					priceItem.setTestPrice(level.getProperty("testprice")
							.getValue().toString());
					priceItem.setUpsellPrice(level.getProperty("upsellprice")
							.getValue().toString());
					priceItems.add(priceItem);
				}
			}
		} catch (PathNotFoundException exception) {
			throw new ItemNotExistException(exception);
		} catch (RepositoryException e) {
			throw e;
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		return priceItems;
	}

	@Override
	public void put(String path, List<PriceItem> priceItems)
			throws ItemNotExistException, RepositoryException {
		Session adminSession = null;
		try {
			adminSession = this.repository.loginAdministrative(null);
			StringTokenizer strTokens = new StringTokenizer(path, "\\");
			Node currentNode = adminSession.getRootNode();
			while (strTokens.hasMoreTokens()) {
				String pathElement = strTokens.nextToken();
				if (!currentNode.hasNode(pathElement))
					break;
				currentNode = currentNode.getNode(pathElement);
			}
			while (strTokens.hasMoreTokens())
				currentNode = currentNode.addNode(strTokens.nextToken());
			for (PriceItem item : priceItems) {
				Node levelNode;
				if (!currentNode.hasNode(item.getLevel()))
					levelNode = currentNode.addNode(item.getLevel());
				else
					levelNode = currentNode.getNode(item.getLevel());
				levelNode.setProperty("price", item.getPrice());
				levelNode.setProperty("testprice", item.getTestPrice());
				levelNode.setProperty("upsellprice", item.getUpsellPrice());
			}
		} catch (PathNotFoundException exception) {
			throw new ItemNotExistException(exception);
		} catch (RepositoryException e) {
			throw e;
		} finally {
			if (adminSession != null) {
				adminSession.logout();
			}
		}
	}

	@Override
	public String getProperty(String path, String propName) throws ItemNotExistException,
			RepositoryException {
		String propValue = null;
		Session session = null;
		try {
			session = this.currentNode.getSession();
			propValue = session.getNode(path).getProperty(propName).getValue().getString();					
		} catch (PathNotFoundException exception) {
			throw new ItemNotExistException(exception);
		} catch (RepositoryException e) {
			throw e;
		} finally {
			if (session != null) {
				session.logout();
			}
		}
		return propValue;
	}

	@Override
	public void setProperty(String path, String propName, String propValue)
			throws ItemNotExistException, RepositoryException {
		Session adminSession = null;
		try {
			adminSession = this.repository.loginAdministrative(null);
			adminSession.getNode(path).getProperty(propName).setValue(propValue);					
		} catch (PathNotFoundException exception) {
			throw new ItemNotExistException(exception);
		} catch (RepositoryException e) {
			throw e;
		} finally {
			if (adminSession != null) {
				adminSession.logout();
			}
		}

	}

}
