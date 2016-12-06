package com.sourcod.wechat.filter;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

public class ContextInitListener implements ServletContextListener {

	private static Logger logger = Logger.getLogger(ContextInitListener.class);
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {

		Properties props = new Properties();

		InputStream inputStream = null;

		inputStream = getClass().getResourceAsStream("/conf/wechat.properties");
		try {
			if(inputStream != null){
				props.load(inputStream);
			}

			logger.info("初始化成功");
			

			String tempPath = (String) props.get("path");

		} catch (IOException ex) {

			ex.printStackTrace();

		}

	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {

	}
}
