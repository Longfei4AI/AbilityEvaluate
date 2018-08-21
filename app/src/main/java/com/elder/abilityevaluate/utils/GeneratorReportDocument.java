package com.elder.abilityevaluate.utils;

import android.app.Activity;
import android.location.Location;
import android.os.Environment;

import com.elder.abilityevaluate.R;
import com.elder.abilityevaluate.entity.BaseInformation;
import com.elder.abilityevaluate.entity.Evaluation;
import com.elder.abilityevaluate.entity.EvaluationReport;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wlf
 * @Description: GeneratorReportDocument
 * @Time: 2018/2/26 15:06
 */

public class GeneratorReportDocument {
    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/评估报告/";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/评估报告";
    private static String fileName = "test.doc";
    private String baseId;
    private static Activity activity = null;
    private static GeneratorReportDocument document = null;
    public static GeneratorReportDocument getInstance(Activity acti){
        activity = acti;
        return document == null ? new GeneratorReportDocument() : document;
    }
    /**
     * newFile 生成文件
     * map 要填充的数据
     */
    public void writeDoc(InputStream in, File newFile, Map<String, String> map) {

        try {

            File file = new File(filePath);
            if (!file.exists()) {
                file.mkdirs();
            }
            if(newFile.exists()){
                newFile.delete();
            }

            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            //hdt.getPicturesTable().getAllPictures();

            // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue() == null ? "" : entry.getValue());
            }
            ByteArrayOutputStream ostream = new ByteArrayOutputStream();
            FileOutputStream out = new FileOutputStream(newFile, true);
            hdt.write(ostream);
            // 输出字节流
            out.write(ostream.toByteArray());
            out.close();
            ostream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        /*
        try{
            writePicture(newFile.getAbsolutePath());
        }catch (Exception e){
            e.printStackTrace();
        }
        */
    }
    /**
    * @Author: wlf
    * @Time: 2018-03-18 19:52
    * @Desc: 保存图片到word
    * @Params:
    * @Return:
    */
    /*
    public void writePicture(String filePath) throws Exception {
        Map<String, Object> datas = new HashMap<String, Object>() {
            {
                //本地图片
                put("localPicture", new PictureRenderData(100, 120, GlobalInfo.PIC_PATH + "/" + baseId + "_watch.jpg"));
                //网路图片
                //put("urlPicture", new PictureRenderData(100, 100, ".png", BytePictureUtils.getUrlByteArray("https://avatars3.githubusercontent.com/u/1394854?v=3&s=40")));
            }
        };

        XWPFTemplate template = XWPFTemplate.compile(filePath)
                .render(datas);

        FileOutputStream out = new FileOutputStream(filePath);
        template.write(out);
        out.flush();
        out.close();
        template.close();
    }
    */
    /**
     * 生产评估报告word文档
     * @param
     */
    public void Save(EvaluationReport report) {
        try {
            BaseInformation baseInformation = report.getBaseInformation(activity);
            baseId = baseInformation.getBaseInfoId();
            Evaluation evaluation = report.getEvaluation(activity);
            //从assets读取Word模板
            InputStream is = this.activity.getAssets().open("model.doc");
            //创建生成的文件
            fileName = report.getE_name() + "_" + DateUtils.getCurDateTime() + ".doc";
            File newFile = new File(newPath + fileName);
            Map<String, String> map = new HashMap<String, String>();
            map.put("$report_code$", report.getE_code());
            map.put("$name$", report.getE_name());
            map.put("$place$", report.getE_location());
            map.put("$date$", report.getE_time());
            map.put("$type$",report.getE_type());


            map.put("$b_1_grade$",activity.getString(report.getB_grade_strId(report.getB_1_grade())));
            map.put("$b_2_grade$",activity.getString(report.getB_grade_strId(report.getB_2_grade())));
            map.put("$b_3_grade$",activity.getString(report.getB_grade_strId(report.getB_3_grade())));
            map.put("$b_4_grade$",activity.getString(report.getB_grade_strId(report.getB_4_grade())));

            map.put("$e_grade_pre$",activity.getString(report.getE_grade_strId(report.getE_grade_pre())));
            map.put("$grade_changeItem$",report.getGrade_change_item());
            map.put("$e_grade_final$",activity.getString(report.getE_grade_strId(report.getE_grade_final())));

            map.put("$A_1_1$",baseInformation.getA_1_1());
            map.put("$A_1_2$",baseInformation.getA_1_2());

            String a_1_3 = baseInformation.getA_1_3() == null ? "" : baseInformation.getA_1_3();
            switch (a_1_3){
                case "1":
                    map.put("$A_1_3$",activity.getString(R.string.a_1_3_1).substring(2));
                    break;
                case "2":
                    map.put("$A_1_3$",activity.getString(R.string.a_1_3_2).substring(2));
                    break;
                case "3":
                    map.put("$A_1_3$",activity.getString(R.string.a_1_3_3).substring(2));
                    break;
                case "4":
                    map.put("$A_1_3$",activity.getString(R.string.a_1_3_4).substring(2));
                    break;
            }


            map.put("$A_2_1$",baseInformation.getA_2_1());

            String a_2_2 = baseInformation.getA_2_2() == null ? "" : baseInformation.getA_2_2();
            switch (a_2_2){
                case "1":
                    map.put("$A_2_2$",activity.getString(R.string.a_2_2_1));
                    break;
                case "2" :
                    map.put("$A_2_2$",activity.getString(R.string.a_2_2_2));
                    break;
            }
            map.put("$A_2_3$",baseInformation.getA_2_3());
            map.put("$A_2_4$",baseInformation.getA_2_4());
            map.put("$A_2_5$",baseInformation.getA_2_5());

            String a_2_6 = baseInformation.getA_2_6() == null ? "" : baseInformation.getA_2_6();
            switch (a_2_6){
                case "1":
                    map.put("$A_2_6$",activity.getString(R.string.a_2_6_1_));
                    break;
                case "2" :
                    String a_2_6Str = ( baseInformation.getA_2_6_1() == null || baseInformation.getA_2_6_1().equals("") ) ?
                            activity.getString(R.string.a_2_6_2_) : baseInformation.getA_2_6_1();
                    map.put("$A_2_6$",a_2_6Str);
                    break;
            }

            String a_2_7 = baseInformation.getA_2_7() == null ? "" : baseInformation.getA_2_7();
            switch (a_2_7){
                case "1":
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_1));
                    break;
                case "2" :
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_2));
                    break;
                case "3" :
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_3));
                    break;
                case "4" :
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_4));
                    break;
                case "5" :
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_5));
                    break;
                case "6" :
                    map.put("$A_2_7$",activity.getString(R.string.a_2_7_6));
                    break;
            }

            String a_2_8 = baseInformation.getA_2_8() == null ? "" : baseInformation.getA_2_8();
            switch (a_2_8){
                case "1":
                    map.put("$A_2_8$",activity.getString(R.string.a_2_8_1_));
                    break;
                case "2" :
                    map.put("$A_2_8$",baseInformation.getA_2_8_1());
                    break;
            }

            String a_2_9 = baseInformation.getA_2_9() == null ? "" : baseInformation.getA_2_9();
            switch (a_2_9){
                case "1":
                    map.put("$A_2_9$",activity.getString(R.string.a_2_9_1));
                    break;
                case "2" :
                    map.put("$A_2_9$",activity.getString(R.string.a_2_9_2));
                    break;
                case "3" :
                    map.put("$A_2_9$",activity.getString(R.string.a_2_9_3));
                    break;
                case "4" :
                    map.put("$A_2_9$",activity.getString(R.string.a_2_9_4));
                    break;
                case "5" :
                    map.put("$A_2_9$",activity.getString(R.string.a_2_9_5));
                    break;
            }

            String a_2_10 = baseInformation.getA_2_10() == null ? "" : baseInformation.getA_2_10();
            switch (a_2_10){
                case "1":
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_1));
                    break;
                case "2" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_2));
                    break;
                case "3" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_3));
                    break;
                case "4" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_4));
                    break;
                case "5" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_5));
                    break;
                case "6" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_6));
                    break;
                case "7" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_7));
                    break;
                case "8" :
                    map.put("$A_2_10$",activity.getString(R.string.a_2_10_8));
                    break;
            }

            String a_2_11 = baseInformation.getA_2_11() == null ? "" : baseInformation.getA_2_11();
            StringBuffer strBuff_2_11 =  new StringBuffer();
            if(a_2_11.contains("1")) strBuff_2_11.append(activity.getString(R.string.a_2_11_1_)).append(";");
            if(a_2_11.contains("2")) strBuff_2_11.append(activity.getString(R.string.a_2_11_2)).append(";");
            if(a_2_11.contains("3")) strBuff_2_11.append(activity.getString(R.string.a_2_11_3)).append(";");
            if(a_2_11.contains("4")) strBuff_2_11.append(activity.getString(R.string.a_2_11_4)).append(";");
            if(a_2_11.contains("5")) strBuff_2_11.append(activity.getString(R.string.a_2_11_5)).append(";");
            if(a_2_11.contains("6")) strBuff_2_11.append(activity.getString(R.string.a_2_11_6)).append(";");
            if(a_2_11.contains("7")) strBuff_2_11.append(activity.getString(R.string.a_2_11_7)).append(";");
            if(a_2_11.contains("8")) strBuff_2_11.append(baseInformation.getA_2_11_1()).append(";");
            map.put("$A_2_11$",strBuff_2_11.toString());

            String a_2_12 = baseInformation.getA_2_12() == null ? "" : baseInformation.getA_2_12();
            StringBuffer strBuff_a_2_12 = new StringBuffer();
            if(a_2_12.contains("1")) strBuff_a_2_12.append(activity.getString(R.string.a_2_12_1)).append(";");
            if(a_2_12.contains("2")) strBuff_a_2_12.append(activity.getString(R.string.a_2_12_2)).append(";");
            if(a_2_12.contains("3")) strBuff_a_2_12.append(activity.getString(R.string.a_2_12_3)).append(";");
            if(a_2_12.contains("4")) strBuff_a_2_12.append(activity.getString(R.string.a_2_12_4)).append(";");
            map.put("$A_2_12$",strBuff_a_2_12.toString());

            String a_2_13_1 = baseInformation.getA_2_13_1() == null ? "" : baseInformation.getA_2_13_1();
            switch (a_2_13_1){
                case "1":
                    map.put("$A_2_13_1$",activity.getString(R.string.a_2_13_1_1));
                    break;
                case "2" :
                    map.put("$A_2_13_1$",activity.getString(R.string.a_2_13_1_2));
                    break;
                case "3" :
                    map.put("$A_2_13_1$",activity.getString(R.string.a_2_13_1_3));
                    break;
                case "4" :
                    map.put("$A_2_13_1$",activity.getString(R.string.a_2_13_1_4));
                    break;
            }

            String a_2_13_2 = baseInformation.getA_2_13_2() == null ? "" : baseInformation.getA_2_13_2();
            switch (a_2_13_2){
                case "1":
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_1));
                    break;
                case "2" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_2));
                    break;
                case "3" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_3));
                    break;
                case "4" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_4));
                    break;
                case "5" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_5));
                    break;
                case "6" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_6));
                    break;
                case "7" :
                    map.put("$A_2_13_2$",activity.getString(R.string.a_2_13_2_7));
                    break;
            }
            map.put("$A_2_13_3$",baseInformation.getA_2_13_3());
            map.put("$A_2_14_1$",baseInformation.getA_2_14_1()+"次");
            map.put("$A_2_14_2$",baseInformation.getA_2_14_2()+"次");
            map.put("$A_2_14_3$",baseInformation.getA_2_14_3()+"次");
            map.put("$A_2_14_4$",baseInformation.getA_2_14_4()+"次");
            map.put("$A_2_14_5$",baseInformation.getA_2_14_5());

            map.put("$A_3_1$",baseInformation.getA_3_1());

            String a_3_2 = baseInformation.getA_3_2() == null ? "" : baseInformation.getA_3_2();
            switch (a_3_2){
                case "1":
                    map.put("$A_3_2$",activity.getString(R.string.a_3_2_1).substring(2));
                    break;
                case "2" :
                    map.put("$A_3_2$",activity.getString(R.string.a_3_2_2).substring(2));
                    break;
                case "3" :
                    map.put("$A_3_2$",activity.getString(R.string.a_3_2_3).substring(2));
                    break;
                case "4" :
                    map.put("$A_3_2$",activity.getString(R.string.a_3_2_4).substring(2));
                    break;
                case "5" :
                    map.put("$A_3_2$",activity.getString(R.string.a_3_2_5).substring(2));
                    break;
            }
            map.put("$A_3_3$",baseInformation.getA_3_3());
            map.put("$A_3_4$",baseInformation.getA_3_4());

            map.put("$B_1_1$",evaluation.getB_1_1()+"");
            map.put("$B_1_2$",evaluation.getB_1_2()+"");
            map.put("$B_1_3$",evaluation.getB_1_3()+"");
            map.put("$B_1_4$",evaluation.getB_1_4()+"");
            map.put("$B_1_5$",evaluation.getB_1_5()+"");
            map.put("$B_1_6$",evaluation.getB_1_6()+"");
            map.put("$B_1_7$",evaluation.getB_1_7()+"");
            map.put("$B_1_8$",evaluation.getB_1_8()+"");
            map.put("$B_1_9$",evaluation.getB_1_9()+"");
            map.put("$B_1_10$",evaluation.getB_1_10()+"");
            map.put("$B_1_11$",evaluation.getB_1_score()+"");
            map.put("$B_1$",evaluation.getB_1_level()+"");

            map.put("$B_2_1$",evaluation.getB_2_1()+"");
            map.put("$B_2_2$",evaluation.getB_2_2()+"");
            map.put("$B_2_3$",evaluation.getB_2_3()+"");
            map.put("$B_2_4$",evaluation.getB_2_score()+"");
            map.put("$B_2$",evaluation.getB_2_level()+"");

            map.put("$B_3_1$",evaluation.getB_3_1()+"");
            map.put("$B_3_2$",evaluation.getB_3_2()+"");
            map.put("$B_3_3$",evaluation.getB_3_3()+"");
            map.put("$B_3_4$",evaluation.getB_3_4()+"");
            map.put("$B_3$",evaluation.getB_3_level()+"");

            map.put("$B_4_1$",evaluation.getB_4_1()+"");
            map.put("$B_4_2$",evaluation.getB_4_2()+"");
            map.put("$B_4_3$",evaluation.getB_4_3()+"");
            map.put("$B_4_4$",evaluation.getB_4_4()+"");
            map.put("$B_4_5$",evaluation.getB_4_5()+"");
            map.put("$B_4_6$",evaluation.getB_4_score()+"");
            map.put("$B_4$",evaluation.getB_4_level()+"");

            writeDoc(is, newFile, map);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
