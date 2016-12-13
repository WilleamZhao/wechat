package com.sourcod.wechat.mapper;

import org.apache.ibatis.annotations.Select;

public interface KyfwMapper {

	
	int selectCount();
	
	@Select("select count(*) from kyfw_order")
	int select();
}
