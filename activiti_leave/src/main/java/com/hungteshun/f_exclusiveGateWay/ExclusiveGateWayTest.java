package com.hungteshun.f_exclusiveGateWay;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * 排他网关相关操作
 * 
 * @author hungteshun
 *
 */
public class ExclusiveGateWayTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** 部署流程定义（从inputStream） */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		// 注意这里的bpmn文件的路径，不是放在resource目录下的，所以要修改buildPath信息
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("exclusiveGateWay.bpmn");
		// 注意这里的png文件的路径，不是放在resource目录下的，所以要修改buildPath信息
		InputStream inputStreamPng = this.getClass().getResourceAsStream("exclusiveGateWay.png");
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("排他网关")// 添加部署的名称
				.addInputStream("exclusiveGateWay.bpmn", inputStreamBpmn)
				.addInputStream("exclusiveGateWay.png", inputStreamPng).deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
}
