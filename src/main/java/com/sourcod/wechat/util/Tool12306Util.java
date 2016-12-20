package com.sourcod.wechat.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URISyntaxException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourcod.wechat.util.constants.UrlConstants;

public class Tool12306Util {

	private static final Logger logger = LoggerFactory.getLogger(Tool12306Util.class);

	/**
	 * 初始化12306登陆
	 * 
	 * @author willeam
	 * @time 2016-12-20 15:47:25
	 * @param openid
	 *            微信openid
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static String init(String openid) throws IOException, URISyntaxException {
		// 1. 访问初始化登录URL
		HttpResponse get = HttpClientUtil.HttpsGet(UrlConstants.LOGIN_INIT_URL);
		// 2. 获取cookie
		String cookie = Tool12306Util.getCookie(get);
		if (StringUtils.isEmpty(cookie)) {
			logger.info("获取cookie为空");
		}
		logger.info("获取的Cookie：" + cookie);

		// 3. 把cookie保存到memcached.
		String cookieKeyid = "sourcod_cookieKey_" + openid;
		// MemCached.add(cookieKeyid, cookie);

		// 4. 生成验证码并返回
		Tool12306Util.saveValidateCode("0", cookie);
		logger.info("初始化12306页面成功");
		return Tool12306Util.saveValidateCode("0", cookie);
	}

	/**
	 * 获取12306 Cookie
	 * 
	 * @author zcj
	 * @time 2016-10-12 16:22:39
	 * 
	 * @return cookie
	 */
	public static String getCookie(HttpResponse get) {
		logger.info("--------------------------------开始获取cookie--------------------------------");
		String Cookie = "";
		Header h = get.getFirstHeader("Set-Cookie");
		if (StringUtils.isEmpty(h.getValue())) {
			logger.info("12306没有返回Set-Cookie信息");
			return "";
		}
		Pattern pattern = Pattern.compile(".*?;");
		Matcher matcher = pattern.matcher(h.getValue());
		boolean jSessionIdIsok = false;
		boolean bIGipServerotnIsok = false;
		while (matcher.find()) {
			String name = matcher.group();
			if (!jSessionIdIsok && name.indexOf("JSESSIONID") != -1) {
				Cookie += "JSESSIONID=" + name.substring(name.indexOf("JSESSIONID") + 11, name.length() - 1) + "; ";
				jSessionIdIsok = true;
			}
			if (!bIGipServerotnIsok && matcher.group().indexOf("BIGipServerotn") != -1) {
				Cookie += "BIGipServerotn=" + name.substring(name.indexOf("BIGipServerotn") + 15, name.length() - 1)
						+ "; ";
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
		return Cookie;

	}

	/**
	 * 获取验证码
	 * 
	 * @author zcj
	 * @time 2016-12-20 15:49:44
	 * @param refCode 刷新代码
	 * @param cookie cookie
	 * @return 获取验证码图片地址
	 * @throws URISyntaxException
	 * @throws IOException
	 */
	public static String saveValidateCode(String refCode, String cookie) throws IOException, URISyntaxException {
		String url = null;
		if ("0".equals(refCode)) {
			url = UrlConstants.LOGIN_PASSCODE_URL;
		} else if ("1".equals(refCode)) {
			url = UrlConstants.ORDER_PASSCODE_URL;
		} else {
			logger.info("当前刷新代码不存在");
			return null;
		}
		HttpResponse get = HttpClientUtil.HttpsGet(url, cookie);
		InputStream in = get.getEntity().getContent();
		return OSSUtil.uploadOSS("sourcod-document", "", in);
	}

}
