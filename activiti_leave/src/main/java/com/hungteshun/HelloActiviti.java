package com.hungteshun;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.junit.Test;

/**
 * 运行activiti测试
 * @author hungteshun
 *
 */

public class HelloActiviti {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	/**部署流程定义*/
	@Test
	public void deploymentProcessDefinition(){
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("部署helloActiviti工程")//添加部署的名称
						.addClasspathResource("diagrams/helloActiviti.bpmn")//从classpath的资源中加载，一次只能加载一个文件
						.addClasspathResource("diagrams/helloActiviti.png")//从classpath的资源中加载，一次只能加载一个文件
						.deploy();//创建部署对象
		System.out.println("部署ID："+deployment.getId());//1
		System.out.println("部署名称："+deployment.getName());//部署helloActiviti工程
	}
}
