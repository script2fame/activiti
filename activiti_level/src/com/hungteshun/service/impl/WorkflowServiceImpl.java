package com.hungteshun.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.task.Task;

import com.hungteshun.dao.ILeaveBillDao;
import com.hungteshun.domain.LeaveBill;
import com.hungteshun.service.IWorkflowService;
import com.hungteshun.utils.SessionContext;
import com.hungteshun.web.form.WorkflowBean;

/**
 * 工作流操作service
 * 
 * @author hungteshun
 *
 */
public class WorkflowServiceImpl implements IWorkflowService {

	/** 请假申请Dao */
	private ILeaveBillDao leaveBillDao;

	private RepositoryService repositoryService;

	private RuntimeService runtimeService;

	private TaskService taskService;

	private FormService formService;

	private HistoryService historyService;

	public void setLeaveBillDao(ILeaveBillDao leaveBillDao) {
		this.leaveBillDao = leaveBillDao;
	}

	public void setRepositoryService(RepositoryService repositoryService) {
		this.repositoryService = repositoryService;
	}

	public void setRuntimeService(RuntimeService runtimeService) {
		this.runtimeService = runtimeService;
	}

	public void setTaskService(TaskService taskService) {
		this.taskService = taskService;
	}

	public void setFormService(FormService formService) {
		this.formService = formService;
	}

	public void setHistoryService(HistoryService historyService) {
		this.historyService = historyService;
	}

	/** 查询部署对象信息，对应表（act_re_deployment） */
	@Override
	public List<Deployment> findDeploymentList() {
		List<Deployment> list = repositoryService.createDeploymentQuery()// 创建部署对象查询
				.orderByDeploymenTime().asc()//
				.list();
		return list;
	}

	/** 查询流程定义的信息，对应表（act_re_procdef） */
	@Override
	public List<ProcessDefinition> findProcessDefinitionList() {
		List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery()// 创建流程定义查询
				.orderByProcessDefinitionVersion().asc()//
				.list();
		return list;
	}

	// 部署流程
	@Override
	public void saveNewDeploye(File file, String filename) {
		try {
			// 2：将File类型的文件转化成ZipInputStream流
			ZipInputStream zipInputStream = new ZipInputStream(new FileInputStream(file));
			repositoryService.createDeployment()// 创建部署对象
					.name(filename)// 添加部署名称
					.addZipInputStream(zipInputStream)//
					.deploy();// 完成部署
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 通过输入流获取图片信息
	@Override
	public InputStream findImageInputStream(String deploymentId, String imageName) {
		return repositoryService.getResourceAsStream(deploymentId, imageName);
	}

	// 删除部署信息
	@Override
	public void deleteProcessDefinitionByDeploymentId(String deploymentId) {
		repositoryService.deleteDeployment(deploymentId, true);
	}

	// 查看当前用户的任务信息
	@Override
	public List<Task> findTaskListByName(String name) {
		List<Task> list = taskService.createTaskQuery()//
				.taskAssignee(name)// 指定个人任务查询
				.orderByTaskCreateTime().asc()//
				.list();
		return list;
	}

	// 更新请假状态，启动流程实例，设置流程变量指定第一个任务办理人，让启动的流程实例关联业务*/
	@Override
	public void saveStartProcess(WorkflowBean workflowBean) {
		// 1：获取请假单ID，使用请假单ID，查询请假单的对象LeaveBill
		Long id = workflowBean.getId();
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(id);
		// 2：使用快照更新请假单的请假状态从0变成1（初始录入-->审核中）
		leaveBill.setState(1);
		// 3：使用当前对象获取到流程定义的key（对象的名称就是流程定义的key）
		String key = leaveBill.getClass().getSimpleName();
		// 4：从Session中获取当前任务的办理人，使用流程变量设置初始任务的办理人
		// inputUser是流程变量的名称，
		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put("inputUser", SessionContext.getUser().getName());// 表示惟一用户
		// 5：流程实例关联业务
		// 方式一：使用流程变量设置字符串（格式：LeaveBill.id的形式）
		// 方式二：使用正在执行对象表中的一个字段BUSINESS_KEY（Activiti提供的一个字段）
		// 格式：LeaveBill.id的形式（使用流程变量）
		String objId = key + "." + id;
		variables.put("objId", objId);
		// 6：使用流程定义的key，启动流程实例，同时设置流程变量，同时向正在执行的执行对象表中的字段BUSINESS_KEY添加业务数据，同时让流程关联业务
		runtimeService.startProcessInstanceByKey(key, objId, variables);
	}

}
