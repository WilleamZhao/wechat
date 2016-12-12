package com.sourcod.wechat.dao;

import javax.annotation.Resource;

import com.sourcod.wechat.mapper.KyfwMapper;

public class TrainDao{

	@Resource
	private KyfwMapper mapper;

	public int delete(int id) {
		
		return mapper.selectCount();
	}
	

}
