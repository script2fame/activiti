package com.hungteshun.processInstance;

import java.io.InputStream;
import java.util.zip.ZipInputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
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
		Deployment deployment = processEngine.getRepositoryService()
				.createDeployment()
				.name("流程定义")
				.addZipInputStream(zipInputStream)
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());
		System.out.println("部署名称：" + deployment.getName());
	}
	
	
	
}
