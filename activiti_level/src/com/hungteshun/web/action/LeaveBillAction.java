package com.hungteshun.web.action;

import com.hungteshun.domain.LeaveBill;
import com.hungteshun.service.ILeaveBillService;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * 请假操作action
 * @author hungteshun
 *
 */

@SuppressWarnings("serial")
public class LeaveBillAction extends ActionSupport implements ModelDriven<LeaveBill> {

	private LeaveBill leaveBill = new LeaveBill();
	
	@Override
	public LeaveBill getModel() {
		// TODO Auto-generated method stub
		return leaveBill;
	}
	
	private ILeaveBillService leaveBillService;

	public void setLeaveBillService(ILeaveBillService leaveBillService) {
		this.leaveBillService = leaveBillService;
	}
	
}
