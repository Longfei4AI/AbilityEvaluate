/**   
 * @Title: BasicEntity.java 
 * @Package com.jr001.basic 
 * @Description: TODO(用一句话描述该文件做什么) 
 * @author jiaone@163.com
 * @date 2015-8-6 上午10:48:21 
 * @version V1.0   
 */
package com.elder.abilityevaluate.basic;

import com.elder.abilityevaluate.utils.ObjectUtils;

import org.json.JSONException;
import org.json.JSONObject;
import java.lang.reflect.Field;

public class BasicEntity {
	public String getJsonStr() {
		String json = "";
		JSONObject jsonObject = new JSONObject();
		Class c = this.getClass();
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {
			Object v;
			try {
				v = ObjectUtils.invokeGetMethod(this,f.getName(), null);
				jsonObject.put(f.getName(), v);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		json = jsonObject.toString();
		return json;
	}
	
	public void setValueByJson(String json) throws JSONException{
		JSONObject jsonObject = new JSONObject(json);
		Class c = this.getClass();
		Field field[] = c.getDeclaredFields();
		for (Field f : field) {
			Object v;
			try {
				v = jsonObject.get(f.getName());
				ObjectUtils.invokeSetMethod(this,f.getName(),f.getType(), new Object[]{v});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
