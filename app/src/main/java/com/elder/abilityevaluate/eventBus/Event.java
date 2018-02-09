/**   
* @Title: Event.java 
* @Package com.jr001.eventBus 
* @Description: TODO(用一句话描述该文件做什么) 
* @author jiaone@163.com
* @date 2015-4-11 上午10:48:00 
* @version V1.0   
*/
package com.elder.abilityevaluate.eventBus;

import java.util.Map;

public class Event {
	public static class MsgEvent{
		public Class fromClass;
		public int type;
		public Map<String,Object> values;
		public MsgEvent(int type, Map<String,Object> values) {
			super();
			this.type = type;
			this.values = values;
		}
		
	}
	
	public static class EMSEvent{
		
	}
}
