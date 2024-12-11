package com.gmail.devpelegrino.zhupper.ui.utils

import android.app.AlertDialog
import android.content.Context
import com.gmail.devpelegrino.zhupper.R

fun showErrorDialog(
    fragmentContext: Context,
    errorMessage: String
) {
    AlertDialog.Builder(fragmentContext)
        .setTitle(fragmentContext.getString(R.string.error_dialog_title))
        .setMessage(errorMessage)
        .setNeutralButton(
            fragmentContext.getString(R.string.exit_button)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        .show()
}
