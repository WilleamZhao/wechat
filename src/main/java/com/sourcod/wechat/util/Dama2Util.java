package com.sourcod.wechat.util;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.chainsaw.Main;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourcod.wechat.model.Dama2Model;

public class Dama2Util {
	
	private static final Logger logger = LoggerFactory.getLogger(Dama2Util.class);
	
	private static String key = "b887c0169e0da6fed09fb316c9793946";
	private static String username = "yjwan521";
	private static String password = "OoOo0000";
	private static String serverUrl = "http://api.dama2.com:7766/app/d2Url";
	private static String appID = "44963";
	private static String resultServerUrl = "http://api.dama2.com:7766/app/d2Result";

	public Dama2Util(String imageUrl) {
		
	}

	private static String getSign(String url) {
		return EncryptionUtil.MD5(key + username + url).substring(0, 8);
	}

	private static String getPwd() {
		return EncryptionUtil.MD5(key + EncryptionUtil.EncoderByMd5(EncryptionUtil.EncoderByMd5(username) + EncryptionUtil.EncoderByMd5(password))).substring(0, 8);
	}

	/**
	 * @author willeam
	 * @time 2016-12-07 16:25:30
	 * @param url
	 * @return	randCode
	 * @throws IOException
	 * @throws URISyntaxException
	 */
	public static String getVaildateCode(String url) throws IOException, URISyntaxException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		String sign = Dama2Util.getSign(url);
		String pwd = Dama2Util.getPwd();
		nvps.add(new BasicNameValuePair("appID", appID));
		nvps.add(new BasicNameValuePair("user", username));
		nvps.add(new BasicNameValuePair("pwd", pwd));
		nvps.add(new BasicNameValuePair("type", "287"));
		nvps.add(new BasicNameValuePair("timeout", appID));
		nvps.add(new BasicNameValuePair("sign", sign));
		nvps.add(new BasicNameValuePair("url", URLEncoder.encode(url, "utf-8")));
		HttpResponse response = HttpClientUtil.HttpsPost(serverUrl, nvps);
		if(response.getEntity().isStreaming()){
			logger.info("isNotNull");
		}
		String test = EntityUtils.toString(response.getEntity());
		logger.info(test);
		String result = GeneralUtil.getString(response.getEntity().getContent());
		String randCode = "";
		Dama2Model dm2 = GeneralUtil.StringToJson(result, Dama2Model.class);
		// TODO
		if(StringUtils.isEmpty(result)){
			nvps = new ArrayList<NameValuePair>();
			nvps.add(new BasicNameValuePair("appID", appID));
			nvps.add(new BasicNameValuePair("user", username));
			nvps.add(new BasicNameValuePair("pwd", pwd));
			nvps.add(new BasicNameValuePair("id", dm2.getId()));
			nvps.add(new BasicNameValuePair("sign", sign));
			response = HttpClientUtil.HttpsPost(resultServerUrl, nvps);
			result = GeneralUtil.getString(response.getEntity().getContent());
			dm2 = GeneralUtil.StringToJson(result, Dama2Model.class);
		}
		String[] randCodes = dm2.getReuslt().split("\\|");
		for (int j = 0; j < randCodes.length; j++) {
			// randCode += randCodes[j] + ",";
			int q = 1;
			for (String rands : randCodes[j].split(",")) {
				if (q % 2 == 0) {
					randCode += Integer.parseInt(rands) - 30 + ",";
				} else {
					randCode += rands + ",";
				}
				q++;
			}
		}
		randCode = randCode.substring(0, randCode.length() - 1);
		return randCode;
	}
	
	public static String getRandCode(HttpResponse response) throws ParseException, IOException{
		String test = EntityUtils.toString(response.getEntity());
		logger.info(test);
		String result = GeneralUtil.getString(response.getEntity().getContent());
		
		return "";
	}
	
	public static Dama2Model getDama2Model(String result) throws IOException{
		Dama2Model dm2 = GeneralUtil.StringToJson(result, Dama2Model.class);
		return dm2;
	}
	
	public static void main(String[] args) throws IOException, URISyntaxException {
		Dama2Util.getVaildateCode("");
	}

}
