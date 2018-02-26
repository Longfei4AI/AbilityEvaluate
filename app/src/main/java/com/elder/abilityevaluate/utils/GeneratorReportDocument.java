package com.elder.abilityevaluate.utils;

import android.app.Activity;
import android.os.Environment;
import android.view.View;

import com.elder.abilityevaluate.entity.EvaluationReport;

import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.usermodel.Range;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @Author: wlf
 * @Description: GeneratorReportDocument
 * @Time: 2018/2/26 15:06
 */

public class GeneratorReportDocument {
    // 创建生成的文件地址
    private static final String newPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc/test.doc";
    private static final String filePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/doc";
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

            HWPFDocument hdt = new HWPFDocument(in);
            // Fields fields = hdt.getFields();
            // 读取word文本内容
            Range range = hdt.getRange();
            // System.out.println(range.text());

            // 替换文本内容
            for (Map.Entry<String, String> entry : map.entrySet()) {
                range.replaceText(entry.getKey(), entry.getValue());
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
    }

    /**
     * 这个是Button的点击事件
     * @param
     */
    public void Save(EvaluationReport report) {
        try {
            //从assets读取我们的Word模板
            InputStream is = this.activity.getAssets().open("model.doc");
            //创建生成的文件
            File newFile = new File(newPath);
            Map<String, String> map = new HashMap<String, String>();
            map.put("$name$", "张磊");
            map.put("$place$", "济南");
            map.put("$date$", "2018-02-05");
            writeDoc(is, newFile, map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
