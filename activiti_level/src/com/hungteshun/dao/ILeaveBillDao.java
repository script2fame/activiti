package com.hungteshun.dao;

import java.util.List;

import com.hungteshun.domain.LeaveBill;

public interface ILeaveBillDao {

	// 查询当前用的所有请假信息
	List<LeaveBill> findLeaveBillList();

	//通过id查找请假单信息
	LeaveBill findLeaveBillById(Long id);

	//保存请假信息
	void saveLeaveBill(LeaveBill leaveBill);

	//更新请假信息
	void updateLeaveBill(LeaveBill leaveBill);

	//删除请假信息
	void deleteLeaveBillById(Long id);



}
