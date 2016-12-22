package com.sourcod.wechat.service;

public interface ExpressService {

	/**
	 * 获取快递信息
	 * @param orderNumber
	 * @return
	 */
	String getExpressInfo(String orderNumber);
}
