package com.gobinda.facilities.util

import android.graphics.Typeface
import android.view.Gravity
import com.google.android.material.snackbar.Snackbar
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import java.text.SimpleDateFormat
import java.util.*


/**
 * Functional extension of View to show Snack Bar
 */
fun View.showSnackBar(message: String, duration: Int) {

    Snackbar.make(this, message, duration).show()
}

/**
 * Functional extension TextView to convert FontAwesome text to icon
 * using stringResId
 */
fun TextView.toIcon(stringResId: Int) : TextView{
    return toIcon(context.getString(stringResId))
}

/**
 * Functional extension TextView to convert FontAwesome text to icon
 */
fun TextView.toIcon(text: CharSequence? = this.text) : TextView{
    gravity = Gravity.CENTER
    typeface = Typeface.createFromAsset(context.assets,
        "fonts/FontAwesome.ttf")
    this.text = text
    return this
}

fun ImageView.setIcon( name: String){
    val resId = context.resources.getIdentifier(
        name.replace("-", "_"),"drawable",
        context.packageName)
    if(resId != 0) {
        this.setImageResource(resId)
    }
//    esources resources = context.getResources();
//    final int resourceId = resources.getIdentifier(name, "drawable",
//        context.getPackageName());
//    return resources.getDrawable(resourceId);

}



