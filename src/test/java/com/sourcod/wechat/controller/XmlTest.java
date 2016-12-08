package com.sourcod.wechat.controller;

import com.sourcod.wechat.model.MessageModel;
import com.thoughtworks.xstream.XStream;

public class XmlTest {

	public static void main(String[] args) {
		String xml1 = "<xml><ToUserName><![CDATA[gh_18a1f3e6210d]]></ToUserName><Encrypt><![CDATA[M6tzfbT35Wr0Y7qsoGw/hA4e+KmNKxU0etiui8NTEx0LPucELrP7wyZ7aR5xHtMh44psF36v50xNYMziAIKr/Ja8ZgwjXUxnfjOavE7FUj1I0gHKaTLdKZ6VDLGevidUp7OskoqQiYwSZtRFk/O+mliNT49Uk55Hp2U48mhhyXia1D5X0sgiU1lWf5p8fSmBbl1NBWuPs7cNq8KyrzYVw5XB24iVkXBWYmkwId/YL0yzsax48C9/tRllT5ppVzYWAeQYsc7oXVBLkVn7WRHgd93KHBB9TeTJsBAJmG1dQxcKlGHgZAp38fgaCdQKdYMJGQHEjDcXnLERmtoztmapStQO07dJGjLWJ/IzygQ8o57DSnOj8s8jNK67SryMEk9GoLj9TANuT7ufQ18Lmes2+Ba79MaWsv60k93Bj7smhd0=]]></Encrypt></xml>";
		String xml2 = "<xml><ToUserName><![CDATA[gh_18a1f3e6210d]]></ToUserName><FromUserName><![CDATA[oMQe3vz8v7CNovI2-jaKtplBnkjw]]></FromUserName><CreateTime>1481189068</CreateTime><MsgType><![CDATA[text]]></MsgType><Content><![CDATA[5]]></Content><MsgId>6361658606812117700</MsgId></xml>";
		XStream s = new XStream();
		
		
		MessageModel mm = new MessageModel();
		mm.setToUserName("<![CDATA[gh_18a1f3e6210d]]>");
		mm.setEncrypt("<![CDATA[M6tzfbT35Wr0Y7qsoGw/hA4e+KmNKxU0etiui8NTEx0LPucELrP7wyZ7aR5xHtMh44psF36v50xNYMziAIKr/Ja8ZgwjXUxnfjOavE7FUj1I0gHKaTLdKZ6VDLGevidUp7OskoqQiYwSZtRFk/O+mliNT49Uk55Hp2U48mhhyXia1D5X0sgiU1lWf5p8fSmBbl1NBWuPs7cNq8KyrzYVw5XB24iVkXBWYmkwId/YL0yzsax48C9/tRllT5ppVzYWAeQYsc7oXVBLkVn7WRHgd93KHBB9TeTJsBAJmG1dQxcKlGHgZAp38fgaCdQKdYMJGQHEjDcXnLERmtoztmapStQO07dJGjLWJ/IzygQ8o57DSnOj8s8jNK67SryMEk9GoLj9TANuT7ufQ18Lmes2+Ba79MaWsv60k93Bj7smhd0=]]>");
		s.alias("xml", MessageModel.class);
		String r = s.toXML(mm);
		System.out.println(r);
		
		MessageModel o = (MessageModel)s.fromXML(xml2);
		System.out.println(o.getToUserName());
		System.out.println(o.getEncrypt());
		System.out.println(o.getFromUserName());
	}

}
