package com.hungteshun.service;

import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;


public interface IWorkflowService {

	/**查询部署对象信息，对应表（act_re_deployment）*/
	List<Deployment> findDeploymentList();

	
	List<ProcessDefinition> findProcessDefinitionList();



}
