package com.sourcod.wechat.util;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * 通用工具类
 * 
 * @author willeam
 *
 */
public class GeneralUtil {

	public static String getKeyByValue() {
		return "";
	}

	/**
	 * 打印内容到页面
	 * 
	 * @author willeam
	 * @param response
	 * @param content
	 *            内容
	 */
	public static void write(HttpServletResponse response, String content) {
		try {
			response.getWriter().write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static JSONObject StringToJson(String json) throws JSONException {
		return new JSONObject(json);
	}
}
