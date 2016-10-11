package com.mumms.rest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.mumms.model.RestLoaderException;
import com.mumms.utils.DataLoaderConstants;
import com.mumms.utils.HBUtil;

@Component
@EnableAutoConfiguration
public class HBRestClient<T> {

	@Autowired
	private Environment env;

	@Value("${api.base.url.path}")
	private String baseUrl;

	private String resource;
	private Map<String, String> queryParams;
	private Map<String, String> pathParams;

	public HBRestClient() {
		this.queryParams = new HashMap<String, String>();
		this.pathParams = new HashMap<String, String>();
	}

	public void setResource(String resource) {
		this.resource = resource;
	}

	public void addPathParam(String key, String value) {
		this.pathParams.put(key, value);
	}

	public void addQueryParam(String key, String value) {
		this.queryParams.put(key, value);
	}

	public Response doPost(List<T> input) throws RestLoaderException {
		Entity<List<T>> jsonInput = Entity.json(input);
		String path = env.getProperty(resource + DataLoaderConstants.POST_URL_KEY);
		Invocation invocation = getRestBuilder(path).buildPost(jsonInput);
		return invocation.invoke(Response.class);
	}

	public Response doPut(T input) throws RestLoaderException {
		Entity<T> jsonInput = Entity.json(input);
		String path = env.getProperty(resource + DataLoaderConstants.PUT_URL_KEY);
		Invocation invocation = getRestBuilder(path).buildPut(jsonInput);
		return invocation.invoke(Response.class);
	}

	private Invocation.Builder getRestBuilder(String resourcePath) throws RestLoaderException {
		if (HBUtil.isBlank(resource)) {
			throw new RestLoaderException("Resource Path cannot be null", "resourcePath");
		}
		Client client = ClientBuilder.newClient();
		for (Map.Entry<String, String> param : pathParams.entrySet()) {
			resourcePath = resourcePath.replace(param.getKey(), param.getValue());
		}
		WebTarget webTarget = client.target(baseUrl).path(resourcePath);
		for (Map.Entry<String, String> param : queryParams.entrySet()) {
			webTarget = webTarget.queryParam(param.getKey(), param.getValue());
		}
		return webTarget.request(MediaType.APPLICATION_JSON).header("content-type", MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON, MediaType.TEXT_HTML);
	}
}