package com.hungteshun.service;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;

import com.hungteshun.domain.LeaveBill;
import com.hungteshun.web.form.WorkflowBean;



public interface IWorkflowService {

	//查询部署对象信息，对应表（act_re_deployment）
	List<Deployment> findDeploymentList();

	// 查询流程定义的信息，对应表（act_re_procdef）
	List<ProcessDefinition> findProcessDefinitionList();

	//部署流程定义
	void saveNewDeploye(File file, String filename);

	//查看流程图
	InputStream findImageInputStream(String deploymentId, String imageName);

	//删除部署信息
	void deleteProcessDefinitionByDeploymentId(String deploymentId);

	//查看当前用户的任务信息
	List<Task> findTaskListByName(String name);

	//启动流程
	void saveStartProcess(WorkflowBean workflowBean);

	//查看任务表单
	String findTaskFormKeyByTaskId(String taskId);

	//使用任务ID，查找请假单ID，从而获取请假单信息
	LeaveBill findLeaveBillByTaskId(String taskId);

	//获取当前任务完成之后的连线名称
	List<String> findOutComeListByTaskId(String taskId);

	//查询所有历史审核人的审核信息
	List<Comment> findCommentByTaskId(String taskId);

	//完成任务
	void saveSubmitTask(WorkflowBean workflowBean);


}
