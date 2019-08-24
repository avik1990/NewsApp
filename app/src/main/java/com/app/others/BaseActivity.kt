package com.app.others

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View

abstract class BaseActivity : AppCompatActivity() {

    var isActivityVisible: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /* To change the status bar text color */
       /* if (android.os.Build.VERSION.SDK_INT >= 21) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }*/
        setContentView(getLayout())
        initResources()
        initListeners()

    }

    override fun onResume() {
        super.onResume()
        isActivityVisible = true
    }

    override fun onPause() {
        super.onPause()
        isActivityVisible = false
    }


    abstract fun initResources()
    abstract fun initListeners()
    abstract fun getLayout(): Int

}