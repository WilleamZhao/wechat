package com.sourcod.wechat.service.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sourcod.wechat.mapper.KyfwMapper;
import com.sourcod.wechat.service.WechatService;
import com.sourcod.wechat.model.AccessTokenModel;
import com.sourcod.wechat.model.MessageModel;
import com.sourcod.wechat.util.ConfigUtil;
import com.sourcod.wechat.util.GeneralUtil;
import com.sourcod.wechat.util.HttpClientUtil;
import com.sourcod.wechat.util.WechatApiUrl;
import com.sourcod.wechat.util.aes.AesException;
import com.sourcod.wechat.util.aes.WXBizMsgCrypt;

/**
 * 
 * @author willeam
 *
 */
@Service
@Transactional
public class WechatServiceImpl implements WechatService {

	private static Logger logger = LoggerFactory.getLogger(WechatServiceImpl.class);
	private static String token = ConfigUtil.getValueByKey("token");
	private static String appId = ConfigUtil.getValueByKey("appId");
	private static String encodingAesKey = ConfigUtil.getValueByKey("encodingAesKey");
	private static WXBizMsgCrypt pc = null;
	static String access_token;
	WechatServiceImpl() {
		try {
			pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
		} catch (AesException e) {
			e.printStackTrace();
		}
	}

	@Resource
	private KyfwMapper mapper;

	@Override
	public int selectCount() {
		return mapper.selectCount();
	}

	@Override
	public int selectCountByOpenId() {

		return 0;
	}

	// 获取消息类
	public MessageModel getMessageModel(String encryptType, String msgSignature, String timestamp, String nonce, BufferedReader in) {
		StringBuilder xmlMsg = new StringBuilder();
		String line = null;
		try {
			while ((line = in.readLine()) != null) {
				xmlMsg.append(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("获取到xml{}", xmlMsg.toString());
		MessageModel mm = (MessageModel) GeneralUtil.getXml(xmlMsg.toString());
		// 查询用户是否在表里
		// 如果不再新增
		String result = "";
		// raw不加密
		if ("aes".equals(encryptType)) {
			logger.info("aes加密");
			try {
				result = pc.decryptMsg(msgSignature, timestamp, nonce, xmlMsg.toString());
				logger.info("解密后明文:{}", result);
				mm = (MessageModel) GeneralUtil.getXml(result);
				mm.getContent();
			} catch (AesException e) {
				logger.error("错误", e);
			}
		}
		return mm;
	}
	
	/**
	 * 判断消息类型
	 * @author willeam
	 * @param mm
	 */
	public void getMessageType(MessageModel mm){
		String msgType = mm.getMsgType();
		String event = mm.getEvent();
		// 事件推送
		if("event".equals(msgType)){
			if("subscribe".equals(event)){
				
			} else if("unsubscribe".equals(event)){
				
			}
		}
		// 文本消息
		if("text".equals(msgType)){
			
		}
		// 图片消息
		if("image".equals(msgType)){
			
		}
		
		// 语音消息
		if("voice".equals(msgType)){
			
		}
		
		// 视频消息
		if("video".equals(msgType)){
			
		}
		
		// 小视频消息
		if("shortvideo".equals(msgType)){
			
		}
		
		// 地理位置消息
		if("location".equals(msgType)){
			
		}
		
		// 链接消息
		if("link".equals(msgType)){
			
		}
	}
	
	
	// 返回消息
	public MessageModel setMessageModel(){
		MessageModel mm = new MessageModel();
		return mm;
	}

	/**
	 * 获取accessToken
	 * 
	 * @author willeam
	 * @time 2016-12-07 10:19:41
	 * @return accessToken
	 */
	@Override
	public String getAccessToken() {
		HttpResponse get = null;
		try {
			get = HttpClientUtil.HttpsGet(
					WechatApiUrl.ACCESS_TOKEN + "&appid="+appId+"&secret=213a2755ddd537120f266cbbcc69a188");
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
	@Override
	public String createMenu() {
		String menu = "{ \"button\":[ { \"type\":\"click\", \"name\":\"今日歌曲\", \"key\":\"V1001_TODAY_MUSIC\" }, { \"name\":\"菜单\", \"sub_button\":[ { \"type\":\"view\",	\"name\":\"搜索\", \"url\":\"http://www.soso.com/\" }, { \"type\":\"view\", \"name\":\"视频\", \"url\":\"http://v.qq.com/\" }, { \"type\":\"click\", \"name\":\"赞一下我们\", \"key\":\"V1001_GOOD\" }] }]}";
				HttpResponse response = null;
				List<NameValuePair> nvps = new ArrayList<NameValuePair>();
				nvps.add(new BasicNameValuePair("access_token", access_token));
				try {
					response = HttpClientUtil.HttpsPost(WechatApiUrl.CREATE_MENU, nvps, menu);
					HttpEntity entity = response.getEntity();
					String httpStr = EntityUtils.toString(entity, "utf-8");
					System.out.println(httpStr);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (URISyntaxException e) {
					e.printStackTrace();
				}
				return "";
	}

}
