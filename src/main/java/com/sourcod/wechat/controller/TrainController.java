package com.sourcod.wechat.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sourcod.wechat.service.TrainService;
import com.sourcod.wechat.util.EncryptionUtil;
import com.sourcod.wechat.util.GeneralUtil;

/**
 * 火车Controller
 * 
 * @author willeam
 *
 */
@Controller
@RequestMapping("/access")
public class TrainController {

	private static Logger logger = Logger.getLogger(TrainController.class);

	@Autowired
	private TrainService trainService;

	public TrainController() {
		System.out.println("UserController");
	}

	/**
	 * 验证机器
	 * 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/sign", method = { RequestMethod.GET, RequestMethod.POST })
	public String toIndex(HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURL());
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		if (isGet) {
			String token = "wechatpub";
			String timestamp = request.getParameter("timestamp");
			String nonce = request.getParameter("nonce");
			String echostr = request.getParameter("echostr");
			String signature = request.getParameter("signature");
			// 字典序排序
			ArrayList<String> list = new ArrayList<String>();
			list.add(nonce);
			list.add(timestamp);
			list.add(token);
			Collections.sort(list);
			// 获取SH1加密后字符串
			String SH1 = EncryptionUtil.SHA1(list.get(0) + list.get(1) + list.get(2));
			if (signature.equals(SH1)) {
				GeneralUtil.write(response, echostr);
			}
		} else {
			try {
				ServletInputStream in = request.getInputStream();
				StringBuilder xmlMsg = new StringBuilder();
				byte[] b = new byte[4096];
				for (int n; (n = in.read(b)) != -1;) {
					xmlMsg.append(new String(b, 0, n, "UTF-8"));
				}
				System.out.println(xmlMsg.toString());
				logger.info(xmlMsg.toString());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "getAccessToken", method = RequestMethod.GET)
	public String getAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = trainService.getAccessToken();
		GeneralUtil.write(response, accessToken);
		return null;
	}

	@RequestMapping(value = "createMenu", method = RequestMethod.GET)
	public String createMenu(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = trainService.createMenu();
		GeneralUtil.write(response, accessToken);
		return null;
	}

}
