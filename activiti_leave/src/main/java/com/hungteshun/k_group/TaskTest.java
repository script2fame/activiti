package com.hungteshun.k_group;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.IdentityLink;
import org.activiti.engine.task.Task;
import org.junit.Test;

/**
 * 组任务相关操作
 * 
 * @author hungteshun
 *
 */
public class TaskTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
	
	/**部署流程定义（从inputStream）*/
	@Test
	public void deploymentProcessDefinition_inputStream(){
		InputStream inputStreamBpmn = this.getClass().getResourceAsStream("task.bpmn");
		InputStream inputStreamPng = this.getClass().getResourceAsStream("task.png");
		Deployment deployment = processEngine.getRepositoryService()//与流程定义和部署对象相关的Service
						.createDeployment()//创建一个部署对象
						.name("任务")//添加部署的名称
						.addInputStream("task.bpmn", inputStreamBpmn)//
						.addInputStream("task.png", inputStreamPng)//
						.deploy();//完成部署
		System.out.println("部署ID："+deployment.getId());//
		System.out.println("部署名称："+deployment.getName());//
	}
	
	
	/**启动流程实例--流程图中强制设定审批人*/
	//@Test
	//public void startProcessInstance(){
	//	//流程定义的key
	//	String processDefinitionKey = "task";
	//	/**启动流程实例的同时，设置流程变量，使用流程变量用来指定任务的办理人，对应task.pbmn文件中#{userIDs}*/
	//	ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
	//					.startProcessInstanceByKey(processDefinitionKey);//使用流程定义的key启动流程实例
	//	System.out.println("流程实例ID:"+pi.getId());//流程实例ID  
	//	System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   
	//}
	
	
	/**启动流程实例*/
	@Test
	public void startProcessInstance(){
		//流程定义的key
		String processDefinitionKey = "task";
		/**启动流程实例的同时，设置流程变量，使用流程变量用来指定任务的办理人，对应task.pbmn文件中#{userIDs}*/
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("userIDs", "大大,中中,小小");
		ProcessInstance pi = processEngine.getRuntimeService()//与正在执行的流程实例和执行对象相关的Service
						.startProcessInstanceByKey(processDefinitionKey,variables);
		System.out.println("流程实例ID:"+pi.getId());//流程实例ID  
		System.out.println("流程定义ID:"+pi.getProcessDefinitionId());//流程定义ID   
	}
	
	
}
