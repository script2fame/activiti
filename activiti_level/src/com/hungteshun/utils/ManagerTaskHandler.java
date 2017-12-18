package com.hungteshun.utils;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.struts2.ServletActionContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.hungteshun.domain.Employee;
import com.hungteshun.service.IEmployeeService;


/**
 * 通过TaskListener实现类来动态的指定任务的办理人
 * @author hungteshun
 *
 */
@SuppressWarnings("serial")
public class ManagerTaskHandler implements TaskListener {

	@Override
	public void notify(DelegateTask delegateTask) {
		
		/**注意这里的懒加载异常，不能直接从session中的用户对象中获取该对象的上级领导manager对象；
		 * 我们需要重新查询当前用户，再获取当前用户对应的领导*/
		Employee employee = SessionContext.getUser();
		//当前用户
		String name = employee.getName();
		//使用当前用户名查询用户的详细信息
		//从web中获取spring容器
		WebApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(ServletActionContext.getServletContext());
		IEmployeeService employeeService = (IEmployeeService) ac.getBean("employeeService");
		Employee emp = employeeService.findEmployeeByName(name);
		//设置个人任务的办理人
		delegateTask.setAssignee(emp.getManager().getName());
	}

}
