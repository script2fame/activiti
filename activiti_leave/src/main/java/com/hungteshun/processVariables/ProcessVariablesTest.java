package com.hungteshun.processVariables;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * 流程变量相关操作
 * @author hungteshun
 *
 */
public class ProcessVariablesTest {

ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**部署流程定义（从InputStream）*/
	@Test
	public void deploymentProcessDefinition_inputStream(){
		InputStream inputStreambpmn = this.getClass().getResourceAsStream("/diagrams/processVariables.bpmn");
		InputStream inputStreampng = this.getClass().getResourceAsStream("/diagrams/processVariables.png");
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("测试流程变量的流程定义")//添加部署的名称
						.addInputStream("processVariables.bpmn", inputStreambpmn)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
						.addInputStream("processVariables.png", inputStreampng)//使用资源文件的名称（要求：与资源文件的名称要一致），和输入流完成部署
						.deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());//
		System.out.println("部署名称："+deployment.getName());//
	}
}
