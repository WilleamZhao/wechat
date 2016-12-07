package com.sourcod.wechat.model;

import java.io.Serializable;

/**
 * accessToken
 * 
 * @author willeam
 * @time 2016-12-07 09:55:19
 *
 */
public class AccessTokenModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2657220104631086864L;

	private String access_token; // accesstoken
	private String expires_in; // 凭证有效时间，单位：秒

	public String getAccess_token() {
		return access_token;
	}

	public void setAccess_token(String access_token) {
		this.access_token = access_token;
	}

	public String getExpires_in() {
		return expires_in;
	}

	public void setExpires_in(String expires_in) {
		this.expires_in = expires_in;
	}

}