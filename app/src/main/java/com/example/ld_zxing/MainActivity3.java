package com.example.ld_zxing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class MainActivity3 extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        System.out.println("xxxxxxxxxxxxxxxxxx" + Environment.getExternalStorageDirectory());

      /*  try {
            createXML();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }


    private  void createXML() throws Exception {
        File file =  new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.xls");
        System.out.println("xxxxxxxxxxxxxxxxxx" + Environment.getExternalStorageDirectory());

        // 文件存在先删除
  /*      if (file.exists()) {
            file.delete();
        }*/

        if (!file.exists()) {
            file.createNewFile();
            System.out.println("文件创建成功!");
        }



        try {
            // 输出Excel的路径
            // 新建一个文件
            OutputStream os = new FileOutputStream(file);
            // 创建Excel工作簿
            WritableWorkbook mWritableWorkbook = Workbook.createWorkbook(os);
            // 创建Sheet表
            WritableSheet mWritableSheet =  mWritableWorkbook.createSheet("第一张工作表", 0);
        } catch (IOException e) {
            e.printStackTrace();
        }

      /* WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        WritableSheet sheet = wwb.createSheet("订单", 0);
        Label orderNum = new Label(0, 1, "xxxxx");
        sheet.addCell(orderNum);*/
    }
}