package com.hungteshun.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipInputStream;

import org.activiti.engine.FormService;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.pvm.PvmTransition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Task;
import org.apache.commons.lang.StringUtils;

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

	// 通过任务id查看任务的表单
	@Override
	public String findTaskFormKeyByTaskId(String taskId) {
		TaskFormData formData = formService.getTaskFormData(taskId);
		// 获取Form key的值
		String url = formData.getFormKey();
		return url;
	}

	// 通过请假单id查找请假信息
	@Override
	public LeaveBill findLeaveBillByTaskId(String taskId) {
		// 1：使用任务ID，查询任务对象Task
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 2：使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 3：使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 4：使用流程实例对象获取BUSINESS_KEY
		String buniness_key = pi.getBusinessKey();
		// 5：获取BUSINESS_KEY对应的主键ID，使用主键ID，查询请假单对象（LeaveBill.1）
		String id = "";
		if (StringUtils.isNotBlank(buniness_key)) {
			// 截取字符串，取buniness_key小数点的第2个值
			id = buniness_key.split("\\.")[1];
		}
		// 查询请假单对象
		// 使用hql语句：from LeaveBill o where o.id=1
		LeaveBill leaveBill = leaveBillDao.findLeaveBillById(Long.parseLong(id));
		return leaveBill;
	}

	@Override
	public List<String> findOutComeListByTaskId(String taskId) {
		// 1:使用任务ID，查询任务对象
		Task task = taskService.createTaskQuery()//
				.taskId(taskId)// 使用任务ID查询
				.singleResult();
		// 使用任务对象Task获取流程实例ID
		String processInstanceId = task.getProcessInstanceId();
		// 使用流程实例ID，查询正在执行的执行对象表，返回流程实例对象
		ProcessInstance pi = runtimeService.createProcessInstanceQuery()//
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.singleResult();
		// 获取当前活动的id
		String activityId = pi.getActivityId();
		// 2：获取流程定义ID
		String processDefinitionId = task.getProcessDefinitionId();
		// 3：查询ProcessDefinitionEntiy对象
		ProcessDefinitionEntity processDefinitionEntity = (ProcessDefinitionEntity) repositoryService
				.getProcessDefinition(processDefinitionId);
		// 4：获取当前的活动
		ActivityImpl activityImpl = processDefinitionEntity.findActivity(activityId);
		// 5：获取当前活动完成之后连线的名称
		List<PvmTransition> pvmList = activityImpl.getOutgoingTransitions();
		//返回存放连线的名称集合
		List<String> list = new ArrayList<String>();
		if(pvmList!=null && pvmList.size()>0){
			for(PvmTransition pvm:pvmList){
				String name = (String) pvm.getProperty("name");
				if(StringUtils.isNotBlank(name)){
					list.add(name);
				}
				else{
					list.add("默认提交");
				}
			}
		}
		return list;
	}

	@Override
	public List<Comment> findCommentByTaskId(String taskId) {
		return null;
	}

}
