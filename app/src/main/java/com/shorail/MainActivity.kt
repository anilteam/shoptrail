package com.shorail

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }


    @SuppressLint("SetJavaScriptEnabled")
    private fun init() {

        val pageWebViewSettings = mainWeb.settings
        pageWebViewSettings.builtInZoomControls = true
        pageWebViewSettings.displayZoomControls = false
        pageWebViewSettings.javaScriptEnabled = true
        pageWebViewSettings.javaScriptCanOpenWindowsAutomatically = true
        pageWebViewSettings.allowFileAccess = true
        pageWebViewSettings.allowUniversalAccessFromFileURLs = true
        pageWebViewSettings.allowFileAccessFromFileURLs = true
        pageWebViewSettings.setAppCacheEnabled(true)
        pageWebViewSettings.domStorageEnabled = true
        if (Utility.isNetworkAvailable(this)) {
            pageWebViewSettings.cacheMode = WebSettings.LOAD_DEFAULT
        } else {
            pageWebViewSettings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1)
            pageWebViewSettings.mediaPlaybackRequiresUserGesture = false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pageWebViewSettings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            CookieSyncManager.createInstance(this).startSync()
        } else {
            CookieManager.getInstance()
        }

        val cookieManager = CookieManager.getInstance()
        cookieManager!!.setAcceptCookie(true)

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            cookieManager.setAcceptThirdPartyCookies(mainWeb, true)
        }
        mainWeb.webViewClient = object : WebViewClient() {


            override fun shouldOverrideUrlLoading(wv: WebView?, url: String): Boolean {
                return if (!url.startsWith("https://www.shoprail.in/")) {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW)
                        intent.data = Uri.parse(url)
                        startActivity(intent)
                    }catch (e:Exception){
                        e.printStackTrace()
                    }
                    true
                }else{
                    wv!!.loadUrl(url)
                    true
                }
            }
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                loader.visibility = View.GONE
                swiperefresh.isRefreshing = false
            }
        }
        swiperefresh.visibility = View.VISIBLE
        mainWeb.loadUrl("https://www.shoprail.in/")
        swiperefresh.setProgressViewOffset(false, 150, 180)
        swiperefresh.setColorSchemeResources(R.color.colorAccent)
        swiperefresh.setOnRefreshListener {
            mainWeb.reload()
        }

        retry.setOnClickListener {
            forceRefresh(this)
        }


        bottom_navigation.setOnNavigationItemSelectedListener { item ->
            when (item.getItemId()) {
                R.id.home -> {
                    loadUrl("https://www.shoprail.in/")
                }
                R.id.category -> {
                    loadUrl("https://www.shoprail.in/index.php?route=product/category&path=135")

                }
                R.id.cart -> {
                    loadUrl("https://www.shoprail.in/index.php?route=checkout/cart")
                }

             /*   R.id.search -> {
                    loadUrl("https://www.shoprail.com/index.php?route=product/search&search")
                }*/

                R.id.me -> {
                    loadUrl("https://www.shoprail.in/index.php?route=account/account")
                }
            }
            true
        }
    }

    private fun loadUrl(s: String) {
        loader.visibility = View.VISIBLE
        mainWeb.loadUrl(s)
    }


    override fun onBackPressed() {
        if (mainWeb.canGoBack()) {
            mainWeb.goBack()
        } else {
            showBackDialog()
        }
    }

    private fun showBackDialog() {
        val snackBar: Snackbar =
            Snackbar.make(mainWeb, "Want exit from "+getString(R.string.app_name)+"!", Snackbar.LENGTH_LONG).setAction(
                "OK"
            ) {
                finish()
            }
        snackBar.show()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter()
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }

    private val networkChangeReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.d("app", "Network connectivity change")

            forceRefresh(context)
        }
    }

    private fun forceRefresh(context: Context?) {
        if (Utility.isNetworkAvailable(context)) {
            swiperefresh.visibility = View.VISIBLE
            group.visibility = View.GONE
        } else {
            group.visibility = View.VISIBLE
            animation_view.playAnimation()
            swiperefresh.visibility = View.GONE
        }
    }
}