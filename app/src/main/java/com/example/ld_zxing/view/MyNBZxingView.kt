package com.example.ld_zxing.view

import android.content.Context
import android.util.AttributeSet
import android.view.View
import com.ailiwean.core.Result
import com.ailiwean.core.view.*
import com.google.android.cameraview.R
import kotlinx.android.synthetic.main.nbzxing_style1_floorview.view.*


open class MyNBZxingView @JvmOverloads constructor(context: Context, attributeSet: AttributeSet? = null, def: Int = 0) :
        MyFreeZxingView(context, attributeSet, def) {

    override fun resultBack(content: Result) {

    }

    override fun provideFloorView(): Int {
        return R.layout.nbzxing_style1_floorview
    }

    override fun provideParseRectView(): View? {
        return scanRectView
    }

    override fun provideLightView(): ScanLightViewCallBack? {
        return lightView
    }

    override fun provideLocView(): ScanLocViewCallBack? {
        return locView
    }

    override fun provideScanBarView(): ScanBarCallBack? {
        return scanBarView
    }



}