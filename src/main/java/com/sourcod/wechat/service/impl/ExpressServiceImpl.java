package com.sourcod.wechat.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourcod.wechat.service.ExpressService;
import com.sourcod.wechat.util.HttpClientUtil;

@Service
@Transactional
public class ExpressServiceImpl implements ExpressService{

	@Override
	public String getExpressInfo(String orderNumber) {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("number", orderNumber));
		nvps.add(new BasicNameValuePair("type", "auto"));
		Header[] Headers = {new BasicHeader("Authorization", "APPCODE 3a53ff8f11df486fac366d90722b518f")};
		HttpResponse hp = null;
		try {
			hp = HttpClientUtil.HttpGet("http://jisukdcx.market.alicloudapi.com/express/query", nvps, Headers);
			return EntityUtils.toString(hp.getEntity());
		} catch (IOException e) {
			e.printStackTrace();
			return "查询不到";
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return "查询不到";
		}
	}

	
}
