package com.hungteshun.l_groupUser;

import java.io.InputStream;
import java.util.List;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 用户角色组
 * 
 * @author hungteshun
 *
 */
public class TaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** 部署流程定义（从inputStream） */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("用户角色组测试")// 添加部署的名称
				.addInputStream("task.bpmn", inputStreamBpmn)//
				.addInputStream("task.png", inputStreamPng)//
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());//
		System.out.println("部署名称：" + deployment.getName());//
		/** 添加用户角色组 */
		IdentityService identityService = processEngine.getIdentityService();//
		// 创建角色
		identityService.saveGroup(new GroupEntity("总经理"));
		identityService.saveGroup(new GroupEntity("部门经理"));
		// 创建用户
		identityService.saveUser(new UserEntity("张三"));
		identityService.saveUser(new UserEntity("李四"));
		identityService.saveUser(new UserEntity("王五"));
		// 建立用户和角色的关联关系
		identityService.createMembership("张三", "部门经理");
		identityService.createMembership("李四", "部门经理");
		identityService.createMembership("王五", "总经理");
		System.out.println("添加组织机构成功");
	}

	/** 启动流程实例 */
	@Test
	public void startProcessInstance() {
		// 流程定义的key
		String processDefinitionKey = "task";
		ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:" + pi.getId());// 流程实例ID
		System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID
	}

	/** 查询当前人的组任务 */
	@Test
	public void findMyGroupTask() {
		// 因为.bpmn文件中指定了角色组为部门经理，所以李四可以查看组任务
		String candidateUser = "李四";
		List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
				.createTaskQuery()// 创建任务查询对象
				/** 查询条件（where部分） */
				.taskCandidateUser(candidateUser)// 组任务的办理人查询
				/** 排序 */
				.orderByTaskCreateTime().asc()// 使用创建时间的升序排列
				/** 返回结果集 */
				.list();// 返回列表
		if (list != null && list.size() > 0) {
			for (Task task : list) {
				System.out.println("任务ID:" + task.getId());
				System.out.println("任务名称:" + task.getName());
				System.out.println("任务的创建时间:" + task.getCreateTime());
				System.out.println("任务的办理人:" + task.getAssignee());
				System.out.println("流程实例ID：" + task.getProcessInstanceId());
				System.out.println("执行对象ID:" + task.getExecutionId());
				System.out.println("流程定义ID:" + task.getProcessDefinitionId());
				System.out.println("########################################################");
			}
		}
	}

	/** 查询正在执行的任务办理人表 */
	@Test
	public void findRunPersonTask() {
		// 任务ID
		String taskId = "6204";
		List<IdentityLink> list = processEngine.getTaskService()//
				.getIdentityLinksForTask(taskId);
		if (list != null && list.size() > 0) {
			for (IdentityLink identityLink : list) {
				System.out.println(identityLink.getTaskId() + "   " + identityLink.getType() + "   "
						+ identityLink.getProcessInstanceId() + "   " + identityLink.getUserId());
			}
		}
	}

	/** 查询历史任务的办理人表 */
	@Test
	public void findHistoryPersonTask() {
		// 流程实例ID
		String processInstanceId = "6201";
		List<HistoricIdentityLink> list = processEngine.getHistoryService()//
				.getHistoricIdentityLinksForProcessInstance(processInstanceId);
		if (list != null && list.size() > 0) {
			for (HistoricIdentityLink identityLink : list) {
				System.out.println(identityLink.getTaskId() + "   " + identityLink.getType() + "   "
						+ identityLink.getProcessInstanceId() + "   " + identityLink.getUserId());
			}
		}
	}

}
