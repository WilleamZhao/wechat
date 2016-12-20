package com.sourcod.wechat.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.sourcod.wechat.model.AccessTokenModel;
import com.sourcod.wechat.model.JsonMsgLogin;
import com.sourcod.wechat.model.JsonMsgRandCode;
import com.sourcod.wechat.service.TrainService;
import com.sourcod.wechat.util.ConfigUtil;
import com.sourcod.wechat.util.Dama2Util;
import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.HttpClientUtil;
import com.sourcod.wechat.util.MemCached;
import com.sourcod.wechat.util.OSSUtil;
import com.sourcod.wechat.util.WechatApiUrl;
import com.sourcod.wechat.util.constants.Constants;

@Service("trainService")
public class TrainServiceImpl implements TrainService {

	private static Logger logger = Logger.getLogger(TrainServiceImpl.class);

	/**
	 * 登录12306方法
	 * 
	 * @param username
	 * @param password
	 * @throws IOException
	 */
	public void login12306(String openId, String username, String password) {
		String cookieKey = openId;
		String url = ""; //TrainServiceImpl.init12306(cookieKey);
		String randCode = "";
		try {
			randCode = Dama2Util.getVaildateCode(url);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		randCode = randCode.substring(0, randCode.length() - 1);
		System.out.println(randCode);
		boolean isLogin = false;//TrainServiceImpl.login(cookieKey, username, password, randCode);
		if (isLogin) {
			// 登录成功
		} else {
			// 登录失败
			Dama2Util.reportError(url, "");
		}
	}

	/**
	 * 初始化12306 1. 访问初始化URL 2. 获取cookie 3. 把cookie保存到memcached 4. 生成验证码并返回
	 * 
	 * @author willeam
	 * @time 2016-12-07 10:24:14
	 * @param cookieKey
	 *            cookieKey
	 * @return 验证码地址
	 */
	public String init12306(String cookieKey) {
		logger.info("开始初始化12306: cookieKey=" + cookieKey);
		HttpResponse get = null;
		// cookieKey非空验证
		if (StringUtils.isEmpty(cookieKey)) {
			logger.info("用户cookieKey为空");
			return "";
		}

		// 1. 访问初始化登陆URL
		try {
			get = HttpClientUtil.HttpsGet(ConfigUtil.getValueByKey("LOGIN_INIT_URL"));
		} catch (IOException e) {
			logger.error("Https请求错误", e);
			return "";
		} catch (URISyntaxException e) {
			logger.error("Https请求错误", e);
			return "";
		}

		// 2. 获取cookie
		String cookie = "";// TrainServiceImpl.getCookie(get);
		if (StringUtils.isEmpty(cookie)) {
			logger.info("获取cookie为空");
			return "";
		}
		logger.info("获取的Cookie：" + cookie);

		// 3. 把cookie保存到memcached.
		String cookieKeyid = "sourcod_cookieKey_" + cookieKey;
		MemCached.set(cookie, cookieKeyid);

		// 4. 生成验证码并返回
		logger.info("初始化12306页面成功");
		return "";
	}

	/**
	 * 获取12306 Cookie
	 * 
	 * @author willeam
	 * @time 2016-10-12 16:22:39
	 * @return cookie
	 */
	public String getCookie(HttpResponse response) {
		logger.info("--------------------------------开始获取cookie--------------------------------");
		try {
			String Cookie = "";
			Header[] hs = response.getHeaders("Set-Cookie");
			if (hs.length == 1) {
				if (StringUtils.isEmpty(hs[0].getValue())) {
					logger.info("12306没有返回Set-Cookie信息");
					return "";
				}
				Pattern pattern = Pattern.compile(".*?;");
				Matcher matcher = pattern.matcher(hs[0].getValue());
				boolean jSessionIdIsok = false;
				boolean bIGipServerotnIsok = false;
				while (matcher.find()) {
					String name = matcher.group();
					if (!jSessionIdIsok && name.indexOf("JSESSIONID") != -1) {
						Cookie += "JSESSIONID=" + name.substring(name.indexOf("JSESSIONID") + 11, name.length() - 1)
								+ "; ";
						jSessionIdIsok = true;
					}
					if (!bIGipServerotnIsok && matcher.group().indexOf("BIGipServerotn") != -1) {
						Cookie += "BIGipServerotn="
								+ name.substring(name.indexOf("BIGipServerotn") + 15, name.length() - 1) + "; ";
						bIGipServerotnIsok = true;
					}
					if (jSessionIdIsok && bIGipServerotnIsok) {
						break;
					}
				}
				if (StringUtils.isNotEmpty(Cookie)) {
					Cookie += "current_captcha_type=Z; ";
				}
				logger.info("获取的cookie:" + Cookie);
				logger.info("--------------------------------获取cookie结束--------------------------------");
			}
			return Cookie;
		} catch (Exception e) {
			logger.error("获取12306Cookie异常", e);
			return "";
		}
	}

	/**
	 * 登录12306 1. 转换验证码坐标 2. 在memcached中获取cookieKey 3. 验证验证码是否正确 4. 验证用户名，密码是否正确
	 * 5. 请求登录URL
	 * 
	 * @author zcj
	 * @time 2016-10-21 09:59:25
	 * @param cookieKey
	 *            cookieKey
	 * @param username
	 *            12306用户名
	 * @param password
	 *            12306密码
	 * @param randCode
	 *            验证码坐标
	 * @param sessionId
	 *            sessionID
	 * @param rand
	 *            rand
	 * @return
	 */
	public boolean login(String cookieKey, String username, String password, String randCode) {
		logger.info("开始登录12306");
		logger.info("传入参数cookieKey=" + cookieKey + "验证码坐标" + randCode);
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		// 转换点击坐标
		if (randCode.indexOf("null") > -1) {
			logger.info("验证码错误");
			return false;
		}

		// 用户ID非空验证
		if (StringUtils.isEmpty(cookieKey)) {
			logger.info("cookieKey为空");
			return false;
		}
		// 12306用户名非空验证
		if (StringUtils.isEmpty(username)) {
			logger.info("用户12306登录名为空");
			return false;
		}
		// 12306密码非空验证
		if (StringUtils.isEmpty(password)) {
			logger.info("用户12306密码为空");
			return false;
		}
		// 验证码坐标非空验证
		if (StringUtils.isEmpty(randCode)) {
			logger.info("验证码坐标为空");
			return false;
		}

		boolean isok = false;
		String result = "";
		HttpResponse response = null;
		// 在memcached中获取cookie
		String cookie = MemCached.get("sourcodcookieKey_" + cookieKey).toString();
		if (StringUtils.isEmpty(cookie)) {
			logger.info("cookie为空");
			return false;
		}

		// 3. 验证验证码是否正确
		logger.info("开始验证,验证码是否正确!");
		nvps.add(new BasicNameValuePair("randCode", randCode));
		nvps.add(new BasicNameValuePair("rand", "sjrand"));
		isok = false; //TrainServiceImpl.validateCodeParam(ConfigUtil.getValueByKey("CHECK_CODE_URL"), nvps, cookie);
		if (isok) {
			logger.info("验证码错误,请重新登录.");
			return false;
		}
		logger.info("验证验证码成功!");

		// 4. 验证用户名，密码是否正确
		logger.info("开始验证用户名密码");
		nvps = new ArrayList<NameValuePair>();
		nvps.add(new BasicNameValuePair(Constants.LOGIN_PARAMS_RANDCODE, randCode));
		nvps.add(new BasicNameValuePair(Constants.LOGIN_PARAMS_USERNAME, username));
		nvps.add(new BasicNameValuePair(Constants.LOGIN_PARAMS_PASSWORD, username));
		try {
			response = HttpClientUtil.HttpsPost(ConfigUtil.getValueByKey("LOGIN_AYSN_SUGGEST_URL"), nvps, cookie);
			result = EntityUtils.toString(response.getEntity());
		} catch (Exception e) {
			logger.error("验证用户名密码异常", e);
			return false;
		}
		logger.info("返回信息:" + result);
		JsonMsgLogin json = null;
		try {
			json = GeneralUtil.StringToJson(result, JsonMsgLogin.class);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		if (!"true".equals(json.getStatus()) || json.getData() == null || json.getMessages().length != 0
				|| !"Y".equals(json.getData().getLoginCheck())) {
			logger.info("验证用户名,密码失败");
			if (json.getMessages().length != 0 && "登录名不存在。".equals(json.getMessages())) {
				return false;
			}
			return false;
		}
		logger.info("验证用户名,密码验证成功");

		// 5. 请求登录URL
		logger.info("开始请求登录URL");
		try {
			HttpClientUtil.HttpsPost(ConfigUtil.getValueByKey("LOGIN_URL"), cookie);
		} catch (Exception e) {
			logger.error("请求登录URL异常", e);
			return false;
		}
		logger.info("登录成功");
		return true;
	}

	/**
	 * 验证验证码是否正确
	 * 
	 * @param url
	 * @param cookie
	 * @param param
	 * @return
	 */
	public boolean validateCodeParam(String url, List<NameValuePair> nvps, String cookie) {
		logger.info("开始验证验证码");
		try {
			HttpResponse pm = HttpClientUtil.HttpsPost(url, nvps, cookie);
			String result = null;
			result = GeneralUtil.getString(pm.getEntity().getContent());
			JsonMsgRandCode json = GeneralUtil.StringToJson(result, JsonMsgRandCode.class);
			if (json.getData() == null) {
				return false;
			}
			String msg = json.getData().getMsg();
			// 验证失败返回失败原因(FALSE,EXPIRED)
			if (!"true".equals(json.getStatus()) || !"TRUE".equals(msg)) {
				return false;
			}
			return true;
		} catch (Exception e) {
			logger.error("验证码验证异常", e);
			return false;
		}
	}

	/**
	 * 获取验证码
	 * 
	 * @author zcj
	 * @time 2016-10-18 18:16:28
	 * @param refCode
	 *            刷新代码
	 * @param cookie
	 *            cookie
	 * @return 获取验证码图片地址,图片宽高
	 */
	public Map<String, String> saveValidateCode(String refCode, String cookie) {
		Map<String, String> map = null;
		String url = null;
		if ("0".equals(refCode)) {
			url = ConfigUtil.getValueByKey("LOGIN_PASSCODE_URL");
		} else if ("1".equals(refCode)) {
			url = ConfigUtil.getValueByKey("ORDER_PASSCODE_URL");
		} else {
			logger.info("当前刷新代码不存在");
		}
		HttpResponse get = null;
		try {
			get = HttpClientUtil.HttpsGet(url, cookie);
		} catch (IOException e) {
			logger.error("", e);
		} catch (URISyntaxException e) {
			logger.error("", e);
		}
		String imageUrl = null;
		try {
			imageUrl = OSSUtil.uploadOSS("hitu-12306-validate", "", get.getEntity().getContent());
		} catch (IOException e) {
			logger.error("保存验证码失败", e);
		}
		map = new HashMap<String, String>();
		map.put("imageUrl", imageUrl);
		logger.info("验证码保存成功,地址:" + imageUrl);
		return map;
	}

}
