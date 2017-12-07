package com.hungteshun.j_personalTask;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * 个人任务相关操作
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
				.name("个人任务")// 添加部署的名称
				.addInputStream("task.bpmn", inputStreamBpmn)
				.addInputStream("task.png", inputStreamPng)
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
}
