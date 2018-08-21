/**   
 * @Title: EvaluationReport.java
 */
package com.elder.abilityevaluate.entity;

import android.content.Context;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicEntity;
import com.elder.abilityevaluate.utils.CustomDataHelper;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@Table(name = "EvaluationReport")
public class EvaluationReport extends BasicEntity {
	public static final String FINISHED = "1";
	public static final String NOT_FINISHED = "0";
	@Column(column = "id")
	private int id;
	@Column(column = "baseInfoId")
	private String baseInfoId;
	@Column(column = "e_code")
	private String e_code;
	@Column(column = "e_evalue_code")
	private String e_evalue_code;
	@Column(column = "e_name")
	private String e_name;
	@Column(column = "e_location")
	private String e_location;
	@Column(column = "e_time")
	private String e_time;
	@Column(column = "e_type")
	private String e_type;
	@Column(column = "e_count")
	private int e_count;
	@Column(column = "e_environment")
	private String e_environment;
	@Column(column = "e_light")
	private String e_light;
	@Column(column = "e_air")
	private String e_air;
	@Column(column = "e_temperature")
	private int e_temperature;
	@Column(column = "b_1_grade")
	private int b_1_grade;
	@Column(column = "b_2_grade")
	private int b_2_grade;
	@Column(column = "b_3_grade")
	private int b_3_grade;
	@Column(column = "b_4_grade")
	private int b_4_grade;
	@Column(column = "e_grade_pre")
	private int e_grade_pre = -1;
	@Column(column = "grade_change_item")
	private String grade_change_item;
	@Column(column = "e_grade_final")
	private int e_grade_final = -1;
	@Column(column = "e_factor")
	private String e_factor;
	@Column(column = "e_tools")
	private String e_tools;
	@Column(column = "e_result")
	private String e_result;
	@Column(column = "e_organization")
	private String e_organization;
	@Column(column = "e_issue_date")
	private String e_issue_date;
	@Column(column = "e_approver")
	private String e_approver;
	@Column(column = "e_checker")
	private String e_checker;
	@Column(column = "e_evaluator")
	private String e_evaluator;
	@Column(column = "e_remark")
	private String e_remark;
	@Column(column = "state")
	private String state;

	public BaseInformation getBaseInformation(Context context) {
		return DataBaseHelper.getInstance(context,BaseInformation.class).findFirst(
				Selector.from(BaseInformation.class).where("baseInfoId", "=", this.baseInfoId));
	}
	public Evaluation getEvaluation(Context context) {
		return DataBaseHelper.getInstance(context,Evaluation.class).findFirst(
				Selector.from(Evaluation.class).where("baseInfoId", "=", this.baseInfoId));
	}

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

	public String getE_code() {
		return e_code;
	}

	public void setE_code(String e_code) {
		this.e_code = e_code;
	}

	public String getE_evalue_code() {
		return e_evalue_code;
	}

	public void setE_evalue_code(String e_evalue_code) {
		this.e_evalue_code = e_evalue_code;
	}

	public String getE_name() {
		return e_name;
	}

	public void setE_name(String e_name) {
		this.e_name = e_name;
	}

	public String getE_location() {
		return e_location;
	}

	public void setE_location(String e_location) {
		this.e_location = e_location;
	}

	public String getE_time() {
		return e_time;
	}

	public void setE_time(String e_time) {
		this.e_time = e_time;
	}

	public String getE_type() {
		return e_type;
	}

	public void setE_type(String e_type) {
		this.e_type = e_type;
	}

	public int getE_count() {
		return e_count;
	}

	public void setE_count(int e_count) {
		this.e_count = e_count;
	}

	public String getE_environment() {
		return e_environment;
	}

	public void setE_environment(String e_environment) {
		this.e_environment = e_environment;
	}

	public String getE_light() {
		return e_light;
	}

	public void setE_light(String e_light) {
		this.e_light = e_light;
	}

	public String getE_air() {
		return e_air;
	}

	public void setE_air(String e_air) {
		this.e_air = e_air;
	}

	public int getE_temperature() {
		return e_temperature;
	}

	public void setE_temperature(int e_temperature) {
		this.e_temperature = e_temperature;
	}

	public int getB_1_grade() {
		return b_1_grade;
	}

	public void setB_1_grade(int b_1_grade) {
		this.b_1_grade = b_1_grade;
	}

	public int getB_2_grade() {
		return b_2_grade;
	}
	public int getB_grade_strId(int grade) {
		int strId = R.string.level_0;
		switch (grade){
			case 0:
				strId = R.string.level_0;
				break;
			case 1:
				strId = R.string.level_1;
				break;
			case 2:
				strId = R.string.level_2;
				break;
			case 3:
				strId = R.string.level_3;
				break;
		}
		return strId;
	}

	public void setB_2_grade(int b_2_grade) {
		this.b_2_grade = b_2_grade;
	}

	public int getB_3_grade() {
		return b_3_grade;
	}

	public void setB_3_grade(int b_3_grade) {
		this.b_3_grade = b_3_grade;
	}

	public int getB_4_grade() {
		return b_4_grade;
	}

	public void setB_4_grade(int b_4_grade) {
		this.b_4_grade = b_4_grade;
	}

	public int getE_grade_pre() {
		return e_grade_pre;
	}
	public int getE_grade_strId(int grade){
		int strId = R.string.grade_0;
		switch (grade){
			case 0:
				strId = R.string.grade_0;
				break;
			case 1:
				strId = R.string.grade_1;
				break;
			case 2:
				strId = R.string.grade_2;
				break;
			case 3:
				strId = R.string.grade_3;
				break;
		}
		return strId;
	}

	public void setE_grade_pre(int e_grade_pre) {
		this.e_grade_pre = e_grade_pre;
	}

	public String getGrade_change_item() {
		return grade_change_item;
	}

	public void setGrade_change_item(String grade_change_item) {
		this.grade_change_item = grade_change_item;
	}

	public int getE_grade_final() {
		return e_grade_final;
	}

	public void setE_grade_final(int e_grade_final) {
		this.e_grade_final = e_grade_final;
	}

	public String getE_factor() {
		return e_factor;
	}

	public void setE_factor(String e_factor) {
		this.e_factor = e_factor;
	}

	public String getE_tools() {
		return e_tools;
	}

	public void setE_tools(String e_tools) {
		this.e_tools = e_tools;
	}

	public String getE_result() {
		return e_result;
	}

	public void setE_result(String e_result) {
		this.e_result = e_result;
	}

	public String getE_organization() {
		return e_organization;
	}

	public void setE_organization(String e_organization) {
		this.e_organization = e_organization;
	}

	public String getE_issue_date() {
		return e_issue_date;
	}

	public void setE_issue_date(String e_issue_date) {
		this.e_issue_date = e_issue_date;
	}

	public String getE_approver() {
		return e_approver;
	}

	public void setE_approver(String e_approver) {
		this.e_approver = e_approver;
	}

	public String getE_checker() {
		return e_checker;
	}

	public void setE_checker(String e_checker) {
		this.e_checker = e_checker;
	}

	public String getE_evaluator() {
		return e_evaluator;
	}

	public void setE_evaluator(String e_evaluator) {
		this.e_evaluator = e_evaluator;
	}

	public String getE_remark() {
		return e_remark;
	}

	public void setE_remark(String e_remark) {
		this.e_remark = e_remark;
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
				EvaluationReport.class);
		try {
			JSONObject jsonObjectT = new JSONObject(json);
			JSONArray jsonArray = jsonObjectT.getJSONArray("data");
//			int numbers = jsonObjectT.getInt("num");
//			删除所有记录
			dataHelper.deleteAll(EvaluationReport.class);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				EvaluationReport arcEmployee = new EvaluationReport();
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
