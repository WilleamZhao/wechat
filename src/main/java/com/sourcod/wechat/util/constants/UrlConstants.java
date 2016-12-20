package com.sourcod.wechat.util.constants;

public class UrlConstants {

	// baseUrl
	public final static String BASE_URL = "https://kyfw.12306.cn/otn/";
	// 登录初始化url
	public final static String LOGIN_INIT_URL = "https://kyfw.12306.cn/otn/login/init";
	// 动态js文件url
	public final static String DYNAMIC_JS_URL = "https://kyfw.12306.cn/otn/dynamicJs/";
	// 登录验证码url
	public final static String LOGIN_PASSCODE_URL = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=login&rand=sjrand";
	// 提交订单验证码url
	public final static String ORDER_PASSCODE_URL = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=passenger&rand=randp";
	// 检查验证码url
	public final static String CHECK_CODE_URL = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn";
	// 登录验证url
	public final static String LOGIN_AYSN_SUGGEST_URL = "https://kyfw.12306.cn/otn/login/loginAysnSuggest";
	// 登录url
	public final static String LOGIN_URL = "https://kyfw.12306.cn/otn/login/userLogin";
	// 初始化我的12306url
	public final static String INIT_MY_12306_URL = "https://kyfw.12306.cn/otn/index/initMy12306";
	// 初始化提交订单url
	public final static String LEFT_TICKET_INIT_URL = "https://kyfw.12306.cn/otn/leftTicket/init";
	// 余票查询url
	public final static String TICKET_QUERY_URL1 = "https://kyfw.12306.cn/otn/leftTicket/queryT";
	public final static String TICKET_QUERY_URL2 = "https://kyfw.12306.cn/otn/leftTicket/query";
	public final static String TICKET_QUERY_URL3 = "https://kyfw.12306.cn/otn/leftTicket/queryX";
	// 查询日志url
	public final static String TICKET_QUERY_LOG_URL = "https://kyfw.12306.cn/otn/leftTicket/log";
	// 提交订单url
	public final static String SUBMIT_ORDER_URL = "https://kyfw.12306.cn/otn/leftTicket/submitOrderRequest";
	// 提交订单凭证url单程
	public final static String INITDC_URL = "https://kyfw.12306.cn/otn/confirmPassenger/initDc";
	// 提交订单凭证url往返
	public final static String INITWC_URL = "https://kyfw.12306.cn/otn/confirmPassenger/initWc";
	// 检查订单url
	public final static String CHECK_ORDER_URL = "https://kyfw.12306.cn/otn/confirmPassenger/checkOrderInfo";
	// 排队情况
	public final static String QUEUE_COUNT_URL = "https://kyfw.12306.cn/otn/confirmPassenger/getQueueCount";
	// 确认订单url
	public final static String CONFIRM_SINGLE_URL = "https://kyfw.12306.cn/otn/confirmPassenger/confirmSingleForQueue";
	// 排队等待时间url
	public final static String QUERY_ORDER_WAIT_URL = "https://kyfw.12306.cn/otn/confirmPassenger/queryOrderWaitTime";

	// 首页url
	public final static String INDEX_INIT_URL = "https://kyfw.12306.cn/otn/index/init";

	// 确认添加联系人url
	public static final String PASSENGERS_REALADD_URL = "https://kyfw.12306.cn/otn/passengers/realAdd";
	// 常用联系人获取所有城市
	public static final String PASSENGER_ALLCITYS_URL = "https://kyfw.12306.cn/otn/userCommon/allCitys";
	// 获取学校名称
	public static final String PASSENGERS_SCHOOLNAMES_URL = "https://kyfw.12306.cn/otn/userCommon/schoolNames";

	// 常用联系人页面url
	public static final String PASSENGERS_INIT_URL = "https://kyfw.12306.cn/otn/passengers/init";
	// 联系人url(json)
	public static final String PASSENGERS_QUERY_URL = "https://kyfw.12306.cn/otn/confirmPassenger/getPassengerDTOs";
	// 添加联系人url
	public static final String PASSENGERS_ADD_URL = "https://kyfw.12306.cn/otn/passengers/add";
	// 删除联系人url
	public static final String PASSENGER_DELETE_URL = "https://kyfw.12306.cn/otn/passengers/delete";
	// 编辑联系人url
	public static final String PASSENGER_EDIT_URL = "https://kyfw.12306.cn/otn/passengers/edit";
	// 编辑页面展示数据
	public static final String PASSENGERS_EDIT_SHOW_URL = "https://kyfw.12306.cn/otn/passengers/show";

	// 检查是否登录
	public static final String CHECK_USER_URL = "https://kyfw.12306.cn/otn/login/checkUser";

	// 查看单程队列
	public static final String RESULT_ORDER_QUEUE = "https://kyfw.12306.cn/otn/confirmPassenger/resultOrderForDcQueue";
	// 支付页面
	public static final String PAY_ORDER_URL = "https://kyfw.12306.cn/otn/payOrder/init";
	// 检查支付参数
	public static final String PAY_CHECK_NEW_URL = "https://kyfw.12306.cn/otn/payOrder/paycheckNew";

	// 登出
	public static final String LOGOUT_URL = "https://kyfw.12306.cn/otn/login/loginOut";

	// 验证用户名是否注册过
	public static final String REGIST_CHECKUSERNAME_URL = "https://kyfw.12306.cn/otn/regist/checkUserName?user_name=";
	// 获取未完成订单信息
	public static final String MY_ORDER_INFO_NO_COMPLETE_URL = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrderNoComplete";
	// 获取已完成订单信息
	public static final String MY_ORDER_INFO_URL = "https://kyfw.12306.cn/otn/queryOrder/queryMyOrder";
	// 继续支付
	public static final String CONTINUE_PAY_ORDER_URL = "https://kyfw.12306.cn/otn/queryOrder/continuePayNoCompleteMyOrder";
	// 支付页面URL
	public static final String PAYGATEWAY_URL = "https://epay.12306.cn/pay/payGateway";
	// 跳转银行URL
	public static final String WEBBUSINESS_URL = "https://epay.12306.cn/pay/webBusiness";
	// 完成支付URL
	public static final String PAYFINISH_URL = "https://kyfw.12306.cn/otn/payfinish/init";

	// 找回密码验证验证码是否正确(POST)
	public static final String FORGETPASSWORD_CHECKRANDCODE_URL = "https://kyfw.12306.cn/otn/passcodeNew/checkRandCodeAnsyn?rand=sjrand&randCode=qysx&randCode_validate=";
	// 获取验证码(GET)
	public static final String FORGETPASSWORD_GETPASSWORDNEW_URL = "https://kyfw.12306.cn/otn/passcodeNew/getPassCodeNew?module=findpassword&rand=sjrand";
	// 通过邮件找回密码(post)
	public static final String FORGETPASSWORD_FINDPASSWORDBYEMAIL_URL = "https://kyfw.12306.cn/otn/forgetPassword/findPasswordByEmail?randCode=mvqa&randCode_validate=&userDTO.email=&userDTO.loginUserDTO.id_no=&userDTO.loginUserDTO.id_type_code=1";
	// 设置新密码（post）
	public static final String FORGETPASSWORD_RESETPASSWORD_URL = "https://kyfw.12306.cn/otn//forgetPassword/resetPassWord?email=&reset_confirmPassWord=&reset_password_new=&uuId=4ea802fb-1310-46af-b0f4-9bb87722a79e";

	// 取消订单
	// CANCEL_ORDER=https://kyfw.12306.cn/otn/payOrder/cancel
	public static final String CANCEL_ORDER = "https:// kyfw.12306.cn/otn/queryOrder/cancelNoCompleteMyOrder";
}
