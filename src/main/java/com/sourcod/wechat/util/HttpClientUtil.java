package com.sourcod.wechat.util;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.List;

import javax.net.ssl.SSLContext;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * httpClient工具类
 * 
 * @author willeam
 *
 */
public class HttpClientUtil {
	private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

	private static void setBaseHeader(HttpRequestBase http) {
		http.setHeader("Host", "kyfw.12306.cn");
		http.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 5.1; rv:34.0) Gecko/20100101 Firefox/34.0");
		http.setHeader("Accept", "*/*");
		http.setHeader("Accept-Language", "zh-cn,zh;q=0.8,en-us;q=0.5,en;q=0.3");
		http.setHeader("Accept-Encoding", "gzip, deflate");
		http.setHeader("Referer", "https://kyfw.12306.cn/");
		http.setHeader("Cookie", "tmp");
		http.setHeader("Connection", "keep-alive");
		http.setHeader("Cache-Control", "no-cache");
	}

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

	/**
	 * 
	 * @return
	 */
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

}
