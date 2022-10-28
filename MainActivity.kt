package com.example.trying

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), View.OnClickListener {
    private val TAG = javaClass.simpleName

    // server의 url을 적어준다
    private val BASE_URL = "http://27ad1bf3.ngrok.io"
    private var mMyAPI: MyAPI? = null
    private var mListTv: TextView? = null
    private var mGetButton: Button? = null
    private var mPostButton: Button? = null
    private var mPatchButton: Button? = null
    private var mDeleteButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mListTv = findViewById(R.id.result1)

        mGetButton = findViewById(R.id.button1)
        //mGetButton.setOnClickListener(this)

        mPostButton = findViewById(R.id.button2)
       // mPostButton.setOnClickListener(this)

        mPatchButton = findViewById(R.id.button3)
        //mPatchButton.setOnClickListener(this)

        mDeleteButton = findViewById(R.id.button4)
        //mDeleteButton.setOnClickListener(this)

        initMyAPI(BASE_URL)
    }

    private fun initMyAPI(baseUrl: String) {
        Log.d(TAG, "initMyAPI : $baseUrl")
        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        mMyAPI = retrofit.create(MyAPI::class.java)
    }

    override fun onClick(v: View) {
        if (v === mGetButton) {
            Log.d(TAG, "GET")
            val getCall = mMyAPI!!.get_posts()
            getCall!!.enqueue(object: Callback<List<PostItem>> {
                override fun onResponse(
                    call: Call<List<PostItem>>,
                    response: Response<List<PostItem>>
                ) {
                    if (response.isSuccessful) {
                        val mList = response.body()!!
                        var result = ""
                        for (item in mList) {
                            result += """title : ${item.title} text: ${item.text}
"""
                        }
                        mListTv!!.text = result
                    } else {
                        Log.d(TAG, "Status Code : " + response.code())
                    }
                }

                override fun onFailure(call: Call<List<PostItem>>, t: Throwable) {
                    Log.d(TAG, "Fail msg : " + t.message)
                }
            })
        } else if (v === mPostButton) {
            Log.d(TAG, "POST")
            val item = PostItem()
            item.title = "Android title"
            item.text = "Android text"
            val postCall = mMyAPI!!.post_posts(item)
            postCall!!.enqueue(object : Callback<PostItem?> {
                override fun onResponse(call: Call<PostItem?>, response: Response<PostItem?>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "등록 완료")
                    } else {
                        Log.d(TAG, "Status Code : " + response.code())
                        Log.d(TAG, response.errorBody().toString())
                        Log.d(TAG, call.request().body().toString())
                    }
                }

                override fun onFailure(call: Call<PostItem?>, t: Throwable) {
                    Log.d(TAG, "Fail msg : " + t.message)
                }
            })
        } else if (v === mPatchButton) {
            Log.d(TAG, "PATCH")
            val item = PostItem()
            item.title = "android patch title"
            item.text = "android patch text"
            //pk 값은 임의로 하드코딩하였지만 동적으로 setting 해서 사용가능
            val patchCall = mMyAPI!!.patch_posts(1, item)
            patchCall!!.enqueue(object : Callback<PostItem?> {
                override fun onResponse(call: Call<PostItem?>, response: Response<PostItem?>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "patch 성공")
                    } else {
                        Log.d(TAG, "Status Code : " + response.code())
                    }
                }

                override fun onFailure(call: Call<PostItem?>, t: Throwable) {
                    Log.d(TAG, "Fail msg : " + t.message)
                }
            })
        } else if (v === mDeleteButton) {
            Log.d(TAG, "DELETE")
            // pk 값은 임의로 변경가능
            val deleteCall = mMyAPI!!.delete_posts(2)
            deleteCall!!.enqueue(object : Callback<PostItem?> {
                override fun onResponse(call: Call<PostItem?>, response: Response<PostItem?>) {
                    if (response.isSuccessful) {
                        Log.d(TAG, "삭제 완료")
                    } else {
                        Log.d(TAG, "Status Code : " + response.code())
                    }
                }

                override fun onFailure(call: Call<PostItem?>, t: Throwable) {
                    Log.d(TAG, "Fail msg : " + t.message)
                }
            })
        }
    }
}

private fun <T> Call<T>.enqueue(callback: Callback<List<PostItem>>) {

}
