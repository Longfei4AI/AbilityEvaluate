/**   
 * @Title: BaseInformationListActivity.java
 */
package com.elder.abilityevaluate.entity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.content.Context;
import com.elder.abilityevaluate.basic.BasicEntity;
import com.elder.abilityevaluate.utils.CustomDataHelper;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
import com.lidroid.xutils.db.sqlite.Selector;
import com.lidroid.xutils.exception.DbException;

@Table(name = "BaseInformationListActivity")
public class BaseInformation extends BasicEntity {
	public static final String EVALUATED = "1";
	public static final String NOT_EVALUATED = "0";
	@Column(column = "id")
	private int id;
	@Column(column = "baseInfoId")
	private String baseInfoId;
	@Column(column = "a_1_1")
	private String a_1_1;	//评估编号
	@Column(column = "a_1_2")
	private String a_1_2;	//评估基准日期
	@Column(column = "a_1_3")
	private String a_1_3;	//评估原因
	@Column(column = "a_2_1")
	private String a_2_1;	//老人姓名
	@Column(column = "a_2_2")
	private String a_2_2;	//性别
	@Column(column = "a_2_3")
	private String a_2_3;	//出生日期
	@Column(column = "a_2_4")
	private String a_2_4;	//身份证号
	@Column(column = "a_2_5")
	private String a_2_5;	//社保卡号
	@Column(column = "a_2_6")
	private String a_2_6;	//民族
	@Column(column = "a_2_6_1")
	private String a_2_6_1;	//少数民族
	@Column(column = "a_2_7")
	private String a_2_7;	//文化程度
	@Column(column = "a_2_8")
	private String a_2_8;	//宗教信仰
	@Column(column = "a_2_8_1")
	private String a_2_8_1;	//有宗教信仰
	@Column(column = "a_2_9")
	private String a_2_9;	//婚姻状况
	@Column(column = "a_2_10")
	private String a_2_10;	//居住状况
	@Column(column = "a_2_11")
	private String a_2_11;	//医疗费用支付方式
	@Column(column = "a_2_11_1")
	private String a_2_11_1;	//其他支付方式
	@Column(column = "a_2_12")
	private String a_2_12;	//经济来源
	@Column(column = "a_2_13_1")
	private String a_2_13_1;	//痴呆
	@Column(column = "a_2_13_2")
	private String a_2_13_2;	//精神疾病
	@Column(column = "a_2_13_3")
	private String a_2_13_3;	//慢性疾病
	@Column(column = "a_2_14_1")
	private String a_2_14_1;	//跌倒
	@Column(column = "a_2_14_2")
	private String a_2_14_2;	//走失
	@Column(column = "a_2_14_3")
	private String a_2_14_3;	//噎食
	@Column(column = "a_2_14_4")
	private String a_2_14_4;	//自杀
	@Column(column = "a_2_14_5")
	private String a_2_14_5;	//其他
	@Column(column = "a_3_1")
	private String a_3_1;	//信息提供者姓名
	@Column(column = "a_3_2")
	private String a_3_2;	//信息提供者与老人的关系
	@Column(column = "a_3_3")
	private String a_3_3;	//联系人姓名
	@Column(column = "a_3_4")
	private String a_3_4;	//联系人电话
	@Column(column = "state")
	private String state;	//数据状态

	public Evaluation getEvaluation(Context context) throws DbException {
		return DataBaseHelper.getInstance(context,Evaluation.class).findFirst(
				Selector.from(Evaluation.class).where("baseInfoId", "=", this.baseInfoId));
	}
	public EvaluationReport getEvaluationReport(Context context) throws DbException {
		return DataBaseHelper.getInstance(context,EvaluationReport.class).findFirst(
				Selector.from(EvaluationReport.class).where("baseInfoId", "=", this.baseInfoId));
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

	public String getA_1_1() {
		return a_1_1;
	}

	public void setA_1_1(String a_1_1) {
		this.a_1_1 = a_1_1;
	}

	public String getA_1_2() {
		return a_1_2;
	}

	public void setA_1_2(String a_1_2) {
		this.a_1_2 = a_1_2;
	}

	public String getA_1_3() {
		return a_1_3;
	}

	public void setA_1_3(String a_1_3) {
		this.a_1_3 = a_1_3;
	}

	public String getA_2_1() {
		return a_2_1;
	}

	public void setA_2_1(String a_2_1) {
		this.a_2_1 = a_2_1;
	}

	public String getA_2_2() {
		return a_2_2;
	}

	public void setA_2_2(String a_2_2) {
		this.a_2_2 = a_2_2;
	}

	public String getA_2_3() {
		return a_2_3;
	}

	public void setA_2_3(String a_2_3) {
		this.a_2_3 = a_2_3;
	}

	public String getA_2_4() {
		return a_2_4;
	}

	public void setA_2_4(String a_2_4) {
		this.a_2_4 = a_2_4;
	}

	public String getA_2_5() {
		return a_2_5;
	}

	public void setA_2_5(String a_2_5) {
		this.a_2_5 = a_2_5;
	}

	public String getA_2_6() {
		return a_2_6;
	}

	public void setA_2_6(String a_2_6) {
		this.a_2_6 = a_2_6;
	}

	public String getA_2_6_1() {
		return a_2_6_1;
	}

	public void setA_2_6_1(String a_2_6_1) {
		this.a_2_6_1 = a_2_6_1;
	}

	public String getA_2_7() {
		return a_2_7;
	}

	public void setA_2_7(String a_2_7) {
		this.a_2_7 = a_2_7;
	}

	public String getA_2_8() {
		return a_2_8;
	}

	public void setA_2_8(String a_2_8) {
		this.a_2_8 = a_2_8;
	}

	public String getA_2_8_1() {
		return a_2_8_1;
	}

	public void setA_2_8_1(String a_2_8_1) {
		this.a_2_8_1 = a_2_8_1;
	}

	public String getA_2_9() {
		return a_2_9;
	}

	public void setA_2_9(String a_2_9) {
		this.a_2_9 = a_2_9;
	}

	public String getA_2_10() {
		return a_2_10;
	}

	public void setA_2_10(String a_2_10) {
		this.a_2_10 = a_2_10;
	}

	public String getA_2_11() {
		return a_2_11;
	}

	public void setA_2_11(String a_2_11) {
		this.a_2_11 = a_2_11;
	}

	public String getA_2_11_1() {
		return a_2_11_1;
	}

	public void setA_2_11_1(String a_2_11_1) {
		this.a_2_11_1 = a_2_11_1;
	}

	public String getA_2_12() {
		return a_2_12;
	}

	public void setA_2_12(String a_2_12) {
		this.a_2_12 = a_2_12;
	}

	public String getA_2_13_1() {
		return a_2_13_1;
	}

	public void setA_2_13_1(String a_2_13_1) {
		this.a_2_13_1 = a_2_13_1;
	}

	public String getA_2_13_2() {
		return a_2_13_2;
	}

	public void setA_2_13_2(String a_2_13_2) {
		this.a_2_13_2 = a_2_13_2;
	}

	public String getA_2_13_3() {
		return a_2_13_3;
	}

	public void setA_2_13_3(String a_2_13_3) {
		this.a_2_13_3 = a_2_13_3;
	}

	public String getA_2_14_1() {
		return a_2_14_1;
	}

	public void setA_2_14_1(String a_2_14_1) {
		this.a_2_14_1 = a_2_14_1;
	}

	public String getA_2_14_2() {
		return a_2_14_2;
	}

	public void setA_2_14_2(String a_2_14_2) {
		this.a_2_14_2 = a_2_14_2;
	}

	public String getA_2_14_3() {
		return a_2_14_3;
	}

	public void setA_2_14_3(String a_2_14_3) {
		this.a_2_14_3 = a_2_14_3;
	}

	public String getA_2_14_4() {
		return a_2_14_4;
	}

	public void setA_2_14_4(String a_2_14_4) {
		this.a_2_14_4 = a_2_14_4;
	}

	public String getA_2_14_5() {
		return a_2_14_5;
	}

	public void setA_2_14_5(String a_2_14_5) {
		this.a_2_14_5 = a_2_14_5;
	}

	public String getA_3_1() {
		return a_3_1;
	}

	public void setA_3_1(String a_3_1) {
		this.a_3_1 = a_3_1;
	}

	public String getA_3_2() {
		return a_3_2;
	}

	public void setA_3_2(String a_3_2) {
		this.a_3_2 = a_3_2;
	}

	public String getA_3_3() {
		return a_3_3;
	}

	public void setA_3_3(String a_3_3) {
		this.a_3_3 = a_3_3;
	}

	public String getA_3_4() {
		return a_3_4;
	}

	public void setA_3_4(String a_3_4) {
		this.a_3_4 = a_3_4;
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
				BaseInformation.class);
		try {
			JSONObject jsonObjectT = new JSONObject(json);
			JSONArray jsonArray = jsonObjectT.getJSONArray("data");
//			int numbers = jsonObjectT.getInt("num");
//			删除所有记录
			dataHelper.deleteAll(BaseInformation.class);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jo = jsonArray.getJSONObject(i);
				BaseInformation arcEmployee = new BaseInformation();
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
