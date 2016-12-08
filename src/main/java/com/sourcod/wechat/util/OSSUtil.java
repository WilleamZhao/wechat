package com.sourcod.wechat.util;

import java.io.InputStream;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;

import com.aliyun.oss.ClientConfiguration;
import com.aliyun.oss.OSSClient;

/**
 * 阿里云OSS工具类
 * @author willeam
 *
 */
public class OSSUtil {
	private static String endpoint = ConfigUtil.getValueByKey("ossEndpoint");
	private static String accessKeyId = ConfigUtil.getValueByKey("ossAccessKeyId");
	private static String accessKeySecret = ConfigUtil.getValueByKey("ossAccessKeySecret");
	private static int maxConnections = Integer.parseInt(ConfigUtil.getValueByKey("ossMaxConnections"));
	private static int socketTimeout = Integer.parseInt(ConfigUtil.getValueByKey("ossSocketTimeout"));
	private static int maxErrorRetry = Integer.parseInt(ConfigUtil.getValueByKey("ossMaxErrorRetry"));

	public static String uploadOSS(String BucketName, String name, InputStream is) {
		// 上传到远程图片服务器
		ClientConfiguration conf = new ClientConfiguration();
		// 设置OSSClient使用的最大连接数，默认1024
		conf.setMaxConnections(maxConnections);
		// 设置请求超时时间，默认50秒
		conf.setSocketTimeout(socketTimeout);
		// 设置失败请求重试次数，默认3次
		conf.setMaxErrorRetry(maxErrorRetry);

		// 创建OSSClient实例
		OSSClient client = new OSSClient(endpoint, accessKeyId, accessKeySecret, conf);
		// 使用访问OSS
		if (StringUtils.isEmpty(name)) {
			name = DateUtils.getDate("yyyyMMdd") + "/" + DateUtils.getDate("yyyyMMddHHmmssSSSS")
					+ new Random().nextInt(1000) + ".jpg";
		}
		// 上传
		client.putObject(BucketName, name, is);
		// 关闭client
		client.shutdown();
		return ConfigUtil.getValueByKey("oss_head_url") + name;
	}
}
