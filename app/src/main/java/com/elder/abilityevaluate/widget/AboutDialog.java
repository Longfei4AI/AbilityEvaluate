/**   

 * @Description: TODO(用一句话描述该文件做什么) 
 * @author jiaone@163.com
 * @date 2015-8-5 下午4:08:09 
 * @version V1.0   
 */
package com.elder.abilityevaluate.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import com.elder.abilityevaluate.R;

public class AboutDialog extends Dialog {

	ListView infoLV;
	ArrayAdapter<String> adapter;
	Button closeB;

	public AboutDialog(Context context) {
		super(context, R.style.Custom_Dialog);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		// 设置对话框使用的布局文件
		this.setContentView(R.layout.dialog_about);
		infoLV = (ListView) findViewById(R.id.lv_user_info);
		closeB = (Button) findViewById(R.id.button_close);
		adapter = new ArrayAdapter<String>(getContext(),
				R.layout.list_item_simple, getContext().getResources()
						.getStringArray(R.array.about_array));
		infoLV.setAdapter(adapter);

		closeB.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				AboutDialog.this.cancel();
			}
		});
	}

	@Override
	public void show() {
		super.show();
	}
}
