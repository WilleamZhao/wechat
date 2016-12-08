package com.sourcod.wechat.controller;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sourcod.wechat.model.MessageModel;
import com.sourcod.wechat.service.TrainService;
import com.sourcod.wechat.util.EncryptionUtil;
import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.aes.AesException;
import com.sourcod.wechat.util.aes.WXBizMsgCrypt;

/**
 * 火车Controller
 * 
 * @author willeam
 *
 */
@Controller
@RequestMapping("/wechat")
public class TrainController {

	private static Logger logger = LoggerFactory.getLogger(TrainController.class);

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
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String toIndex(HttpServletRequest request, HttpServletResponse response) {
		logger.info(request.getRequestURL().toString());
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String token = "wechatpub";
		String appId = "wxcb206180144624c9";
		String encodingAesKey = "6KhFDnsk8jWxwUEsO0yxJtXcqxXAvKFwihm3I5wrhum";
		if (isGet) {
			
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
				logger.debug("获取到xml{}", xmlMsg.toString());
				MessageModel mm = (MessageModel) GeneralUtil.getXml(xmlMsg.toString());
				String encrypt = mm.getEncrypt();

				logger.debug("获取到明文{}", encrypt);
				//
				// 公众平台发送消息给第三方，第三方处理
				//

				// 第三方收到公众号平台发送的消息
				String result2;
				try {
					WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
					result2 = pc.decryptMsg("", timestamp, nonce, encrypt);
					System.out.println("解密后明文: " + result2);
				} catch (AesException e) {
					e.printStackTrace();
				}
				
				logger.info("获取到内容" + mm.getContent());
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
