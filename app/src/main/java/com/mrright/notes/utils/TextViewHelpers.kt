package com.mrright.notes.utils

import android.widget.TextView
import androidx.annotation.StringRes


fun TextView.setStringRes(@StringRes id : Int) {
	this.text = this.context.resources.getString(id)
}