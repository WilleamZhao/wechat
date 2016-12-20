package com.sourcod.wechat.service;

import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;

public interface TrainService {

	/**
	 * 初始化12306 
	 * 1. 访问初始化URL 
	 * 2. 获取cookie 
	 * 3. 把cookie保存到memcached 
	 * 4. 生成验证码并返回
	 * 
	 * @author willeam
	 * @time 2016-12-07 10:24:14
	 * @param openId openId
	 * @return 验证码地址
	 */
	public String init12306(String openId);

	/**
	 * 登录12306方法
	 * 
	 * @param username
	 * @param password
	 */
	public void login12306(String openId, String username, String password);


	/**
	 * 获取12306 Cookie
	 * 
	 * @author willeam
	 * @time 2016-10-12 16:22:39
	 * @return cookie
	 */
	public String getCookie(HttpResponse response);

	/**
	 * 登录12306 1. 转换验证码坐标 2. 在memcached中获取cookieKey 3. 验证验证码是否正确 4. 验证用户名，密码是否正确
	 * 5. 请求登录URL
	 * 
	 * @author zcj
	 * @time 2016-10-21 09:59:25
	 * @param openId openId
	 * @param username 12306用户名
	 * @param password 12306密码
	 * @param randCode 验证码坐标
	 * @param sessionId sessionID
	 * @param rand rand
	 * @return
	 */
	public boolean login(String openId, String username, String password, String randCode);

	/**
	 * 验证验证码是否正确
	 * 
	 * @param url
	 * @param cookie
	 * @param param
	 * @return
	 */
	public boolean validateCodeParam(String url, List<NameValuePair> nvps, String cookie);

	/**
	 * 获取验证码
	 * 
	 * @author zcj
	 * @time 2016-10-18 18:16:28
	 * @param refCode 刷新代码
	 * @param cookie cookie
	 * @return 获取验证码图片地址,图片宽高
	 */
	public Map<String, String> saveValidateCode(String refCode, String cookie);

}
