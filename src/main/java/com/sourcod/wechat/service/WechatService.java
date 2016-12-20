package com.sourcod.wechat.service;

import java.io.BufferedReader;

import com.sourcod.wechat.model.MessageModel;

/**
 * 微信service
 * @author willeam
 *
 */
public interface WechatService {

	/**
	 * 获取accessToken
	 * @author willeam
	 * @time 2016-12-14 12:53:55
	 * @return
	 */
	String getAccessToken();

	public String createMenu();

	int selectCount();

	
	int selectCountByOpenId();

	/**
	 * 获取消息对象接口
	 * @author willeam
	 * @time 2016-12-14 12:53:05
	 * @param encryptType
	 * @param msgSignature
	 * @param timestamp
	 * @param nonce
	 * @param in
	 * @return
	 */
	MessageModel getMessageModel(String encryptType, String msgSignature, String timestamp, String nonce,
			BufferedReader in);
}
