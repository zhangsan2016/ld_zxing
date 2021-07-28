package com.example.ld_zxing;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.ailiwean.core.Result;
import com.ailiwean.core.view.style1.NBZxingView;
import com.example.ld_zxing.util.CustomToast;
import com.example.ld_zxing.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


public class MainActivity3 extends AppCompatActivity {

    private  CusScanView cusScanView;
    private List<String> data =  new ArrayList<>();
    private Gson gson = new Gson();
    private TextView tvlistsize;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            winParams.flags |= bits;
        }
        // 隐藏状态栏
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main3);


       /* NBZxingView NBZxingView = new NBZxingView(this) {
            @Override
            public void resultBack(Result content) {
                Toast.makeText(getContext(), content.getText(), Toast.LENGTH_LONG).show();
            }
        };
        NBZxingView.synchLifeStart(this);*/


       /* ZxingFragment fragment = new ZxingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.parent, fragment).commit();*/

        // 获取权限
        checkPermission();

        initView();




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
            }else{
                initScan();
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

        initScan();
        // System.out.println("xxxxx");
      /*  if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
        } else {
        }*/
    }

    private void initScan() {
        cusScanView = this.findViewById(R.id.zxingview);
        cusScanView.synchLifeStart(this);
        cusScanView.setListeren(new CusScanView.MyInterface() {
            @Override
            public void scanResult(@NotNull String content) {
                Toast.makeText(MainActivity3.this, content, Toast.LENGTH_LONG).show();

                String strJson = (String) SharedPreferencesUtils.getParam(MainActivity3.this,"data",new String());
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
                    data.add(content);
                    strJson = gson.toJson(data);
                    SharedPreferencesUtils.setParam(MainActivity3.this,"data",strJson);

                    strJson = (String) SharedPreferencesUtils.getParam(MainActivity3.this,"data",strJson);
                    datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

                    //   Toast.makeText(MainActivity2.this,"添加成功！",Toast.LENGTH_SHORT).show();
                    // showToastTop("添加成功！",Toast.LENGTH_LONG);
                    CustomToast.showToast(MainActivity3.this,"添加成功！",1000);

                    setListSize(data.size());
                    cusScanView.unProscibeCamera(); // 开始识别
                    return;
                }

                if(!data.contains(content)){
                    data.add(content);
                    strJson = gson.toJson(data);
                    SharedPreferencesUtils.setParam(MainActivity3.this,"data",strJson);

                    strJson = (String) SharedPreferencesUtils.getParam(MainActivity3.this,"data",strJson);
                    datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {}.getType());

                    System.out.println("xxxxxxxxxxxxxxxxx datalist = " + datalist.get(0) + "leng() = " + datalist.size());
                    //     Toast.makeText(MainActivity2.this,"添加成功！",Toast.LENGTH_LONG).show();
                    //  showToastTop("添加成功！",Toast.LENGTH_LONG);
                    CustomToast.showToast(MainActivity3.this,"添加成功！",1000);
                }else{
                    // Toast.makeText(MainActivity2.this,"当前数据已经保存，不再次保存！！",Toast.LENGTH_SHORT).show();
                    // showToastTop("当前数据已经保存，不再次保存！！",Toast.LENGTH_SHORT);
                    CustomToast.showToast(MainActivity3.this,"当前数据已经保存",1000);

                }

                String strJson2 = (String) SharedPreferencesUtils.getParam(MainActivity3.this,"data",new String());
                List<String>  datalist2 = gson.fromJson(strJson2, new TypeToken<List<String>>() {}.getType());
                if(datalist2 != null){
                    setListSize(datalist2.size());
                }else{
                    setListSize(0);
                }
                cusScanView.unProscibeCamera(); // 开始识别
            }
        });
    }

    private void initView() {

        tvlistsize = this.findViewById(R.id.tv_listsize);
        ImageView imageView = this.findViewById(R.id.vLeftImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    private void setListSize(int size) {
        tvlistsize.setText(size+"");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                Uri uri = data.getData();
                Log.e("xx", uri.toString());

            }
        }
    }


    public void writeText(View view) {

        File file = new File(Environment.getExternalStorageDirectory(), "二维码扫描数据.txt");
        // xml读写
        FileOutputStream fileOutputStream;
        BufferedWriter bufferedWriter;
        try {
            file.createNewFile();
            fileOutputStream = new FileOutputStream(file);
            bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream));

            String c = "";
            String strJson = (String) SharedPreferencesUtils.getParam(MainActivity3.this, "data", c);
            List<String> datalist = gson.fromJson(strJson, new TypeToken<List<String>>() {
            }.getType());
            for (int i = 0; i < datalist.size(); i++) {
                bufferedWriter.write(datalist.get(i) + "\r\n");
            }

            bufferedWriter.close();
            Toast.makeText(MainActivity3.this, "写入成功！", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity3.this, "写入失败！！", Toast.LENGTH_SHORT).show();
        }

    }

    public void dataList(View view) {
        Intent intent = new Intent(this, DataActivity.class);
        startActivity(intent);
    }
}