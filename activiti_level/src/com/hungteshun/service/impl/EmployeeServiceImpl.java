package com.hungteshun.service.impl;

import com.hungteshun.dao.IEmployeeDao;
import com.hungteshun.service.IEmployeeService;
/**
 * 员工操作service
 * @author hungteshun
 *
 */
public class EmployeeServiceImpl implements IEmployeeService {
	
	private IEmployeeDao employeeDao;

	public void setEmployeeDao(IEmployeeDao employeeDao) {
		this.employeeDao = employeeDao;
	}
}
