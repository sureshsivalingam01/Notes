package com.mrright.notes.utils

import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.google.android.material.snackbar.Snackbar


fun View.visible() {
	this.visibility = View.VISIBLE
}

fun View.inVisible() {
	this.visibility = View.INVISIBLE
}

fun View.gone() {
	this.visibility = View.GONE
}



