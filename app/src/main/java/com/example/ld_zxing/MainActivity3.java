package com.example.ld_zxing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ailiwean.core.Result;
import com.ailiwean.core.view.style1.NBZxingView;
import com.example.ld_zxing.util.SharedPreferencesUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;


public class MainActivity3 extends AppCompatActivity {

    private  CusScanView cusScanView;


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

        cusScanView = this.findViewById(R.id.zxingview);
        cusScanView.synchLifeStart(this);
        cusScanView.setListeren(new CusScanView.MyInterface() {
            @Override
            public void scanResult(@NotNull String content) {
                Toast.makeText(MainActivity3.this, content, Toast.LENGTH_LONG).show();
                cusScanView.unProscibeCamera();
            }
        });

        initView();


    }

    private void initView() {
        ImageView imageView = this.findViewById(R.id.vLeftImage);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

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


    Gson gson = new Gson();

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