package com.sourcod.wechat.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.springframework.stereotype.Service;

import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.HttpClientUtil;
import com.sourcod.wechat.util.WechatApiUrl;

@Service("trainService")
public class TrainService {

	static String access_token;

	// @Autowired
	// private TrainDao trainDao;
	public String getAccessToken() {
		HttpResponse get = null;
		try {
			get = HttpClientUtil.HttpsGet(
					WechatApiUrl.ACCESS_TOKEN + "&appid=wx0c9478693a508813&secret=213a2755ddd537120f266cbbcc69a188");
			HttpEntity entity = get.getEntity();
			String httpStr = EntityUtils.toString(entity, "utf-8");
			JSONObject json = GeneralUtil.StringToJson(httpStr);
			System.out.println(httpStr);
			access_token = json.getString("access_token");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String createMenu() {
		String menu = "{     \"button\":[     {	          \"type\":\"click\",          \"name\":\"今日歌曲\",          \"key\":\"V1001_TODAY_MUSIC\"      },      {           \"name\":\"菜单\",           \"sub_button\":[           {	               \"type\":\"view\",               \"name\":\"搜索\",               \"url\":\"http://www.soso.com/\"            },            {               \"type\":\"view\",               \"name\":\"视频\",               \"url\":\"http://v.qq.com/\"            },            {               \"type\":\"click\",               \"name\":\"赞一下我们\",               \"key\":\"V1001_GOOD\"            }]       }] }";
		HttpResponse response = null;
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair("access_token", access_token));
		try {
			response = HttpClientUtil.HttpsPost(WechatApiUrl.CREATE_MENU, nvps);
			HttpEntity entity = response.getEntity();
			String httpStr = EntityUtils.toString(entity, "utf-8");
			System.out.println(httpStr);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
}
