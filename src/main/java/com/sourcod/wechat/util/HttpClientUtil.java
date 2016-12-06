package com.sourcod.wechat.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;

/**
 * httpClient工具类
 * 
 * @author willeam
 *
 */
public class HttpClientUtil {

	public void HttpGet() {

	}

	public void HttpPost() {

	}

	public static HttpResponse HttpsGet(String url) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpGet get = new HttpGet();
		get.setURI(new URI(url));
		HttpResponse response = httpClient.execute(get);
		return response;
	}

	public static HttpResponse HttpsPost(String url, List<NameValuePair> nvps) throws IOException, URISyntaxException {
		CloseableHttpClient httpClient = HttpClientUtil.createSSLClientDefault();
		HttpPost post = new HttpPost();
		String menu = "{\"button\":[{\"type\":\"click\",\"name\":\"今日歌曲\",\"key\":\"V1001_TODAY_MUSIC\"},{\"name\":\"菜单\",\"sub_button\":[{\"type\":\"view\",\"name\":\"搜索\",\"url\":\"http://www.soso.com/\"},{\"type\":\"view\",\"name\":\"视频\",\"url\":\"http://v.qq.com/\"},{\"type\":\"click\",\"name\":\"赞一下我们\",\"key\":\"V1001_GOOD\"}]}]}";
		post.setEntity(new StringEntity(menu, "UTF-8"));
		post.setEntity(new UrlEncodedFormEntity(nvps));
		post.setURI(new URI(url));
		HttpResponse response = httpClient.execute(post);
		return response;
	}

	public static CloseableHttpClient createSSLClientDefault() {
		try {
			SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null, new TrustStrategy() {
				// 信任所有
				public boolean isTrusted(X509Certificate[] chain, String authType) throws CertificateException {
					return true;
				}
			}).build();
			SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslContext);
			return HttpClients.custom().setSSLSocketFactory(sslsf).build();
		} catch (KeyManagementException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (KeyStoreException e) {
			e.printStackTrace();
		}
		return HttpClients.createDefault();
	}

	// private static SSLConnectionSocketFactory createSSLConnSocketFactory() {
	// SSLConnectionSocketFactory sslsf = null;
	// try {
	// SSLContext sslContext = new SSLContextBuilder().loadTrustMaterial(null,
	// new TrustStrategy() {
	//
	// public boolean isTrusted(X509Certificate[] chain, String authType) throws
	// CertificateException {
	// return true;
	// }
	// }).build();
	// sslsf = new SSLConnectionSocketFactory(sslContext, new
	// X509HostnameVerifier() {
	//
	// @Override
	// public boolean verify(String arg0, SSLSession arg1) {
	// return true;
	// }
	//
	// @Override
	// public void verify(String host, SSLSocket ssl) throws IOException {
	// }
	//
	// @Override
	// public void verify(String host, X509Certificate cert) throws SSLException
	// {
	// }
	//
	// @Override
	// public void verify(String host, String[] cns, String[] subjectAlts)
	// throws SSLException {
	// }
	// });
	// } catch (GeneralSecurityException e) {
	// e.printStackTrace();
	// }
	// return sslsf;
	// }
}
