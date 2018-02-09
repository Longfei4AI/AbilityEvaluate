/**   
 * @Title: ObjectUtils.java 
 * @Package com.jr001.utils 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author jiaone@163.com
 * @date 2015-8-4 下午9:27:55 
 * @version V1.0   
 */
package com.elder.abilityevaluate.utils;

import java.lang.reflect.Method;

public class ObjectUtils {
	/**
	 * 必须要有get方法
	 * 获得对象属性的值
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeGetMethod(Object owner,String methodName,
			Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getMethod("get" + methodName);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			return " can't find 'get'" + methodName + "' method";
		}
		return method.invoke(owner,args);
	}
	
	/**
	 * 必须要有set方法
	 * 获得对象属性的值
	 */
	@SuppressWarnings("unchecked")
	public static void invokeSetMethod(Object owner,String methodName,Class fieldClass,
			Object[] args) throws Exception {
		Class ownerClass = owner.getClass();
		methodName = methodName.substring(0, 1).toUpperCase()
				+ methodName.substring(1);
		Method method = null;
		try {
			method = ownerClass.getDeclaredMethod("set" + methodName,fieldClass);
		} catch (SecurityException e) {
		} catch (NoSuchMethodException e) {
			System.err.println(" can't find 'set'" + methodName + "' method");
		}
		method.invoke(owner,args);
	}
}
