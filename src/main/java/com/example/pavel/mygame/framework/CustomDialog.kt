package com.example.pavel.mygame.framework

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import com.example.pavel.mygame.R
import kotlinx.android.synthetic.main.custom_dialog.*

/**
 * Created by pavel on 28/11/2017.
 */
class CustomDialog(statusText: String, context: Context?): Dialog(context) {
    val status = statusText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.custom_dialog)
        setCanceledOnTouchOutside(false)
        val statusText: TextView = findViewById(R.id.description)
        statusText.text = status
        text_right.setOnClickListener {
            dismiss()
        }
    }


}