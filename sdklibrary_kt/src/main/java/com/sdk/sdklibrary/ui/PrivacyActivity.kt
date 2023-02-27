package com.sdk.sdklibrary.ui

import android.view.View
import android.webkit.WebView
import android.widget.ImageView
import com.sdk.sdklibrary.R
import com.sdk.sdklibrary.base.SdkBaseActivity
import com.sdk.sdklibrary.config.HttpUrlConstants
import kotlinx.coroutines.*

/**
Data:2023/2/21
Time:15:21
author:colin handsome
 */
class PrivacyActivity: SdkBaseActivity() {

    var webView: WebView? = null
    var goback: ImageView? = null
    override fun getLayoutId(): Int {
        return R.layout.activity_privacy;

    }

    override fun initViews() {
        webView = `$`<WebView>(R.id.loadwebview)
        goback = `$`<ImageView>(R.id.goback)
    }

    override fun initListener() {
        setOnClick(goback)
    }

    override fun initData() {

        webView!!.loadUrl(HttpUrlConstants.getPrivacytUrl)

//        GlobalScope.launch {
//            println("codes run in coroutine scope")
//        }

//        runBlocking {
//            launch {
//                println("launch1")
//                delay(1000)
//                println("launch1 finished")
//            }
//            launch {
//                println("launch2")
//                delay(1000)
//                println("launch2 finished")
//            }
//        }

//        val start = System.currentTimeMillis()
//        runBlocking {
//            repeat(100000){
//                launch {
//                    println(".")
//                }
//            }
//        }
//        val end = System.currentTimeMillis()
//        println(end - start)


//        runBlocking {
//            coroutineScope {
//                launch {
//                    for (i in 1..10){
//                        println("coroutineScopecoroutineScope" + i)
//                        delay(1000)
//                    }
//                }
//            }
//            println("coroutineScope finished")
//        }
//        println("runBlocking finished")

//        runBlocking {
//            val result = async {
//                5 + 5
//            }.await()
//            println(result)
//        }

      /*  runBlocking {
            val start = System.currentTimeMillis()
            val result1 = async {
                delay(1000)
                5 + 5
            }.await()

            println("result1 "+result1)

            val result2 = async {
                delay(1000)
                4 + 6
            }.await()

            println("result2 "+result1*//**//*)
            println("result is ${result1 + result2}")
            val  end = System.currentTimeMillis()
            println("cost ${end - start} ms.")
            }
*/

        runBlocking {
            val result = withContext(Dispatchers.Default){
                5 + 5
            }
            println(result)


        }
//        printDot()
//
//        fetchDocs()

    }
    suspend fun fetchDocs() {
        val result = get("developer.android.com")
        println(result)
    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {
        "1111developer.android.com"
            delay(1000)

        }


    suspend fun printDot()  = coroutineScope{
        launch {
            println(".")
            delay(1000)
//            printDot2()
        }
    }
    suspend fun printDot2(){
//        val result ={ 5 + 5 }.await()
    }







    override fun processClick(v: View?) {
        val id = v!!.id
        if (id == R.id.goback) {
            finish()
        }

    }
}