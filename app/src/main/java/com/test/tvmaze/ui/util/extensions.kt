package com.test.tvmaze.ui.util

import android.text.Html
import com.squareup.picasso.Callback

fun String.fromHTML() = Html.fromHtml(this).trim()

fun onLoad(action: () -> Unit, onErrorAction: (Exception?) -> Unit = {}): Callback {
    val callback = object : Callback {
        override fun onSuccess() {
            action()
        }
        override fun onError(e: Exception?) {
            onErrorAction(e)
        }
    }
    return callback
}

fun onLoad(action: () -> Unit) : Callback {
    val callback = object : Callback {
        override fun onSuccess() {
            action()
        }

        override fun onError(e: Exception?) {}
    }
    return callback
}