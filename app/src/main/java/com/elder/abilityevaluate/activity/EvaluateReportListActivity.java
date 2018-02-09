package com.elder.abilityevaluate.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.basic.BasicActiviy;
import com.elder.abilityevaluate.config.GlobalSetting;
import com.elder.abilityevaluate.config.PreferenceParams;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.service.LogService;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.UserInfoDialog;
import com.lidroid.xutils.db.sqlite.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import de.greenrobot.event.EventBus;

public class EvaluateReportListActivity extends BasicActiviy {
    public static final int DIALOG_LOGOUT = 0x01;
    public static final int DIALOG_EXIT = 0x02;
    public final static int EVENT_DOWNLOAD = 0x0A;
    // 基本信息
    private static final String MODEL_BASE_INFORMATION = "base_information";
    // 能力评估
    private static final String MODEL_ABILITY_EVALUATE = "ability_evaluate";
    // 评估报告
    private static final String MODEL_REPORT_EVALUATE = "report_evaluate";
    // 参数设置
    private static final String MODEL_SETTING = "setting";
    // 数据上报
    private static final String MODEL_UPLOAD = "upload";
    private CustomLoadingDialog loadingDialog;
    private CustomDialog confirmDialog;
    private CustomDialog alertDialog;
    private UserInfoDialog userInfoDialog;
    private String corpCode = "";
    private ListView listview = null;
    private SharedPreferences spf;
    private int dialogType = 0;
    private SimpleAdapter saMenuItem = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInitedLoading = false;
        setContentView(R.layout.base_information_list_activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void init() {
        listview = (ListView) findViewById(R.id.menu_list);
        spf = getSharedPreferences(
                GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
        corpCode = spf.getString(PreferenceParams.CORP_CODE, "");
        ArrayList<HashMap<String, Object>> dataList = new ArrayList<HashMap<String, Object>>();
        initializeData(dataList);
        saMenuItem = new SimpleAdapter(this,
                dataList, // 数据源
                R.layout.base_information_list_item, // xml实现
                new String[] {  "ItemText", "ItemId", "ItemContent", "ItemState" }, // 对应map的Key
                new int[] { R.id.ItemText, R.id.ItemId,R.id.ItemContent, R.id.ItemState }); // 对应R的Id
    }

    @Override
    public void initFinished() {
        // 添加Item到网格中
        listview.setAdapter(saMenuItem);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView tv = (TextView) arg1.findViewById(R.id.ItemId);
                Bundle bundle = new Bundle();
                bundle.putString("id",tv.getText().toString());
                GoActivityWithOutFinishing(BaseInformationViewActivity.class,bundle);
            }
        });

        loadingDialog = new CustomLoadingDialog(this);
        userInfoDialog = new UserInfoDialog(this,new UserInfoDialog.CallBack() {
            @Override
            public void logOut() {
                dialogType = DIALOG_LOGOUT;
                confirmDialog.setType(CustomDialog.TYPE_QUESTION);
                confirmDialog.setMessage("确定注销当前登录状态？");
                confirmDialog.show();
            }
        });
        CustomDialog.Builder builder = new CustomDialog.Builder(this);
        builder.setTitle("提示")
                .setPositiveButton(R.string.sure,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                doAfterConfirm(true);
                            }
                        })
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                doAfterConfirm(false);
                            }
                        });
        confirmDialog = builder.create();
        builder = new CustomDialog.Builder(this);
        builder.setTitle("提示").setNegativeButton(R.string.close,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        alertDialog.cancel();
                    }
                });
        alertDialog =  builder.create();
    }
    /**
     * 初始化功能模块
     * @param list
     */
    private void initializeData(ArrayList<HashMap<String, Object>> list) {
        List<BaseInformation> baseInforList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class));
        HashMap<String, Object> baseInfo = null;
        if(baseInforList != null && baseInforList.size() > 0){
            for (BaseInformation base : baseInforList){
                baseInfo = new HashMap<String, Object>();
                baseInfo.put("ItemText", base.getA_2_1()); //老人姓名
                baseInfo.put("ItemId", base.getBaseInfoId());
                baseInfo.put("ItemContent", base.getA_2_4());//身份证号
                baseInfo.put("ItemState", base.getState().equals(BaseInformation.EVALUATED)
                        ? getString(R.string.evaluated) : getString(R.string.not_evaluate));//评估状态
                list.add(baseInfo);
            }
        }else{
            baseInfo = new HashMap<String, Object>();
            baseInfo.put("ItemText", "暂无数据");
            baseInfo.put("ItemId", "");
            baseInfo.put("ItemContent", "");
            baseInfo.put("ItemImage", R.drawable.icon_go);
            baseInfo.put("ItemState", getString(R.string.not_evaluate));//评估状态
            list.add(baseInfo);
        }
    }

    /**
     * @Title: logOut
     * @Description: 注销
     * @return void    返回类型
     * @throws
     */
    public void logOut(View v){
        userInfoDialog.show();
    }
    public void back(View v) {
        GoActivityWithFinishing(MainListActivity.class, null);
    }
   /**
   * @Author: wlf
   * @Time: 2018/2/7 20:21
   * @Desc: 点击添加基本信息
   * @Params:
   * @Return:
   */
    public void add(View v){
        GoActivityWithOutFinishing(BaseInformationEditActivity.class, null);
    }
    /**
    * @Author: wlf
    * @Time: 2018/2/7 20:22
    * @Desc:
    * @Params:
    * @Return:
    */
    private void doAfterConfirm(boolean flag) {
        confirmDialog.dismiss();
        switch (dialogType) {
            case DIALOG_EXIT:
                if (flag) {
                    Intent stateService = new Intent(this,
                            LogService.class);
                    this.stopService(stateService);
                    System.exit(-1);
                } else {
                }
                break;
            case DIALOG_LOGOUT:
                if (flag) {
                    userInfoDialog.dismiss();
                    Intent stateService = new Intent(this,
                            LogService.class);
                    this.stopService(stateService);
                    GoActivityWithFinishing(LoginActivity.class, null);
                }
                break;
            default:
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            dialogType = DIALOG_EXIT;
            confirmDialog.setType(CustomDialog.TYPE_QUESTION);
            confirmDialog.setMessage("确定退出本系统吗？");
            confirmDialog.show();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onEventMainThread(Event.MsgEvent msgEvent) {
        if (msgEvent.type == EVENT_DOWNLOAD
                && msgEvent.fromClass == MainMenuActivity.class) {
            String result = (String)msgEvent.values.get("result");
            String data = (String)msgEvent.values.get("data");
            if (result.equals("-1")) {
                loadingDialog.hideLoading();
                alertDialog.setType(CustomDialog.TYPE_ERROR);
                alertDialog.setMessage("连接服务器异常，上报失败！");
                alertDialog.show();
            }
        }
    }
    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }
}
