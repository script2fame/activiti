package com.hungteshun.service;

import java.io.File;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;


public interface IWorkflowService {

	//查询部署对象信息，对应表（act_re_deployment）
	List<Deployment> findDeploymentList();

	// 查询流程定义的信息，对应表（act_re_procdef）
	List<ProcessDefinition> findProcessDefinitionList();

	//部署流程定义
	void saveNewDeploye(File file, String filename);


}
