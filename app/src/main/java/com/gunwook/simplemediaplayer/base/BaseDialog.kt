package com.gunwook.simplemediaplayer.base

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.WindowManager

abstract class BaseDialog : Dialog {
    constructor(context : Context, themeResId: Int) : super(context , themeResId) {
    }

    constructor(context: Context) : super(context)

    abstract fun getLayoutId() : Int
    abstract fun initView()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val layoutParams = WindowManager.LayoutParams()
        layoutParams.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND
        layoutParams.dimAmount = 0.8f
        window?.attributes = layoutParams

        setContentView(getLayoutId())

        initView()
    }
}