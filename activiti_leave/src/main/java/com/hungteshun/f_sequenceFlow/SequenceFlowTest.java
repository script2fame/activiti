package com.hungteshun.f_sequenceFlow;

import java.io.InputStream;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

/**
 * 连线相关操作
 * 
 * @author hungteshun
 *
 */
public class SequenceFlowTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** 部署流程定义（从inputStream） */
	@Test
	public void deploymentProcessDefinition_inputStream() {
		// 注意这里的bpmn文件的路径，不是放在resource目录下的，所以要修改buildPath信息
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("sequenceFlow.bpmn");
		// 注意这里的png文件的路径，不是放在resource目录下的，所以要修改buildPath信息
		InputStream inputStreamPng = this.getClass().getResourceAsStream("sequenceFlow.png");
		Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
				.createDeployment()// 创建一个部署对象
				.name("连线")// 添加部署的名称
				.addInputStream("sequenceFlow.bpmn", inputStreamBpmn)//
				.addInputStream("sequenceFlow.png", inputStreamPng)//
				.deploy();// 完成部署
		System.out.println("部署ID：" + deployment.getId());//
		System.out.println("部署名称：" + deployment.getName());//
	}
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey = "sequenceFlow";
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例，key对应helloworld.bpmn文件中id的属性值，使用key值启动，默认是按照最新版本的流程定义启动
		System.out.println("流程实例ID:"+pi.getId());//流程实例ID    101
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   helloworld:1:4
	}
}
