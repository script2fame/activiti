package com.hungteshun.utils;


import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.Interceptor;


/**
 * 登录验证拦截器
 * @author hungteshun
 *
 */
@SuppressWarnings("serial")
public class LoginInteceptor implements Interceptor {

	@Override
	public void destroy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

	}

	/**每次访问Action类之前，先执行intercept方法*/
	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		//放行，访问Action类中方法
		return invocation.invoke();
		
	}

}
