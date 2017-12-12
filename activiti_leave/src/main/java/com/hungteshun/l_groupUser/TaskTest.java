package com.hungteshun.l_groupUser;

import java.io.InputStream;

import org.activiti.engine.IdentityService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.impl.persistence.entity.GroupEntity;
import org.activiti.engine.impl.persistence.entity.UserEntity;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
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
}
