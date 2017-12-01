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
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("流程定义")// 添加部署的名称
				.addZipInputStream(zipInputStream)// 指定zip格式的文件完成部署
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());//
		System.out.println("部署名称：" + deployment.getName());//
	}
	
	
}
