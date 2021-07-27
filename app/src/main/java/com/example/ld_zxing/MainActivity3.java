package com.example.ld_zxing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.ailiwean.core.Result;
import com.ailiwean.core.view.style1.NBZxingView;


public class MainActivity3 extends AppCompatActivity {

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

        CusScanView cusScanView =  this.findViewById(R.id.zxingview);
        cusScanView.synchLifeStart(this);

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


    public void writeText(View view) {
    }

    public void dataList(View view) {
    }
}