package com.hungteshun.service;

import com.hungteshun.domain.Employee;

public interface IEmployeeService {

	/**使用用户名作为查询条件，查询用户对象*/
	Employee findEmployeeByName(String name);

}
