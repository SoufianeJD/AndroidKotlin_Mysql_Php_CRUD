package com.example.thotmb3_soufianejd

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imgviewlist = findViewById(R.id.imglist) as ImageView
        val imgviewadd = findViewById(R.id.imgadd) as ImageView

        imgviewlist.setOnClickListener {
            val intent = Intent(this,ListActivity::class.java)
            startActivity(intent)
        }
        imgviewadd.setOnClickListener {
            val intent = Intent(this,CrudOperations::class.java)
            startActivity(intent)
        }



    }




}