package com.example.ld_zxing;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ailiwean.core.Result;
import com.ailiwean.core.helper.VibrateHelper;
import com.ailiwean.core.view.style1.NBZxingView;
import com.ailiwean.core.zxing.ScanTypeConfig;

import org.jetbrains.annotations.NotNull;

public class ZxingFragment extends Fragment {

    private NBZxingView NBZxingView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NBZxingView = new NBZxingView(container.getContext()) {
            @Override
            public void resultBack(@NotNull Result content) {
                Toast.makeText(getContext(), content.getText(), Toast.LENGTH_LONG).show();
               // NBZxingView.unProscibeCamera();
               /* new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(5000);
                            mainHand.post(new Runnable() {
                                @Override
                                public void run() {
                                    unProscibeCamera();
                                }
                            });

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();*/


                unProscibeCamera();
            }



          /*  *//***
             * 扫码成功后的一些动作
             *//*
            private fun scanSucHelper() {

                //关闭相机
                onCameraPause()

                //清理线程池任务缓存
                ableCollect?.clear()

                //关闭扫码条动画
                scanBarView?.stopScanAnimator()

                //播放音频
                VibrateHelper.playVibrate()

                //震动
                VibrateHelper.playBeep()

            }*/





        };
        return NBZxingView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NBZxingView.synchLifeStart(this);
    }
}
