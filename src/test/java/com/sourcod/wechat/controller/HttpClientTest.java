package com.sourcod.wechat.controller;

import java.security.cert.CertificateException;

import javax.net.ssl.SSLContext;

import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.ssl.SSLContextBuilder;

public class HttpClientTest {

	public static void main(String[] args) throws Exception {
		String url = "https://kyfw.12306.cn/otn/login/init";
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		
		/*// 自定义的socket工厂类可以和指定的协议（Http、Https）联系起来，用来创建自定义的连接管理器。
		RegistryBuilder<ConnectionSocketFactory> r = RegistryBuilder.<ConnectionSocketFactory>create();
		PlainConnectionSocketFactory plainsf = PlainConnectionSocketFactory.getSocketFactory();
		// https信任所有证书
		SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
			@Override
			public boolean isTrusted(java.security.cert.X509Certificate[] chain, String authType)
					throws CertificateException {
				// TODO Auto-generated method stub
				return true;
			}
		}).build();
		SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
		r = r.register("https", sslsf);
		r = r.register("http", plainsf);
		// 连接池管理器
		PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(r.build());
		connectionManager.setMaxTotal(100);
		// cookie
		CookieStore cookieStore = new BasicCookieStore();

		HttpClientBuilder httpClientBuilder = HttpClients.custom().setConnectionManager(connectionManager)
				.setDefaultCookieStore(cookieStore);

		RequestConfig globalconfig = RequestConfig.custom().setRedirectsEnabled(true)
				.setCookieSpec(CookieSpecs.BROWSER_COMPATIBILITY).build();

		CloseableHttpClient httpClient = httpClientBuilder.setDefaultRequestConfig(globalconfig).build();*/

	}

}
