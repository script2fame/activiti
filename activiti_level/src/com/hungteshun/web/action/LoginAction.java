package com.hungteshun.web.action;

import com.hungteshun.domain.Employee;
import com.hungteshun.service.IEmployeeService;
import com.hungteshun.utils.SessionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 登录操作action
 * 
 * @author hungteshun
 *
 */

@SuppressWarnings("serial")
public class LoginAction extends ActionSupport implements ModelDriven<Employee> {

	private Employee employee = new Employee();

	@Override
	public Employee getModel() {
		return employee;
	}

	private IEmployeeService employeeService;

	public void setEmployeeService(IEmployeeService employeeService) {
		this.employeeService = employeeService;
	}

	/**
	 * 登录
	 * 
	 * @return
	 */
	public String login() {
		// 1：获取用户名
		String name = employee.getName();
		// 2：使用用户名作为查询条件，查询员工表，获取当前用户名对应的信息
		Employee emp = employeeService.findEmployeeByName(name);
		// 3：将查询的对象（惟一）放置到Session中
		SessionContext.setUser(emp);
		return "success";
	}

	/**
	 * 标题
	 * 
	 * @return
	 */
	public String top() {
		return "top";
	}

	/**
	 * 左侧菜单
	 * 
	 * @return
	 */
	public String left() {
		return "left";
	}

	/**
	 * 主页显示
	 * 
	 * @return
	 */
	public String welcome() {
		return "welcome";
	}

}
