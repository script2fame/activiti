package com.hungteshun.dao;

import com.hungteshun.domain.Employee;

public interface IEmployeeDao {
	
	/**使用用户名作为查询条件，查询用户对象*/
	Employee findEmployeeByName(String name);


	

}
