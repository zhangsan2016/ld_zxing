package com.example.ld_zxing;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.ld_zxing.util.CustomToast;
import com.example.ld_zxing.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class MainActivity2 extends Activity implements QRCodeView.Delegate {
    private static final String TAG = MainActivity2.class.getSimpleName();
    private static final int REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY = 666;

    private ZXingView mZXingView;
    private TextView tvlistsize;
  //  private File file =  new File(getFilesDir().getAbsolutePath(), "二维码扫描数据.xls");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        tvlistsize = findViewById(R.id.tv_listsize);
        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);


        // 获取权限
            checkPermission();
   /*     if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)){
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA},REQUEST_CODE_CHOOSE_QRCODE_FROM_GALLERY);
        }*/



        try {
            createXML();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxxxxxxxxx createXML() 出错" + e.getMessage().toString());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();

        String strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",new String());
        List<String>  datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());
        if(datalist != null){
            setListSize(datalist.size());
        }else{
            setListSize(0);
        }



    }

    @Override
    protected void onResume() {
        super.onResume();
        openQrcodeScan();

    }

    /**
     *  打开二维码扫描
     */
    private void openQrcodeScan() {
        mZXingView.stopCamera();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别

        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    List<String> data =  new ArrayList<>();
    Gson gson = new Gson();
    @Override
    public void onScanQRCodeSuccess(String result) {
        Log.i(TAG, "result:" + result);
        // setTitle("扫描结果为：" + result);
       // Toast.makeText(this,result,Toast.LENGTH_SHORT).show();

    /*   if(data.contains(result)){
           Toast.makeText(this,"当前数据已经存在！",Toast.LENGTH_SHORT).show();
       }else{
         *//*  data.add(result);
           String[] dataString = data.toArray(new String[data.size()]);
           SharedPreferencesUtils.setParam(MainActivity2.this,"data",dataString);*//*

           data.add(result);
           String strJson = gson.toJson(data);
           SharedPreferencesUtils.setParam(MainActivity2.this,"data",strJson);

          strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",strJson);
          List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

           System.out.println("xxxxxxxxxxxxxxxxx datalist = " + datalist.get(0));
       }*/

        String strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",new String());
        List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());
        data.clear();
        data = datalist;

        try {
            Thread.currentThread().sleep(200);//毫秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(data==null){
            data = new ArrayList<>();
            data.add(result);
            strJson = gson.toJson(data);
            SharedPreferencesUtils.setParam(MainActivity2.this,"data",strJson);

            strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",strJson);
            datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

            System.out.println("xxxxxxxxxxxxxxxxx datalist = " + datalist.get(0) + "leng() = " + datalist.size());
         //   Toast.makeText(MainActivity2.this,"添加成功！",Toast.LENGTH_SHORT).show();
           // showToastTop("添加成功！",Toast.LENGTH_LONG);
            CustomToast.showToast(this,"添加成功！",1000);

            setListSize(data.size());
            mZXingView.startSpot(); // 开始识别
            return;
        }

        if(!data.contains(result)){
            data.add(result);
            strJson = gson.toJson(data);
            SharedPreferencesUtils.setParam(MainActivity2.this,"data",strJson);

            strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",strJson);
            datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

            System.out.println("xxxxxxxxxxxxxxxxx datalist = " + datalist.get(0) + "leng() = " + datalist.size());
       //     Toast.makeText(MainActivity2.this,"添加成功！",Toast.LENGTH_LONG).show();
          //  showToastTop("添加成功！",Toast.LENGTH_LONG);
            CustomToast.showToast(this,"添加成功！",1000);
        }else{
           // Toast.makeText(MainActivity2.this,"当前数据已经保存，不再次保存！！",Toast.LENGTH_SHORT).show();
            // showToastTop("当前数据已经保存，不再次保存！！",Toast.LENGTH_SHORT);
            CustomToast.showToast(this,"当前数据已经保存",1000);

        }

        String strJson2 = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",new String());
        List<String>  datalist2 = gson.fromJson(strJson2, new TypeToken<List<String>>() {}.getType());
        if(datalist2 != null){
            setListSize(datalist2.size());
        }else{
            setListSize(0);
        }


        mZXingView.startSpot(); // 开始识别

/*        new Thread(new Runnable() {
            @Override
            public void run() {
                this.s
            }
        }).start();*/

    }


 private void  showToastTop(String str, int length){
      Display display = getWindowManager().getDefaultDisplay();
      // 获取屏幕高度
      int height = display.getHeight();
      Toast toast = Toast.makeText(this, str,length);
      // 这里给了一个1/4屏幕高度的y轴偏移量
      toast.setGravity(Gravity.TOP, 0, 50);
      toast.show();
  }

    private void setListSize(int size) {
        tvlistsize.setText(size+"");
    }


    private  void createXML() throws Exception {
      //  File file =  new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.xls");
        File file =  new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.txt");
        System.out.println("xxxxxxxxxxxxxxxxxx" + Environment.getExternalStorageDirectory());

        // 文件存在先删除
  /*      if (file.exists()) {
            file.delete();
        }*/

        if (!file.exists()) {
            file.createNewFile();
            System.out.println("文件创建成功!");
        }


     /*
        // xml读写
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            bufferedWriter.write("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx\r\n");
            bufferedWriter.write("222222222222222222222222222222222\r\n");
            bufferedWriter.write("333333333333333333333\r\n");
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxxxx  IOException!" + e.getMessage().toString());
        }*/



      /*  try {
            // 输出Excel的路径
            // 新建一个文件
            OutputStream os = new FileOutputStream(file);
            // 创建Excel工作簿
            WritableWorkbook   mWritableWorkbook = Workbook.createWorkbook(os);
            // 创建Sheet表
            WritableSheet  mWritableSheet =  mWritableWorkbook.createSheet("第一张工作表", 0);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("xxxxxxxxxxxxxxxxx 出错 " + e.getMessage().toString());
        }
*/
      /* WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        WritableSheet sheet = wwb.createSheet("订单", 0);
        Label orderNum = new Label(0, 1, "xxxxx");
        sheet.addCell(orderNum);*/
    }

    public void writeText(View view) {

        File file =  new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.txt");
        // xml读写
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            String c ="";
            String  strJson = (String) SharedPreferencesUtils.getParam(MainActivity2.this,"data",c);
            List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());
            for (int i = 0; i < datalist.size(); i++) {
                bufferedWriter.write(datalist.get(i)+"\r\n");
            }

            bufferedWriter.close();
            Toast.makeText(MainActivity2.this,"写入成功！",Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity2.this,"写入失败！！",Toast.LENGTH_SHORT).show();
        }

    }




    private  void writeXML(String result) throws Exception {
        File file =  new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.xls");
        // 文件存在先删除
        if (file.exists()) {
            file.delete();
        }

        if (!file.exists()) {
            file.mkdirs();
        }




        WritableWorkbook wwb;
        OutputStream os = new FileOutputStream(file);
        wwb = Workbook.createWorkbook(os);
        WritableSheet sheet = wwb.createSheet("订单", 0);
        Label orderNum = new Label(1, 1, "sfsdfsdfsdkhfjhsdfhksdhfkjhksdhfkjhddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddfsdf");
        sheet.addCell(orderNum);

    }



    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }


    public void checkPermission() {
        boolean isGranted = true;
        if (android.os.Build.VERSION.SDK_INT >= 23) {
            if (this.checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                //如果没有写sd卡权限
                isGranted = false;
            }
            if (this.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                isGranted = false;
            }
            Log.i("cbs","isGranted == "+isGranted);
            if (!isGranted) {
                this.requestPermissions(
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission
                                .ACCESS_FINE_LOCATION,
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA},
                        102);
            }
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for (int i = 0; i < grantResults.length; i++) {
            System.out.println("权限申请 xxxxxxxxxx " + grantResults[i]  + "xxxxxxx " + grantResults.length);
        }
        System.out.println("权限申请 bushipiliang xxxxxxxxxx " + requestCode);


        openQrcodeScan();


        // System.out.println("xxxxx");
      /*  if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        } else {
        }*/
    }


    public void dataList(View view) {

        Intent intent = new Intent(this,DataActivity.class);
        startActivity(intent);

    }
}
