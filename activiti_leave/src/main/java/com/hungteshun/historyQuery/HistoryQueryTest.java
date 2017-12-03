package com.hungteshun.historyQuery;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricProcessInstance;
import org.junit.Test;

/**
 * 历史记录相关操作
 * 
 * @author hungteshun
 *
 */
public class HistoryQueryTest {

	ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

	/** 查询历史流程实例 */
	@Test
	public void findHistoryProcessInstance() {
		String processInstanceId = "401";
		HistoricProcessInstance hpi = processEngine.getHistoryService()// 与历史数据（历史表）相关的Service
				.createHistoricProcessInstanceQuery()// 创建历史流程实例查询
				.processInstanceId(processInstanceId)// 使用流程实例ID查询
				.orderByProcessInstanceStartTime().asc().singleResult();
		System.out.println("流程实例id：" + hpi.getId());
		System.out.println("流程定义id：" + hpi.getProcessDefinitionId());
		System.out.println("流程开始时间：" + hpi.getStartTime());
		System.out.println("流程结束时间：" + hpi.getId());
		System.out.println("流程持续时间：" + hpi.getDurationInMillis() / 1000 / 60 + "分");

	}
}
