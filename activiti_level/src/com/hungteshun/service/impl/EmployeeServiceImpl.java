package com.hungteshun.service.impl;

import com.hungteshun.dao.IEmployeeDao;
import com.hungteshun.domain.Employee;
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

	/**使用用户名作为查询条件，查询用户对象*/
	public Employee findEmployeeByName(String name) {
		return employeeDao.findEmployeeByName(name);
	}
}
