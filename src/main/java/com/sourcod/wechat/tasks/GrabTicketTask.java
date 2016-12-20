package com.sourcod.wechat.tasks;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sourcod.wechat.util.Tool12306Util;

/**
 * 抢票任务
 * @author willeam
 *
 */
public class GrabTicketTask implements Runnable{

	private static final Logger logger = LoggerFactory.getLogger(GrabTicketTask.class);
	
	public static void main(String[] args) {
		// 创建线程池
		ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
		System.out.println(Runtime.getRuntime().availableProcessors());
		for(int i = 0; i < 1; i ++){
			scheduledThreadPool.scheduleAtFixedRate(new GrabTicketTask("test" + i),  1, 3, TimeUnit.SECONDS);
		}
	}
	
	GrabTicketTask(String a){
		
	}

	@Override
	public void run() {
		
		try {
			String imageUrl = Tool12306Util.init("openid");
			System.out.println(imageUrl);
		} catch (IOException e) {
			logger.error("错误", e);
			e.printStackTrace();
		} catch (URISyntaxException e) {
			logger.error("错误", e);
			e.printStackTrace();
			
		}
		
	}

}
