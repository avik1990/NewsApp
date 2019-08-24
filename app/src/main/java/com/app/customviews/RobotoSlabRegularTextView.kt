package com.app.customviews

import android.content.Context
import android.graphics.Typeface
import android.support.v7.widget.AppCompatTextView
import android.util.AttributeSet
import android.util.Log
import com.app.newsapp.R

class RobotoSlabRegularTextView : AppCompatTextView {
    fun setCustomFont(context: Context?, customFont: String?, isBold: Boolean) {
        try {
            var typeface = Typeface.createFromAsset(context?.assets, customFont)
            if (isBold)
                setTypeface(typeface, Typeface.BOLD)
            else
                setTypeface(typeface)
        } catch (exception: Exception) {
            Log.e("robotoSlab_regular", exception.toString())
        }
    }

    constructor(context: Context) : super(context) {
        setCustomFont(context, context.getString(R.string.robotoSlab_regular), false)
    }

    constructor(context: Context, attributeSet: AttributeSet) : super(context, attributeSet) {
        setCustomFont(context, context.getString(R.string.robotoSlab_regular), false)
    }

    constructor(context: Context, attributeSet: AttributeSet, defStyle: Int) : super(context, attributeSet, defStyle) {
        setCustomFont(context, context.getString(R.string.robotoSlab_regular), false)
    }
}