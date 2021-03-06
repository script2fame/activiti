package com.hungteshun.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.hungteshun.dao.IEmployeeDao;
import com.hungteshun.domain.Employee;
/**
 * 员工操作dao
 * @author hungteshun
 *
 */

public class EmployeeDaoImpl extends HibernateDaoSupport implements IEmployeeDao {

	/**使用用户名作为查询条件，查询用户对象*/
	public Employee findEmployeeByName(String name) {
		String hql = "from Employee o where o.name = ?";
		List<Employee> list = this.getHibernateTemplate().find(hql, name);
		Employee employee = null;
		if(list!=null && list.size()>0){
			employee = list.get(0);
		}
		return employee;
	}

}
