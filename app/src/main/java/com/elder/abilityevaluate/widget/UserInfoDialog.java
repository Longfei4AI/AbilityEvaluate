/**   

* @date 2015-8-5 下午4:08:09
 * @version V1.0   
 */
package com.elder.abilityevaluate.widget;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;

import org.json.JSONArray;
import org.json.JSONException;
import java.util.ArrayList;
import java.util.List;

public class UserInfoDialog extends Dialog {

	ListView infoLV;
	Button logoutB;
	Button closeB;
	String userName;
	String loginName;
	int[] options = { 0, 0 };
	String corpName;
	String loginTime;
	ArrayAdapter<String> adapter;
	List<String> infos = new ArrayList<String>();
	CallBack callback;

	public UserInfoDialog(Context context, CallBack call) {
		super(context, R.style.Custom_Dialog);
		callback = call;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 设置对话框使用的布局文件
		this.setContentView(R.layout.dialog_userinfo);
		infoLV = (ListView) findViewById(R.id.lv_user_info);
		logoutB = (Button) findViewById(R.id.button_logout);
		closeB = (Button) findViewById(R.id.button_close);

		SharedPreferences spf = getContext().getSharedPreferences(
				GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
		String rightStr = spf.getString(PreferenceParams.USER_RIGHT,
				"[]");
		// 初始化数据
		infos.add(new String("当前用户："
				+ spf.getString(PreferenceParams.USER_NAME, "")));
		infos.add(new String("用户帐号："
				+ spf.getString(PreferenceParams.USER_ID, "")));
		infos.add(new String("所属单位："
				//+ spf.getString(PreferenceParams.CORP_NAME, "")));
				+ "老年人能力评估中心"));
		String optionStr = "";
		
		try {
			JSONArray jsonArray = new JSONArray(rightStr);
			String r = "";
			for(int i = 0;i<jsonArray.length();i++){
				r = jsonArray.getString(i);
				if(r.equals("001")){
					options[0] = 1;
				}else if(r.equals("002")){
					options[1] = 1;
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		if (options.length > 1) {
			if (options[0] == 1) {
				optionStr += "，质检员";
			}
			if (options[1] == 1) {
				optionStr += "，保管员";
			}
		}
		optionStr = optionStr.replaceFirst("，", "");
		optionStr = optionStr.equals("") ? "无" : optionStr;
		infos.add(new String("用户角色：" + optionStr));
		infos.add(new String("登录时间："
				+ spf.getString(PreferenceParams.LOGIN_TIME, "")));
		adapter = new ArrayAdapter<String>(getContext(),
				R.layout.list_item_simple, infos);
		infoLV.setAdapter(adapter);

		closeB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				UserInfoDialog.this.cancel();
			}
		});
		logoutB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.logOut();
			}
		});
	}

	@Override
	public void show() {
		super.show();
	}

	public interface CallBack {
		public void logOut();
	}
}
