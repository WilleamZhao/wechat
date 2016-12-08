package com.sourcod.wechat.model;

import java.io.Serializable;

/**
 * 消息Model类
 * 
 * @author willeam
 * @time 2016-12-07 09:55:19
 *
 */
public class MessageModel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2657220104631086864L;

	private String ToUserName; // 接收方帐号（收到的OpenID）
	private String FromUserName; // 开发者微信号
	private String CreateTime; // 消息创建时间 （整型）
	private String MsgType; // music
	private String Content; // 回复的消息内容
	private String MsgId; // 消息id，64位整型
	private String Format; // 语音格式，如amr，speex等
	private String Location_X; // 地理位置维度
	private String Location_Y; // 地理位置经度
	private String Scale; // 地图缩放大小
	private String Label; // 地理位置信息
	private String MediaId; // 上传多媒体文件，得到的id
	private String Title; // 标题
	private String Description; // 描述
	private String MusicURL; // 音乐链接
	private String HQMusicUrl; // 高质量音乐链接，WIFI环境优先使用该链接播放音乐
	private String ThumbMediaId; // 上传多媒体文件，得到的id
	private String ArticleCount; // 图文消息个数，限制为10条以内
	private String Articles; // 多条图文消息信息
	private String PicUrl; // 图片链接
	private String Url; // 点击图文消息跳转链接
	private String Encrypt; // 密文
	private String msg_signature;

	public String getToUserName() {
		return ToUserName;
	}

	public void setToUserName(String toUserName) {
		ToUserName = toUserName;
	}

	public String getFromUserName() {
		return FromUserName;
	}

	public void setFromUserName(String fromUserName) {
		FromUserName = fromUserName;
	}

	public String getCreateTime() {
		return CreateTime;
	}

	public void setCreateTime(String createTime) {
		CreateTime = createTime;
	}

	public String getMsgType() {
		return MsgType;
	}

	public void setMsgType(String msgType) {
		MsgType = msgType;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public String getMusicURL() {
		return MusicURL;
	}

	public void setMusicURL(String musicURL) {
		MusicURL = musicURL;
	}

	public String getHQMusicUrl() {
		return HQMusicUrl;
	}

	public void setHQMusicUrl(String hQMusicUrl) {
		HQMusicUrl = hQMusicUrl;
	}

	public String getThumbMediaId() {
		return ThumbMediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}

	public String getArticleCount() {
		return ArticleCount;
	}

	public void setArticleCount(String articleCount) {
		ArticleCount = articleCount;
	}

	public String getArticles() {
		return Articles;
	}

	public void setArticles(String articles) {
		Articles = articles;
	}

	public String getPicUrl() {
		return PicUrl;
	}

	public void setPicUrl(String picUrl) {
		PicUrl = picUrl;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getMsgId() {
		return MsgId;
	}

	public void setMsgId(String msgId) {
		MsgId = msgId;
	}

	public String getFormat() {
		return Format;
	}

	public void setFormat(String format) {
		Format = format;
	}

	public String getLocation_X() {
		return Location_X;
	}

	public void setLocation_X(String location_X) {
		Location_X = location_X;
	}

	public String getLocation_Y() {
		return Location_Y;
	}

	public void setLocation_Y(String location_Y) {
		Location_Y = location_Y;
	}

	public String getScale() {
		return Scale;
	}

	public void setScale(String scale) {
		Scale = scale;
	}

	public String getLabel() {
		return Label;
	}

	public void setLabel(String label) {
		Label = label;
	}

	public String getEncrypt() {
		return Encrypt;
	}

	public void setEncrypt(String encrypt) {
		Encrypt = encrypt;
	}

	public String getMsg_signature() {
		return msg_signature;
	}

	public void setMsg_signature(String msg_signature) {
		this.msg_signature = msg_signature;
	}

}
