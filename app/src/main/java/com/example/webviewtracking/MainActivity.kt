package com.example.webviewtracking

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat.startActivity
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Myntra Insider website is being used for educational purposes as the website perfectly fits mobile screen and is interactive as well thus we can
 * also learn to track the URLs
 * URL : https://www.myntra.com/myntrainsider
 * */
class MainActivity : AppCompatActivity() {

    companion object {
        val myntraInsiderURL = "https://www.myntra.com/myntrainsider";
    }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        web_view.settings.javaScriptEnabled = true
        web_view.loadUrl(myntraInsiderURL);

        //checkPageNaviagtionLinks();

        web_view.webViewClient = MyWebViewClient(this)
    }

    /**
     * Handling page navigation : When the user clicks a link from a web page in your WebView, the default behavior is for Android to launch an app
     * that handles URLs. Usually, the default web browser opens and loads the destination URL. However, you can override this behavior for your
     * WebView, so links open within your WebView. You can then allow the user to navigate backward and forward through their web page history that's
     * maintained by your WebView.
     * */
    private fun checkPageNaviagtionLinks() {
        /*
        * To open links clicked by the user, provide a WebViewClient for your WebView, using setWebViewClient(). All links the user clicks load in
        * your WebView now instead of opening default browser.
        * */
        web_view.webViewClient = WebViewClient();
    }

    /**
     * If you want more control over where a clicked link loads, create your own WebViewClient that overrides the shouldOverrideUrlLoading() method.
     * */
    private class MyWebViewClient(val context: Context) : WebViewClient() {

        /**
         * Give the host application a chance to take over the control when a new url is about to be loaded in the
         * current WebView. You need to override both the methods.
         * The shouldOverrideUrlLoading(WebView view, String url) method is deprecated in API 24 and
         * the shouldOverrideUrlLoading(WebView view, WebResourceRequest request) method is added in API 24.
         */
        @Suppress("OverridingDeprecatedMember")
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            return handleUri(webView = view, uri = Uri.parse(url));
        }
        @RequiresApi(Build.VERSION_CODES.N)
        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            return handleUri(webView = view, uri = request?.url)
        }

        fun handleUri(webView : WebView?, uri : Uri?) : Boolean{
            if (uri?.host == "www.myntra.com") {
                // This is my web site, so do not override; let my WebView load the page
                return false
            }
            // Otherwise, the link is not for a page on my site, so launch another Activity that handles URLs
            Intent(Intent.ACTION_VIEW, uri).apply {
                startActivity(context, this, null);
                Toast.makeText(webView?.context, "The link is not for a page on my site", Toast.LENGTH_SHORT).show();
            }
            return true
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack()
        } else {
            super.onBackPressed()
        }
    }
}