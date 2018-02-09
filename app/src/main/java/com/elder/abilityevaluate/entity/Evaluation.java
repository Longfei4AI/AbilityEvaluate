/**   
 * @Title: Evaluation.java
 */
package com.elder.abilityevaluate.entity;

import android.content.Context;
import com.elder.abilityevaluate.basic.BasicEntity;
import com.elder.abilityevaluate.utils.CustomDataHelper;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "Evaluation")
public class Evaluation extends BasicEntity {
	@Column(column = "id")
	private int id;
	@Column(column = "baseInfoId")
	private String baseInfoId;
	@Column(column = "b_1_1")
	private int b_1_1 = -1;
	@Column(column = "b_1_2")
	private int b_1_2 = -1;
	@Column(column = "b_1_3")
	private int b_1_3 = -1;
	@Column(column = "b_1_4")
	private int b_1_4 = -1;
	@Column(column = "b_1_5")
	private int b_1_5 = -1;
	@Column(column = "b_1_6")
	private int b_1_6 = -1;
	@Column(column = "b_1_7")
	private int b_1_7 = -1;
	@Column(column = "b_1_8")
	private int b_1_8 = -1;
	@Column(column = "b_1_9")
	private int b_1_9 = -1;
	@Column(column = "b_1_10")
	private int b_1_10 = -1;
	@Column(column = "b_1_score")
	private int b_1_score = -1;
	@Column(column = "b_1_level")
	private String b_1_level;
	@Column(column = "b_2_1")
	private int b_2_1 = -1;
	@Column(column = "b_2_2")
	private int b_2_2 = -1;
	@Column(column = "b_2_3")
	private int b_2_3 = -1;
	@Column(column = "b_2_score")
	private int b_2_score = -1;
	@Column(column = "b_2_level")
	private String b_2_level;
	@Column(column = "b_3_1")
	private int b_3_1 = -1;
	@Column(column = "b_3_2")
	private int b_3_2 = -1;
	@Column(column = "b_3_3")
	private int b_3_3 = -1;
	@Column(column = "b_3_4")
	private int b_3_4 = -1;
	@Column(column = "b_3_score")
	private int b_3_score = -1;
	@Column(column = "b_3_level")
	private String b_3_level;
	@Column(column = "b_4_1")
	private int b_4_1 = -1;
	@Column(column = "b_4_2")
	private int b_4_2 = -1;
	@Column(column = "b_4_3")
	private int b_4_3 = -1;
	@Column(column = "b_4_4")
	private int b_4_4 = -1;
	@Column(column = "b_4_5")
	private int b_4_5 = -1;
	@Column(column = "b_4_score")
	private int b_4_score = -1;
	@Column(column = "b_4_level")
	private String b_4_level;
	@Column(column = "state")
	private String state;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public String getBaseInfoId() {
		return baseInfoId;
	}

	public void setBaseInfoId(String baseInfoId) {
		this.baseInfoId = baseInfoId;
	}

	public int getB_1_1() {
		return b_1_1;
	}

	public void setB_1_1(int b_1_1) {
		this.b_1_1 = b_1_1;
	}

	public int getB_1_2() {
		return b_1_2;
	}

	public void setB_1_2(int b_1_2) {
		this.b_1_2 = b_1_2;
	}

	public int getB_1_3() {
		return b_1_3;
	}

	public void setB_1_3(int b_1_3) {
		this.b_1_3 = b_1_3;
	}

	public int getB_1_4() {
		return b_1_4;
	}

	public void setB_1_4(int b_1_4) {
		this.b_1_4 = b_1_4;
	}

	public int getB_1_5() {
		return b_1_5;
	}

	public void setB_1_5(int b_1_5) {
		this.b_1_5 = b_1_5;
	}

	public int getB_1_6() {
		return b_1_6;
	}

	public void setB_1_6(int b_1_6) {
		this.b_1_6 = b_1_6;
	}

	public int getB_1_7() {
		return b_1_7;
	}

	public void setB_1_7(int b_1_7) {
		this.b_1_7 = b_1_7;
	}

	public int getB_1_8() {
		return b_1_8;
	}

	public void setB_1_8(int b_1_8) {
		this.b_1_8 = b_1_8;
	}

	public int getB_1_9() {
		return b_1_9;
	}

	public void setB_1_9(int b_1_9) {
		this.b_1_9 = b_1_9;
	}

	public int getB_1_10() {
		return b_1_10;
	}

	public void setB_1_10(int b_1_10) {
		this.b_1_10 = b_1_10;
	}

	public int getB_1_score() {
		return b_1_score;
	}

	public void setB_1_score(int b_1_score) {
		this.b_1_score = b_1_score;
	}

	public String getB_1_level() {
		return b_1_level;
	}

	public void setB_1_level(String b_1_level) {
		this.b_1_level = b_1_level;
	}

	public int getB_2_1() {
		return b_2_1;
	}

	public void setB_2_1(int b_2_1) {
		this.b_2_1 = b_2_1;
	}

	public int getB_2_2() {
		return b_2_2;
	}

	public void setB_2_2(int b_2_2) {
		this.b_2_2 = b_2_2;
	}

	public int getB_2_3() {
		return b_2_3;
	}

	public void setB_2_3(int b_2_3) {
		this.b_2_3 = b_2_3;
	}

	public int getB_2_score() {
		return b_2_score;
	}

	public void setB_2_score(int b_2_score) {
		this.b_2_score = b_2_score;
	}

	public String getB_2_level() {
		return b_2_level;
	}

	public void setB_2_level(String b_2_level) {
		this.b_2_level = b_2_level;
	}

	public int getB_3_1() {
		return b_3_1;
	}

	public void setB_3_1(int b_3_1) {
		this.b_3_1 = b_3_1;
	}

	public int getB_3_2() {
		return b_3_2;
	}

	public void setB_3_2(int b_3_2) {
		this.b_3_2 = b_3_2;
	}

	public int getB_3_3() {
		return b_3_3;
	}

	public void setB_3_3(int b_3_3) {
		this.b_3_3 = b_3_3;
	}

	public int getB_3_4() {
		return b_3_4;
	}

	public void setB_3_4(int b_3_4) {
		this.b_3_4 = b_3_4;
	}

	public int getB_3_score() {
		return b_3_score;
	}

	public void setB_3_score(int b_3_score) {
		this.b_3_score = b_3_score;
	}

	public String getB_3_level() {
		return b_3_level;
	}

	public void setB_3_level(String b_3_level) {
		this.b_3_level = b_3_level;
	}

	public int getB_4_1() {
		return b_4_1;
	}

	public void setB_4_1(int b_4_1) {
		this.b_4_1 = b_4_1;
	}

	public int getB_4_2() {
		return b_4_2;
	}

	public void setB_4_2(int b_4_2) {
		this.b_4_2 = b_4_2;
	}

	public int getB_4_3() {
		return b_4_3;
	}

	public void setB_4_3(int b_4_3) {
		this.b_4_3 = b_4_3;
	}

	public int getB_4_4() {
		return b_4_4;
	}

	public void setB_4_4(int b_4_4) {
		this.b_4_4 = b_4_4;
	}

	public int getB_4_5() {
		return b_4_5;
	}

	public void setB_4_5(int b_4_5) {
		this.b_4_5 = b_4_5;
	}

	public int getB_4_score() {
		return b_4_score;
	}

	public void setB_4_score(int b_4_score) {
		this.b_4_score = b_4_score;
	}

	public String getB_4_level() {
		return b_4_level;
	}

	public void setB_4_level(String b_4_level) {
		this.b_4_level = b_4_level;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public static int insertByJson(Context context, String json) {
		if (json == null || json.equals("")) {
			return 0;
		}
		CustomDataHelper dataHelper = CustomDataHelper.getInstance(context,
				Evaluation.class);
		try {
			JSONObject jsonObjectT = new JSONObject(json);
			JSONArray jsonArray = jsonObjectT.getJSONArray("data");
//			int numbers = jsonObjectT.getInt("num");
//			删除所有记录
			dataHelper.deleteAll(Evaluation.class);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				Evaluation arcEmployee = new Evaluation();
				arcEmployee.setValueByJson(jo.toString());
				dataHelper.save(arcEmployee);
			}
			return jsonArray.length();
		} catch (JSONException e) {
			e.printStackTrace();
			return 0;
		}
	}
}
