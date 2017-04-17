package com.sourcod.wechat.controller;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.sourcod.wechat.model.MessageModel;
import com.sourcod.wechat.service.ExpressService;
import com.sourcod.wechat.service.WechatService;
import com.sourcod.wechat.util.ConfigUtil;
import com.sourcod.wechat.util.EncryptionUtil;
import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.Tool12306Util;

/**
 * 火车Controller
 * 
 * @author willeam
 *
 */
@Controller
@RequestMapping("/wechat")
public class WechatController {

	private static Logger logger = LoggerFactory.getLogger(WechatController.class);

	@Autowired
	private WechatService wechatService;
	
	@Autowired
	private ExpressService expressService;

	public WechatController() {
		System.out.println("UserController");
	}

	/**
	 * 验证机器
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/index", method = { RequestMethod.GET, RequestMethod.POST })
	public String toIndex(HttpServletRequest request, HttpServletResponse response) {
		logger.info("start{}", request.getRequestURL().toString());
		// 判断是否是验证(get请求)
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String encryptType = request.getParameter("encrypt_type");
		String msgSignature = request.getParameter("msg_signature");
		String token = ConfigUtil.getValueByKey("token");
		if (isGet) {
			// 字典序排序
			ArrayList<String> list = new ArrayList<String>();
			list.add(nonce);
			list.add(timestamp);
			list.add(token);
			Collections.sort(list);
			// 获取SH1加密后字符串
			String SH1 = EncryptionUtil.SHA1(list.get(0) + list.get(1) + list.get(2));
			// 相同证明验证成功
			if (signature.equals(SH1)) {
				GeneralUtil.write(response, echostr);
			}
		} else {
			try {
				MessageModel mm = wechatService.getMessageModel(encryptType, msgSignature, timestamp, nonce, request.getReader());
				MessageModel returnMM = new MessageModel();
				
				returnMM.setToUserName(mm.getFromUserName());
				returnMM.setFromUserName(mm.getToUserName());
				returnMM.setContent(GeneralUtil.utf8ToIso88591(expressService.getExpressInfo(mm.getContent())));
				returnMM.setCreateTime(mm.getCreateTime());
				returnMM.setMsgType("text");
				String returnMsg = GeneralUtil.toXml(returnMM);
				logger.info("返回消息{}", returnMsg);
				//
				// 公众平台发送消息给第三方，第三方处理
				//
				GeneralUtil.write(response, returnMsg);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@RequestMapping(value = "getAccessToken", method = RequestMethod.GET)
	public String getAccessToken(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = wechatService.getAccessToken();
		GeneralUtil.write(response, accessToken);
		return null;
	}

	@RequestMapping(value = "createMenu", method = RequestMethod.GET)
	public String createMenu(HttpServletRequest request, HttpServletResponse response) {
		String accessToken = wechatService.createMenu();
		GeneralUtil.write(response, accessToken);
		return null;
	}

	@RequestMapping(value = "text", method = RequestMethod.GET)
	public String Text(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// int i = wechatService.selectCount();
		String imageUrl = Tool12306Util.init("openid");
		GeneralUtil.write(response, imageUrl);
		return null;
	}

}
