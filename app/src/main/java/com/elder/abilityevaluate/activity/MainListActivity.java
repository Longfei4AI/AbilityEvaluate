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
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.entity.EvaluationReport;
import com.elder.abilityevaluate.eventBus.Event;
import com.elder.abilityevaluate.service.LogService;
import com.elder.abilityevaluate.utils.DataBaseHelper;
import com.elder.abilityevaluate.utils.DateUtils;
import com.elder.abilityevaluate.utils.GlobalInfo;
import com.elder.abilityevaluate.utils.LocalMethod;
import com.elder.abilityevaluate.utils.MyLog;
import com.elder.abilityevaluate.widget.ClearDataDialog;
import com.elder.abilityevaluate.widget.CustomDialog;
import com.elder.abilityevaluate.widget.CustomLoadingDialog;
import com.elder.abilityevaluate.widget.CustomToast;
import com.elder.abilityevaluate.widget.UserInfoDialog;
import com.lidroid.xutils.db.sqlite.Selector;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import de.greenrobot.event.EventBus;

public class MainListActivity extends BasicActiviy {
    public static final int DIALOG_CLEAR_DATA = 0x01;
    public static final int DIALOG_EXIT = 0x02;
    public final static int EVENT_DOWNLOAD = 0x0A;
    // 基本信息
    private static final String MODEL_BASE_INFORMATION = "base_information";
    // 能力评估
    private static final String MODEL_ABILITY_EVALUATE = "ability_evaluate";
    // 评估报告
    private static final String MODEL_REPORT_EVALUATE = "report_evaluate";
    // 数据清理
    private static final String MODEL_DATA_CLEAR = "data_clear";
    // 参数设置
    private static final String MODEL_SETTING = "setting";
    // 数据上报
    private static final String MODEL_UPLOAD = "upload";
    private DownloadThread dowloadThread;
    private CustomLoadingDialog loadingDialog;
    private CustomDialog confirmDialog;
    private CustomDialog alertDialog;
    private ClearDataDialog clearDataDialog;
    private String corpCode = "";
    private ListView listview = null;
    private TextView userName;
    private TextView register_detailTV;
    private TextView evaluate_detailTV;
    private TextView left_detailTV;
    private SharedPreferences spf;
    private int dialogType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showInitedLoading = false;
        setContentView(R.layout.main_list_activity);
        EventBus.getDefault().register(this);
    }

    @Override
    public void init() {
        listview =  findViewById(R.id.menu_list);
        userName =  findViewById(R.id.tv_username);
        register_detailTV = findViewById(R.id.register_detail);
        evaluate_detailTV = findViewById(R.id.evaluate_detail);
        left_detailTV = findViewById(R.id.left_detail);
        spf = getSharedPreferences(
                GlobalSetting.PREFERENCE_NAME, Context.MODE_PRIVATE);
        corpCode = spf.getString(PreferenceParams.CORP_CODE, "");

    }

    @Override
    public void initFinished() {
        ArrayList<HashMap<String, Object>> meumList = new ArrayList<HashMap<String, Object>>();
        initializeFunction(meumList);
        SimpleAdapter saMenuItem = new SimpleAdapter(this,
                meumList, // 数据源
                R.layout.model_item, // xml实现
                new String[] { "ItemImage", "ItemText", "ItemId", "ItemContent" }, // 对应map的Key
                new int[] { R.id.ItemImage, R.id.ItemText, R.id.ItemId,
                        R.id.ItemContent }); // 对应R的Id
        // 添加Item到网格中
        listview.setAdapter(saMenuItem);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
                                    long arg3) {
                TextView tv = (TextView) arg1.findViewById(R.id.ItemId);
                functionClick(tv.getText() + "");
            }
        });
        userName.setText(spf.getString(PreferenceParams.USER_NAME, "未登录"));
        loadingDialog = new CustomLoadingDialog(this);

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

        //今日登记人数
        List<BaseInformation> registerTodayList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class).where("registerTime","like", DateUtils.getCurDate()+"%"));

        //累计登记人数
        List<BaseInformation> registerAllList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class));

        //今日评估人数
        List<BaseInformation> evaluateTodayList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class).where("a_1_2","=", DateUtils.getCurDate())
                        .and("state","=",BaseInformation.EVALUATED));

        //累计评估人数
        List<BaseInformation> evaluateAllList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class).where("state","=",BaseInformation.EVALUATED));
        //剩余评估人数
        List<BaseInformation> leftList = DataBaseHelper.getInstance(this,BaseInformation.class)
                .getListBySelector(Selector.from(BaseInformation.class).where("state","=",BaseInformation.NOT_EVALUATED));
        register_detailTV.setText( String.format(getResources().getString(R.string.register_detail),registerTodayList.size(),registerAllList.size()) );
        evaluate_detailTV.setText( String.format(getResources().getString(R.string.evaluate_detail),evaluateTodayList.size(),evaluateAllList.size()) );
        left_detailTV.setText( String.format(getResources().getString(R.string.left_detail),leftList.size()) );

        clearDataDialog = new ClearDataDialog(this,new ClearDataDialog.CallBack() {
            @Override
            public void clear() {
                dialogType = DIALOG_CLEAR_DATA;
                confirmDialog.setType(CustomDialog.TYPE_QUESTION);
                confirmDialog.setMessage("数据清理后将不能恢复，确定要清理所有数据？");
                confirmDialog.show();
            }
        });
        clearDataDialog.registerSize = registerAllList.size();
        clearDataDialog.evaluatedSize = evaluateAllList.size();
        clearDataDialog.leftSize = leftList.size();
    }
    /**
     * 初始化功能模块
     * @param list
     */
    private void initializeFunction(ArrayList<HashMap<String, Object>> list) {
        HashMap<String, Object> baseInfo = new HashMap<String, Object>();
        baseInfo.put("ItemText", getResources().getString(R.string.base_information));
        baseInfo.put("ItemImage", R.drawable.role);
        baseInfo.put("ItemId", MODEL_BASE_INFORMATION);
        baseInfo.put("ItemContent", "登记待评估老人的基本信息。");
        list.add(baseInfo);

        HashMap<String, Object> evaluate = new HashMap<String, Object>();
        evaluate.put("ItemText", getResources().getString(R.string.ability_evaluate));
        evaluate.put("ItemImage", R.drawable.evaluate);
        evaluate.put("ItemId", MODEL_ABILITY_EVALUATE);
        evaluate.put("ItemContent", "开始进行能力评估，并记录评估内容明细。");
        list.add(evaluate);

        HashMap<String, Object> report = new HashMap<String, Object>();
        report.put("ItemText", getResources().getString(R.string.report_evaluate));
        report.put("ItemImage", R.drawable.view);
        report.put("ItemId", MODEL_REPORT_EVALUATE);
        report.put("ItemContent", "查看和导出评估报告。");
        list.add(report);

        HashMap<String, Object> dataClear = new HashMap<String, Object>();
        dataClear.put("ItemText", getResources().getString(R.string.data_clear));
        dataClear.put("ItemImage", R.drawable.trash);
        dataClear.put("ItemId", MODEL_DATA_CLEAR);
        dataClear.put("ItemContent", "清理所有历史数据，清除后，数据不能恢复。");
        list.add(dataClear);

        HashMap<String, Object> setting = new HashMap<String, Object>();
        setting.put("ItemText", getResources().getString(R.string.setting));
        setting.put("ItemImage", R.drawable.setting_h);
        setting.put("ItemId", MODEL_SETTING);
        setting.put("ItemContent", "设置系统参数，上报服务器基本信息等。");
        list.add(setting);
    }
    /**
     * 点击响应事件
     * @param
     */
    private void functionClick(String modelFlag) {
        if (modelFlag.equals(MODEL_BASE_INFORMATION)) {// 基本信息
            GoActivityWithFinishing(BaseInformationListActivity.class,null);
        } else if (modelFlag.equals(MODEL_ABILITY_EVALUATE)) {// 能力评估
            GoActivityWithFinishing(EvaluationListActivity.class,null);
        } else if (modelFlag.equals(MODEL_REPORT_EVALUATE)) {// 评估报告
            GoActivityWithFinishing(EvaluateReportListActivity.class,null);
        }else if (modelFlag.equals(MODEL_DATA_CLEAR)) {// 数据清理
            clearData();
        }else if (modelFlag.equals(MODEL_SETTING)) {// 参数设置
            GoActivityWithFinishing(SettingActivity.class,null);
        }else if (modelFlag.equals(MODEL_UPLOAD)) {// 数据上报
            //GoActivityWithFinishing(DownloadActivity.class,null);
            if(!GlobalInfo.isInternetAvailable(this)){
                alertDialog.setMessage("网络连接异常，不能进行数据上报！");
                alertDialog.show();
                return;
            }
            dowloadThread = new DownloadThread();
            dowloadThread.start();
            loadingDialog.setMsg("正在上报数据，请稍候...");
            loadingDialog.showLoading();
        }
    }

    /**
     * @Title: clearData
     * @Description: 数据清理对话框
     * @return void    返回类型
     * @throws
     */
    public void clearData(){
        clearDataDialog.show();
    }
    /**
     * @Title: clearHistoryData
     * @Description: 数据清理
     * @return void    返回类型
     * @throws
     */
    public void clearHistoryData(){
        DataBaseHelper.getInstance(this,EvaluationReport.class).deleteAll(EvaluationReport.class);
        DataBaseHelper.getInstance(this,Evaluation.class).deleteAll(Evaluation.class);
        DataBaseHelper.getInstance(this,BaseInformation.class).deleteAll(BaseInformation.class);
        File file = new File(GlobalInfo.PIC_PATH);
        if(file.exists()){
            File[] delFile = file.listFiles();
            for (File f : delFile){
                f.delete();
            }
        }
        CustomToast.show(this,"数据清理完成！",1);
    }
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
            case DIALOG_CLEAR_DATA:
                if (flag) {
                    clearDataDialog.dismiss();
                    Intent stateService = new Intent(this,
                            LogService.class);
                    this.stopService(stateService);
                    clearHistoryData();
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
    class DownloadThread extends Thread {
        @Override
        public void run() {
            LocalMethod localMethod = new LocalMethod(MainListActivity.this);
            Map<String, Object> result = new HashMap<String, Object>();
            Event.MsgEvent event = new Event.MsgEvent(EVENT_DOWNLOAD, result);
            event.fromClass = MainMenuActivity.class;

            String employes = localMethod.getArcEmployees(corpCode);
            MyLog.i("employes", employes);
            if(employes.equals("-1")){
                result.put("result", "-1");
                EventBus.getDefault().post(event);
            }else{
                result.put("result", "2");
                result.put("data", employes);
                EventBus.getDefault().post(event);

                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void onEventMainThread(Event.MsgEvent msgEvent) {
        if (msgEvent.type == EVENT_DOWNLOAD
                && msgEvent.fromClass == MainMenuActivity.class) {
            String result = (String)msgEvent.values.get("result");
            String data = (String)msgEvent.values.get("data");
            if (result.equals("-1")) {
                dowloadThread = null;
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
