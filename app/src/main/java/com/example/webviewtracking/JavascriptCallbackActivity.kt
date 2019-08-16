package com.example.webviewtracking

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.Toast
import android.webkit.JavascriptInterface

class JavascriptCallbackActivity : AppCompatActivity() {

    val htmlData = "<input type=\"button\" value=\"Say hello\" onClick=\"xyz('Hello Android!')\" />\n" +
            "\n" +
            "<script type=\"text/javascript\">\n" +
            "    function xyz(toast) {\n" +
            "        Android.showToastttttt(toast);\n" +
            "    }\n" +
            "</script>"


    override fun onStart() {
        super.onStart()
        setContentView(R.layout.activity_main)
        web_view.settings.javaScriptEnabled = true
        web_view.addJavascriptInterface(WebAppInterface(this), "Android")
        web_view.loadData(htmlData, "text/html", null)
    }

    inner class WebAppInterface(internal var mContext: Context) {
        /** Show a toast from the web page  */
        @JavascriptInterface
        fun showToastttttt(toast: String) {
            Toast.makeText(mContext, toast, Toast.LENGTH_SHORT).show()
        }
    }
}