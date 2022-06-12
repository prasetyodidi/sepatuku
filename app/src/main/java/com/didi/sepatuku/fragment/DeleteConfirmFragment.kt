package com.didi.sepatuku.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.didi.sepatuku.repository.ShoppingChartRepository
import timber.log.Timber
import java.lang.IllegalStateException


class DeleteConfirmFragment : DialogFragment(){
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            Timber.d("dialog")
            val builder = AlertDialog.Builder(it)
            builder.setMessage("Are you sure delete this item?")
                .setPositiveButton("delete", DialogInterface.OnClickListener { dialog, id ->

                })
                .setNegativeButton("cancel", DialogInterface.OnClickListener { dialog, id ->
                    Timber.d("cancel dialog")
                })
            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}