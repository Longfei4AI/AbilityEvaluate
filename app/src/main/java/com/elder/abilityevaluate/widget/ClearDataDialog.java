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

public class ClearDataDialog extends Dialog {

	ListView infoLV;
	Button clearB;
	Button notClearB;
	ArrayAdapter<String> adapter;
	List<String> infos = new ArrayList<String>();
	CallBack callback;
	public int registerSize;
	public int evaluatedSize;
	public int leftSize;

	public ClearDataDialog(Context context, CallBack call) {
		super(context, R.style.Custom_Dialog);
		callback = call;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 设置对话框使用的布局文件
		this.setContentView(R.layout.dialog_clear_data);
		infoLV = findViewById(R.id.lv_clear_data);
		clearB = findViewById(R.id.button_clear);
		notClearB = findViewById(R.id.button_notClear);
		// 初始化数据
		infos.add(new String("当前登记老人数量：" + 0));
		infos.add(new String("完成评估老人数量：" + 0));
		infos.add(new String("剩余评估老人数量：" + 0));
		adapter = new ArrayAdapter<String>(getContext(),
				R.layout.list_item_simple, infos);
		infoLV.setAdapter(adapter);

		notClearB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				ClearDataDialog.this.cancel();
			}
		});
		clearB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				callback.clear();
			}
		});
	}
	@Override
	public void show() {
		super.show();
		infos.clear();
		infos.add(new String("当前登记老人数量：" + registerSize + "人"));
		infos.add(new String("完成评估老人数量：" + evaluatedSize + "人"));
		infos.add(new String("剩余评估老人数量：" + leftSize + "人"));
		adapter.notifyDataSetChanged();
	}

	public interface CallBack {
		public void clear();
	}
}
