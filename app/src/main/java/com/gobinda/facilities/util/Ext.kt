package com.gobinda.facilities.util

import android.view.View
import android.widget.ImageView
import com.google.android.material.snackbar.Snackbar


/**
 * Functional extension of View to show Snack Bar
 */
fun View.showSnackBar(message: String, duration: Int) {

    Snackbar.make(this, message, duration).show()
}


fun ImageView.setIcon(name: String) {
    val resId = context.resources.getIdentifier(
        name.replace("-", "_"), "drawable",
        context.packageName
    )
    if (resId != 0) {
        this.setImageResource(resId)
    }

}



