package com.hungteshun.historyQuery;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricVariableInstance;
import org.junit.Test;

import com.hungteshun.processVariables.Person;

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
		System.out.println("流程结束时间：" + hpi.getEndTime());
		System.out.println("流程持续时间：" + hpi.getDurationInMillis() / 1000 / 60 + "分");

	}

	/** 查询历史活动 */
	@Test
	public void findHistoryActiviti() {
		String processInstanceId = "401";
		List<HistoricActivityInstance> list = processEngine.getHistoryService()//
				.createHistoricActivityInstanceQuery()// 创建历史活动实例的查询
				.processInstanceId(processInstanceId)//
				.orderByHistoricActivityInstanceStartTime().asc()//
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricActivityInstance hai : list) {
				System.out.println("活动id：" + hai.getId() + "，活动流程实例id：" + hai.getProcessInstanceId() + "，活动类型："
						+ hai.getActivityType() + "，活动开始时间：" + hai.getStartTime() + "，活动结束时间：" + hai.getEndTime()
						+ "，活动持续时间：" + hai.getDurationInMillis() + "毫秒");
				System.out.println("#####################");
			}
		}
	}

	/** 查询历史任务 */
	@Test
	public void findHistoryTask() {
		String processInstanceId = "401";
		List<HistoricTaskInstance> list = processEngine.getHistoryService()// 与历史数据（历史表）相关的Service
				.createHistoricTaskInstanceQuery()// 创建历史任务实例查询
				.processInstanceId(processInstanceId)//
				.orderByHistoricTaskInstanceStartTime().asc().list();
		if (list != null && list.size() > 0) {
			for (HistoricTaskInstance hti : list) {
				System.out.println("任务id：" + hti.getId() + "，任务名称：" + hti.getName() + "，任务id："
						+ hti.getProcessInstanceId() + "，任务开始时间：" + hti.getStartTime() + "，任务结束时间" + hti.getEndTime()
						+ "，任务持续时间" + hti.getDurationInMillis() / 1000 / 60 + "分");
				System.out.println("################################");
			}
		}
	}

	/** 查询历史流程变量 */
	@Test
	public void findHistoryProcessVariables() {
		String processInstanceId = "401";
		List<HistoricVariableInstance> list = processEngine.getHistoryService()//
				.createHistoricVariableInstanceQuery()// 创建一个历史的流程变量查询对象
				.processInstanceId(processInstanceId)//
				.list();
		if (list != null && list.size() > 0) {
			for (HistoricVariableInstance hvi : list) {
				System.out.println("流程变量id：" + hvi.getId() + "，流程变量所在的流程实例id：" + hvi.getProcessInstanceId() + "，流程变量名称："
						+ hvi.getVariableName() + "，流程变量类型：" + hvi.getVariableTypeName() + "，流程变量的值（或引用）："
						+ hvi.getValue());
				Person p = (Person) hvi.getValue();
				System.out.println("流程变量的值：" + p.getName());
				System.out.println("###############################################");
			}
		}
	}
}
