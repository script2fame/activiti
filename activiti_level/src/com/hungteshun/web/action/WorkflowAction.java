package com.hungteshun.web.action;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.struts2.ServletActionContext;

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
		//将查询到的信息放置在值栈中
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
	
	/**
	 * 查看流程图
	 * @throws Exception 
	 */
	public String viewImage() throws Exception{
		//1：获取页面传递的部署对象ID和资源图片名称
		//部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//资源图片名称
		String imageName = workflowBean.getImageName();
		//2：获取资源文件表（act_ge_bytearray）中资源图片输入流InputStream
		InputStream in = workflowService.findImageInputStream(deploymentId,imageName);
		//3：从response对象获取输出流
		OutputStream out = ServletActionContext.getResponse().getOutputStream();
		int b = -1;
		//4：将输入流中的数据读取出来，写到输出流中
		while((b = in.read()) > -1){
			out.write(b);
		}
		out.close();
		in.close();
		//将图写到页面上，用输出流写
		return null;
	}
	
	
	/**
	 * 删除部署信息
	 */
	public String delDeployment(){
		//1：获取部署对象ID
		String deploymentId = workflowBean.getDeploymentId();
		//2：使用部署对象ID，删除流程定义
		workflowService.deleteProcessDefinitionByDeploymentId(deploymentId);
		return "list";
	}
}
