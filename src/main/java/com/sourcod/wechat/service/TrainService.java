package com.sourcod.wechat.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Date;
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
import com.sourcod.wechat.util.ConfigUtil;
import com.sourcod.wechat.util.EncryptionUtil;
import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.HttpClientUtil;
import com.sourcod.wechat.util.WechatApiUrl;

@Service("trainService")
public class TrainService {

	private static Logger logger = Logger.getLogger(TrainService.class);

	static String access_token;

	// @Autowired
	// private TrainDao trainDao;

	/**
	 * 获取accessToken
	 * 
	 * @author willeam
	 * @time 2016-12-07 10:19:41
	 * @return accessToken
	 */
	public String getAccessToken() {
		HttpResponse get = null;
		try {
			get = HttpClientUtil.HttpsGet(
					WechatApiUrl.ACCESS_TOKEN + "&appid=wx0c9478693a508813&secret=213a2755ddd537120f266cbbcc69a188");
			HttpEntity entity = get.getEntity();
			String httpStr = EntityUtils.toString(entity, "utf-8");
			logger.info(httpStr);
			AccessTokenModel json = GeneralUtil.StringToJson(httpStr, AccessTokenModel.class);
			access_token = json.getAccess_token();
		} catch (IOException e) {
			logger.error("获取accessToken异常", e);
		} catch (URISyntaxException e) {
			logger.error("获取accessToken异常", e);
		}
		return access_token;
	}

	/**
	 * 创建菜单
	 * 
	 * @author willeam
	 * @time 2016-12-07 10:19:30
	 * @return
	 */
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

	public static void login12306(String username, String password) throws IOException {
		String cookieKey = "";
		int count = 1;
		long countTrue = 0;
		long requestAVETime = 0;
		long requestTime = 0;
		long maxTime = 0;
		long minTime = 0;

		// String username = "yjwan521";
		// String password = "OoOo0000";
		String timeout = "30";
		String appID = "44963";
		String key = "b887c0169e0da6fed09fb316c9793946";
		TrainService ticketService = new TrainService();
		String url = TrainService.init12306(cookieKey);
		long startTime = new Date().getTime();
		String serverUrl = "http://api.dama2.com:7766/app/d2Url";
		String sign = EncryptionUtil.getSign(key + username + url);
		String pwd = EncryptionUtil.EncoderByMd5(key + EncryptionUtil.EncoderByMd5(EncryptionUtil.EncoderByMd5(username) + EncryptionUtil.EncoderByMd5(password)));
		// 310 选择图片, 42图片数字组合
		/*NameValuePair[] validateCodeParam = { new NameValuePair("appID", appID), new NameValuePair("user", username),
				new NameValuePair("pwd", pwd), new NameValuePair("type", "287"), new NameValuePair("timeout", timeout),
				new NameValuePair("url", URLEncoder.encode(url, "utf-8")), new NameValuePair("sign", sign) };
		PostMethod pm = Tool12306Util.getPostByHttpClient(serverUrl, validateCodeParam, "");
		String result = null;
		try {
			result = pm.getResponseBodyAsString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(result);
		JSONObject json = Tool12306Util.stringToJson(result);
		String id = json.get("id").toString();
		System.out.println(id);
		sign = EncryptionUtil.EncoderByMd5(key + username + id).substring(0, 8);
		String resultServerUrl = "http://api.dama2.com:7766/app/d2Result";
		NameValuePair[] validateCodeParam1 = { new NameValuePair("appID", appID), new NameValuePair("user", username),
				new NameValuePair("pwd", pwd), new NameValuePair("id", id), new NameValuePair("sign", sign) };
		pm = Tool12306Util.getPostByHttpClient(resultServerUrl, validateCodeParam1, "");
		result = pm.getResponseBodyAsString();
		System.out.println(result);*/
		// String validateCode = Tool12306Util.getValidateCode("310", url);
		// Tool12306Util.getValidateCode("310", url);

		/*String randCode = "";
		String[] randCodes = Tool12306Util.stringToJson(result).getString("result").split("\\|");
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
		}*/
		/*randCode = randCode.substring(0, randCode.length() - 1);
		System.out.println(randCode);
		ResponseData result1 = ticketService.login(userid, "dingruzheng", "nishishui302153", randCode, "sjrand");
		if ("00000".equals(result1.getReturnCode())) {
			countTrue++;
		} else {
			String reportErrorUrl = "http://api.dama2.com:7766/app/d2ReportError";
			NameValuePair[] reportErrorParam = { new NameValuePair("appID", appID), new NameValuePair("user", username),
					new NameValuePair("pwd", pwd), new NameValuePair("id", id), new NameValuePair("sign", sign) };
			pm = Tool12306Util.getPostByHttpClient(reportErrorUrl, reportErrorParam, "");
			result = pm.getResponseBodyAsString();
			System.out.println("报告结果" + result);
		}*/
		// ticketService.loginOut(userid);
		// Thread.sleep(5000);

		String qingqiushijian = "验证码平均请求时间" + requestAVETime / 10000 + "秒,最大请求时间" + maxTime / 1000 + "秒,最小请求时间"
				+ minTime / 1000;
		System.out.println(qingqiushijian);
		// 创建一个数值格式化对象
		NumberFormat numberFormat = NumberFormat.getInstance();

		// 设置精确到小数点后2位

		numberFormat.setMaximumFractionDigits(2);
		String result = numberFormat.format((float) countTrue / (float) count * 100);
		String zql = "验证码正确率统计" + result + "%";
		System.out.println(zql);
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
	public static String init12306(String cookieKey) {
		logger.info("开始初始化12306,进入登陆12306页面");
		logger.info("初始化12306传入参数: cookieKey=" + cookieKey);
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
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		if (get == null) {
			logger.info("初始化get请求错误");
			return "";
		}
		// 2. 获取cookie
		String cookie = "";
		// this.getCookie(get);

		if (StringUtils.isEmpty(cookie)) {
			logger.info("获取cookie为空");
			return "";
		}
		logger.info("获取的Cookie：" + cookie);

		// 3. 把cookie保存到memcached.
		String cookieKeyid = "hitu_cookieKey_" + cookieKey;
		// MemCached.addCookie(cookie, cookieKeyid);

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
	public static String getCookie(HttpResponse response) {
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
	
	
}
