package com.hungteshun.service.impl;

import java.util.List;

import com.hungteshun.dao.ILeaveBillDao;
import com.hungteshun.domain.LeaveBill;
import com.hungteshun.service.ILeaveBillService;
import com.hungteshun.utils.SessionContext;

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

	//保存/更新，请假申请
	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		//获取请假单ID
				Long id = leaveBill.getId();
				/**新增保存*/
				if(id==null){
					//1：从Session中获取当前用户对象，将LeaveBill对象中user与Session中获取的用户对象进行关联
					leaveBill.setUser(SessionContext.getUser());//建立管理关系
					//2：保存请假单表，添加一条数据
					leaveBillDao.saveLeaveBill(leaveBill);
				}
				/**更新保存*/
				else{
					//1：执行update的操作，完成更新
					leaveBillDao.updateLeaveBill(leaveBill);
				}
	}
}
