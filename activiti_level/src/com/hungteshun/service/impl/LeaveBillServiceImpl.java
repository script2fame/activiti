package com.hungteshun.service.impl;

import java.util.List;

import com.hungteshun.dao.ILeaveBillDao;
import com.hungteshun.domain.LeaveBill;
import com.hungteshun.service.ILeaveBillService;

/**
 * 请假单操作service
 * @author hungteshun
 *
 */
public class LeaveBillServiceImpl implements ILeaveBillService {

	private ILeaveBillDao leaveBillDao;

	public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	// 查询当前用的所有请假信息
	@Override
	public List<LeaveBill> findLeaveBillList() {
		List<LeaveBill> list = leaveBillDao.findLeaveBillList();
		return list;
	}

	//通过id查找请假单信息
	@Override
	public LeaveBill findLeaveBillById(Long id) {
		LeaveBill bill = leaveBillDao.findLeaveBillById(id);
		return bill;
	}
}
