package com.hungteshun.processInstance;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 流程实例相关操作
 * 
 * @author hungteshun
 *
 */
public class ProcessInstanceTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** 部署流程定义（从zip） */
	@Test
	public void deploymentProcessDefinition_zip() {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream("diagrams/helloActiviti.zip");
		ZipInputStream zipInputStream = new ZipInputStream(in);
		Deployment deployment = processEngine.getRepositoryService().createDeployment().name("流程定义")
				.addZipInputStream(zipInputStream).deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}

	/** 启动流程实例 */
	@Test
	public void startProcessInstance() {
		// 流程定义的key
		String processDefinitionKey = "helloActiviti";
		ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
				.startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:" + pi.getId());// 流程实例ID
		System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID
	}

}
