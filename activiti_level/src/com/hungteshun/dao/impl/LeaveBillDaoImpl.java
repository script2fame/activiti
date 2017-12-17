package com.hungteshun.dao.impl;

import java.util.List;

import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import com.hungteshun.dao.ILeaveBillDao;
import com.hungteshun.domain.Employee;
import com.hungteshun.domain.LeaveBill;
import com.hungteshun.utils.SessionContext;

/**
 * 请假单业务操作dao
 * 
 * @author Administrator
 *
 */
public class LeaveBillDaoImpl extends HibernateDaoSupport implements ILeaveBillDao {

	// 查询当前用的所有请假信息
	@Override
	public List<LeaveBill> findLeaveBillList() {
		// 从Session中获取当前用户
		Employee employee = SessionContext.getUser();
		String hql = "from LeaveBill o where o.user=?";// 指定当前用户的请假单
		List<LeaveBill> list = this.getHibernateTemplate().find(hql, employee);
		return list;
	}

	// 通过id查找请假单信息
	@Override
	public LeaveBill findLeaveBillById(Long id) {
		return this.getHibernateTemplate().get(LeaveBill.class, id);
	}

	// 保存请假信息
	@Override
	public void saveLeaveBill(LeaveBill leaveBill) {
		this.getHibernateTemplate().save(leaveBill);
	}

	// 更新请假信息
	@Override
	public void updateLeaveBill(LeaveBill leaveBill) {
		this.getHibernateTemplate().update(leaveBill);
	}

	// 删除请假信息
	@Override
	public void deleteLeaveBillById(Long id) {
		// 1、使用请假单ID，查询请假单信息，获取对象LeaveBill
		LeaveBill bill = this.findLeaveBillById(id);
		// 2、执行删除
		this.getHibernateTemplate().delete(bill);
	}

}
