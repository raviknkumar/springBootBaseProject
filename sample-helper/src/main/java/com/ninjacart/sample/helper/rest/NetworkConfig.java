package com.ninjacart.sample.helper.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.InetSocketAddress;
import java.net.Proxy;


@Service
public class NetworkConfig {

	@Autowired Environment env;

	@Value("${rest.client.connectionTimeoutMillis:50000}") private int restClientConnectionTimeoutMillis;
	@Value("${rest.client.readTimeoutMillis:50000}") private int restClientReadTimeoutMillis;
    @Bean
	public RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());
		restTemplate.setRequestFactory(clientHttpRequestFactory());
		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		restTemplate.getMessageConverters().add(new ByteArrayHttpMessageConverter());
		if(Boolean.getBoolean(env.getProperty("proxy.enabled", "false"))){
			String host = env.getProperty("proxy.host", "localhost");
			Integer port = Integer.valueOf(env.getProperty("proxy.port", "3128"));
			SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
			InetSocketAddress address = new InetSocketAddress(host,port);
			Proxy proxy = new Proxy(Proxy.Type.HTTP,address);
			factory.setProxy(proxy);
			restTemplate.setRequestFactory(factory);
		}
		return restTemplate;
	}

	@Bean
	public ClientHttpRequestFactory clientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(restClientConnectionTimeoutMillis);
		factory.setReadTimeout(restClientReadTimeoutMillis);
		return factory;
	}
}
