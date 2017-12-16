package com.hungteshun.utils;

import org.apache.struts2.ServletActionContext;

import com.opensymphony.xwork2.ActionContext;
/**
 * 数据入栈封装类
 * @author hungteshun
 *
 */
public class ValueContext {

	/**放置在Root栈中，存放的对象例如数据回显*/
	public static void putValueContext(String key,Object value){
		ActionContext.getContext().put(key, value);
	}
	
	/**压入值栈顶，存放的对象例如list*/
	public static void putValueStack(Object o){
		ServletActionContext.getContext().getValueStack().push(o);
	}
}
