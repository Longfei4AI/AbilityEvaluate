package com.elder.abilityevaluate.widget;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.elder.abilityevaluate.R;

import java.util.ArrayList;
import java.util.List;

public class CustomToast extends Toast {
	
	private static List<CustomToast> list =  new ArrayList<CustomToast>();
	
	public CustomToast(Context context) {
		super(context);
		if(list != null){
			for(int i = 0;i<list.size()-1;i++){//只保留最后一个Toast
				CustomToast customToast = list.get(i);
				customToast.cancel();
				list.remove(i);
				customToast = null;
			}
		}
		list.add(this);
	}

	public static CustomToast show(Activity activity, String mes,int length,int gravity,int yoffset) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) activity.findViewById(R.id.toast_layout_root));
		TextView textView = (TextView) view.findViewById(R.id.toastmes);
		view.getBackground().setAlpha(200);
		textView.setText(mes);
		CustomToast toast = new CustomToast(activity);
		toast.setDuration(length);
		toast.setView(view);
		toast.setGravity(gravity , 0, yoffset);
		toast.show();
		return toast;
	}
	
	public static CustomToast show(Activity activity, int mesID,int length,int gravity,int yoffset) {
		LayoutInflater inflater = LayoutInflater.from(activity);
		View view = inflater.inflate(R.layout.custom_toast,
				(ViewGroup) activity.findViewById(R.id.toast_layout_root));
		TextView textView = (TextView) view.findViewById(R.id.toastmes);
		view.getBackground().setAlpha(200);
		textView.setText(activity.getResources().getString(mesID));
		CustomToast toast = new CustomToast(activity);
		toast.setDuration(length);
		toast.setView(view);
		toast.setGravity(gravity , 0, yoffset);
		toast.show();
		return toast;
	}
	
	public static CustomToast show(Activity activity, int mesID,int length){
		return CustomToast.show(activity, mesID, length, Gravity.CENTER|Gravity.BOTTOM,100);
	}
	
	public static CustomToast show(Activity activity, String mes,int length){
		return CustomToast.show(activity, mes, length, Gravity.CENTER|Gravity.BOTTOM,100);
	}
}
