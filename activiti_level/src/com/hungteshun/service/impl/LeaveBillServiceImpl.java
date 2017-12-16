package com.hungteshun.service.impl;

import com.hungteshun.dao.ILeaveBillDao;
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
}
