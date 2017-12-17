package com.hungteshun.service;

import java.util.List;

import com.hungteshun.domain.LeaveBill;

public interface ILeaveBillService {

	// 查询当前用的所有请假信息
	List<LeaveBill> findLeaveBillList();

	//通过id查找请假单信息
	LeaveBill findLeaveBillById(Long id);

	//保存/更新，请假申请
	void saveLeaveBill(LeaveBill leaveBill);


}
