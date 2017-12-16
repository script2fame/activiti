package com.hungteshun.web.action;

import java.io.File;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;

import com.hungteshun.service.ILeaveBillService;
import com.hungteshun.service.IWorkflowService;
import com.hungteshun.utils.ValueContext;
import com.hungteshun.web.form.WorkflowBean;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 工作流操作action
 * 
 * @author hungteshun
 *
 */

@SuppressWarnings("serial")
public class WorkflowAction extends ActionSupport implements ModelDriven<WorkflowBean> {

	private WorkflowBean workflowBean = new WorkflowBean();

	@Override
	public WorkflowBean getModel() {
		return workflowBean;
	}

	private IWorkflowService workflowService;

	private ILeaveBillService leaveBillService;

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}

	public void setWorkflowService(IWorkflowService workflowService) {
		this.workflowService = workflowService;
	}

	/**
	 * 部署流程对象
	 * @return
	 */
	public String deployHome(){
		//1:查询部署对象信息，对应表（act_re_deployment）
		List<Deployment> depList = workflowService.findDeploymentList();
		//2:查询流程定义的信息，对应表（act_re_procdef）
		List<ProcessDefinition> pdList = workflowService.findProcessDefinitionList();
		//放置到上下文对象中
		ValueContext.putValueContext("depList", depList);
		ValueContext.putValueContext("pdList", pdList);
		return "deployHome";
	}
	
	/**
	 * 发布流程
	 * @return
	 */
	public String newdeploy(){
		//获取页面传递的值
		//1：获取页面上传递的zip格式的文件，格式是File类型
		File file = workflowBean.getFile();
		//文件名称
		String filename = workflowBean.getFilename();
		//完成部署
		workflowService.saveNewDeploye(file,filename);
		return "list";
	}
}
