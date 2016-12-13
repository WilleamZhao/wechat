package com.sourcod.wechat.controller;

import java.io.BufferedReader;
import java.io.IOException;
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
import com.sourcod.wechat.service.KyfwService;
import com.sourcod.wechat.service.TrainService;
import com.sourcod.wechat.util.ConfigUtil;
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
	
	@Autowired
	private KyfwService kyfwService;

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
		logger.info("start{}", request.getRequestURL().toString());
		//
		boolean isGet = request.getMethod().toLowerCase().equals("get");
		String timestamp = request.getParameter("timestamp");
		String nonce = request.getParameter("nonce");
		String echostr = request.getParameter("echostr");
		String signature = request.getParameter("signature");
		String encryptType = request.getParameter("encrypt_type");
		String msgSignature = request.getParameter("msg_signature");
		String token = ConfigUtil.getValueByKey("token");
		String appId = ConfigUtil.getValueByKey("appId");
		String encodingAesKey = ConfigUtil.getValueByKey("encodingAeskey");
		logger.info("acccccccccccccccc{}", encodingAesKey);
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
				BufferedReader in = request.getReader();
				StringBuilder xmlMsg = new StringBuilder();
				String line = null;
				while ((line = in.readLine()) != null) {
					xmlMsg.append(line);
				}
				logger.debug("获取到xml{}", xmlMsg.toString());
				MessageModel mm = (MessageModel) GeneralUtil.getXml(xmlMsg.toString());
				System.out.println(GeneralUtil.toXml(mm));
				if("aes".equals(encryptType)){
					logger.info("aes");
					
				}
				String encrypt = mm.getEncrypt();

				logger.debug("获取到明文{}", encrypt);
				logger.info("json{}", GeneralUtil.BeanToString(mm));
				//
				// 公众平台发送消息给第三方，第三方处理
				//

				// 第三方收到公众号平台发送的消息
				String result2 = "123";
				WXBizMsgCrypt pc = null;
				try {
					pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
					result2 = pc.decryptMsg(msgSignature, timestamp, nonce, xmlMsg.toString());
					logger.info("解密后明文: bbbbbbbbb{}", result2);
					mm = (MessageModel) GeneralUtil.getXml(result2);
					MessageModel mm1 = new MessageModel();
					mm1.setToUserName(mm.getFromUserName());
					mm1.setFromUserName(mm.getToUserName());
					mm1.setContent("33333333333333");
					mm1.setCreateTime(mm.getCreateTime());
					mm1.setMsgType(mm.getMsgType());
					String returnMsg = GeneralUtil.toXml(mm1);
					logger.info("fanhuixiaoxi{}", returnMsg);
					result2 = pc.encryptMsg(returnMsg, timestamp, nonce);
				} catch (AesException e) {
					logger.error("cuowu", e);
				}
				logger.info("fanhuixiaoxi{}", result2);
				GeneralUtil.write(response, result2);
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
	
	@RequestMapping(value = "text", method = RequestMethod.GET)
	public String Text(HttpServletRequest request, HttpServletResponse response){
		int i = kyfwService.selectCount();
		GeneralUtil.write(response, String.valueOf(i));
		return null;
	}

}
