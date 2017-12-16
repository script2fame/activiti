package com.hungteshun.web.action;

import com.hungteshun.service.ILeaveBillService;
import com.hungteshun.service.IWorkflowService;
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

}
