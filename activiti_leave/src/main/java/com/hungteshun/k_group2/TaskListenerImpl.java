package com.hungteshun.k_group2;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void notify(DelegateTask delegateTask) {
		// 指定个人任务的办理人
		// 在这里，实际开发中的逻辑是通过类去查询数据库，获取到下一个任务的办理人，然后通过setAssignee()的方法指定个人任务的办理人
		// delegateTask.setAssignee("张学友");
		// 指定组任务的办理人：
		delegateTask.addCandidateUser("黎明");
		delegateTask.addCandidateUser("郭富城");
	}

}
