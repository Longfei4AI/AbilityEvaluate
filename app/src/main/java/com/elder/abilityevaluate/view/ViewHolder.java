/**   
* @Title: ViewHolder.java 
* @Package com.custom.tpc.widget
* @Description: View缓存 
* @author 山东山大新元易通信息科技有限公司 www.sdcustom.com
* @date 2015-3-20 上午9:55:48 
* @version V1.0   
*/
package com.elder.abilityevaluate.view;

import android.util.SparseArray;
import android.view.View;

public class ViewHolder{ 
	   
    private final SparseArray<View> views; 
    private View convertView; 
 
    private ViewHolder(View convertView){ 
        this.views = new SparseArray<View>(); 
        this.convertView = convertView; 
    	convertView.setTag(this); 
    } 
 
    public static ViewHolder get(View convertView){ 
        if (convertView != null && convertView.getTag() == null) { 
            return new ViewHolder(convertView); 
        } 
        ViewHolder existedHolder = null;
        if(convertView!=null){
        	existedHolder = (ViewHolder) convertView.getTag(); 
        }
        return existedHolder; 
   } 
 
    public <T extends View> T getView(int viewId) { 
        View view = views.get(viewId); 
        if (view == null) { 
            view = convertView.findViewById(viewId); 
            views.put(viewId, view); 
        } 
        return (T) view; 
    } 
} 
