package com.example.ld_zxing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.ailiwean.core.Result;
import com.ailiwean.core.view.style1.NBZxingView;


public class MainActivity3 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);


       /* NBZxingView NBZxingView = new NBZxingView(this) {
            @Override
            public void resultBack(Result content) {
                Toast.makeText(getContext(), content.getText(), Toast.LENGTH_LONG).show();
            }
        };
        NBZxingView.synchLifeStart(this);*/



        ZxingFragment fragment = new  ZxingFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.parent, fragment).commit();



    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
               Uri uri = data.getData();
                Log.e("xx",uri.toString());

            }
        }
    }





}