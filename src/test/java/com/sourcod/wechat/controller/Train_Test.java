package com.sourcod.wechat.controller;

import java.util.ArrayList;
import java.util.Collections;

import org.apache.commons.codec.digest.DigestUtils;

import com.sourcod.wechat.util.EncryptionUtil;

public class Train_Test {
	
	public static void main(String[] args) {
		String token = "wechatpub";
    	String timestamp = "aaaaaaaaccqweaaaaaaaaaa";
    	String nonce = "zbbbbbbbbbb";
    	
    	String echostr = "cccccccccccc";
    	ArrayList<String> list=new ArrayList<String>();
    	list.add(nonce);
    	list.add(timestamp);
    	list.add(token);
    	Collections.sort(list);
    	System.out.println(list.get(0)+list.get(1)+list.get(2));
    	System.out.println(DigestUtils.sha1Hex(list.get(0)+list.get(1)+list.get(2)));
    	System.out.println(EncryptionUtil.SHA1(list.get(0)+list.get(1)+list.get(2)));
	}
}
