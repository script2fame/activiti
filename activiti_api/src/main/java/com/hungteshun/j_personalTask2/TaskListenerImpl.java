package com.hungteshun.j_personalTask2;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class TaskListenerImpl implements TaskListener {

	private static final long serialVersionUID = 1L;

	// 指定个人任务的办理人，也可以指定组任务的办理人
	// 个人任务：我们可以在这里通过调用dao去查询数据库得到下一个任务的办理人，然后通过setAssignee()的方法指定任务的办理人
	public void notify(DelegateTask delegateTask) {
		delegateTask.setAssignee("张小龙");
	}

}
