package com.example.thotmb3_soufianejd

import android.app.ProgressDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.jacksonandroidnetworking.JacksonParserFactory
import org.json.JSONObject
import kotlinx.android.synthetic.main.activity_recycleview.*

class ListActivity : AppCompatActivity() {
    var arrayList = ArrayList<StudentClass>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycleview)

        AndroidNetworking.setParserFactory(JacksonParserFactory())

        supportActionBar?.title = "E-Learning Thot"

        mRecyclerView.setHasFixedSize(true)
        mRecyclerView.layoutManager = LinearLayoutManager(this) as RecyclerView.LayoutManager?

        mFloatingActionButton.setOnClickListener {
            startActivity(Intent(this, CrudOperations::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        loadAllStudents()
    }

    private fun loadAllStudents() {
        val loading = ProgressDialog(this)
        loading.setMessage("Loading data...")
        loading.show()

        AndroidNetworking.get(ConnectionToScripts.READ)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {
                    arrayList.clear()
                    val jsonArray = response?.optJSONArray("result")

                    if (jsonArray?.length() == 0) {
                        loading.dismiss()
                        Toast.makeText(applicationContext, "Student data is empty, Add the data first", Toast.LENGTH_SHORT).show()
                    }

                    for (i in 0 until jsonArray?.length()!!) {
                        val jsonObject = jsonArray?.optJSONObject(i)

                        arrayList.add(
                            StudentClass(jsonObject.getString("name"),
                            jsonObject.getString("lastname"),
                            jsonObject.getString("level"),
                                jsonObject.getString("mail"),
                                jsonObject.getString("number"),
                            jsonObject.getString("gender"))
                        )

                        if (jsonArray?.length() - 1 == i) {
                            loading.dismiss()
                            val adapter = StudentAdapter(applicationContext, arrayList)
                            adapter.notifyDataSetChanged()
                            mRecyclerView.adapter = adapter
                        }
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.e("Error...", it) }
                    Toast.makeText(applicationContext, "Failed Connection...", Toast.LENGTH_SHORT).show()
                }
            })
    }
}