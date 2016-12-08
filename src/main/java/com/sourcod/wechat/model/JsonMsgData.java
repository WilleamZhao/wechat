package com.sourcod.wechat.model;

/**
 * Jsonxiaoxilei
 * @author willeam
 *
 */
public class JsonMsgData {

	private String result;
	private String msg;
	private String otherMsg;
	private String loginCheck;

	public void setResult(String result) {
		this.result = result;
	}

	public String getResult() {
		return result;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public String getOtherMsg() {
		return otherMsg;
	}

	public void setOtherMsg(String otherMsg) {
		this.otherMsg = otherMsg;
	}

	public String getLoginCheck() {
		return loginCheck;
	}

	public void setLoginCheck(String loginCheck) {
		this.loginCheck = loginCheck;
	}

	@Override
	public String toString() {
		return "Data [msg=" + msg + ", result=" + result + "]";
	}

}
