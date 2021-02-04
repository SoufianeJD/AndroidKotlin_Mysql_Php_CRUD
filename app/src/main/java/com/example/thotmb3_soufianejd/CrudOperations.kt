package com.example.thotmb3_soufianejd

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import kotlinx.android.synthetic.main.activity_register.*
import org.json.JSONObject
class CrudOperations  : AppCompatActivity() {

    lateinit var i: Intent
    private var gender = "Male"
    private var level = "College"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        i = intent

        if (i.hasExtra("editmode")) {
            if (i.getStringExtra("editmode").equals("1")) {
                onEditMode()
            }
        }

        rgGender.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioMale -> {
                    gender = "Male"
                }

                R.id.radioFemale -> {
                    gender = "Female"
                }
            }
        }

        rgLevel.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                R.id.radioSecondary -> {
                    level = "Secondary"
                }

                R.id.radioCollege -> {
                    level = "College"
                }
                R.id.radioUniversity -> {
                    level = "University"
                }
            }
        }



        btnCreate.setOnClickListener {
            create()
        }

        btnUpdate.setOnClickListener {
            update()
        }

        btnDelete.setOnClickListener {
            AlertDialog.Builder(this)
                .setTitle("Confirmation")
                .setMessage("You sure u wanna delete this ?")
                .setPositiveButton("DELETE", DialogInterface.OnClickListener { dialogInterface, i ->
                    delete()
                })
                .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialogInterface, i ->
                    dialogInterface.dismiss()
                })
                .show()
        }
    }

    private fun onEditMode() {
        txtName.setText(i.getStringExtra("name"))
        txtLastName.setText(i.getStringExtra("lastname"))
        txtNumber.setText(i.getStringExtra("number"))
        txtMail.setText(i.getStringExtra("mail"))
        txtName.isEnabled = false

        btnCreate.visibility = View.GONE
        btnUpdate.visibility = View.VISIBLE
        btnDelete.visibility = View.VISIBLE


        gender = i.getStringExtra("gender").toString()

        if (gender.equals("Male")) {
            rgGender.check(R.id.radioMale)
        } else {
            rgGender.check(R.id.radioFemale)
        }

        level = i.getStringExtra("level").toString()
        if (level.equals("Secondary")) {
            rgGender.check(R.id.radioSecondary)
        } else if (level.equals("College")) {
            rgGender.check(R.id.radioCollege)
        } else {
            rgGender.check(R.id.radioUniversity)
        }
    }

    private fun create() {
        val loading = ProgressDialog(this)
        loading.setMessage("Adding data...")
        loading.show()

        AndroidNetworking.post(ConnectionToScripts.CREATE)
            .addBodyParameter("name", txtName.text.toString())
            .addBodyParameter("lastname", txtLastName.text.toString())
                .addBodyParameter("level", level)
            .addBodyParameter("mail", txtMail.text.toString())
            .addBodyParameter("number", txtNumber.text.toString())
            .addBodyParameter("gender", gender)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@CrudOperations.finish()
                    }

                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("Error...", it) }
                    Toast.makeText(applicationContext, "Failed Connection...", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun update() {
        val loading = ProgressDialog(this)
        loading.setMessage("Loading data...")
        loading.show()

        AndroidNetworking.post(ConnectionToScripts.UPDATE)
            .addBodyParameter("name", txtName.text.toString())
            .addBodyParameter("lastname", txtLastName.text.toString())
                .addBodyParameter("level", level)
            .addBodyParameter("mail", txtMail.text.toString())
            .addBodyParameter("number", txtNumber.text.toString())
            .addBodyParameter("gender", gender)
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {

                override fun onResponse(response: JSONObject?) {

                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@CrudOperations.finish()
                    }
                    }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("Error...", it) }
                    Toast.makeText(applicationContext, "Failed Connection...", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun delete() {
        val loading = ProgressDialog(this)
        loading.setMessage("Loading data...")
        loading.show()

        AndroidNetworking.get(ConnectionToScripts.DELETE + "?name=" + txtName.text.toString())
            .setPriority(Priority.MEDIUM)
            .build()
            .getAsJSONObject(object : JSONObjectRequestListener {
                override fun onResponse(response: JSONObject?) {
                    loading.dismiss()
                    Toast.makeText(applicationContext, response?.getString("message"), Toast.LENGTH_SHORT).show()

                    if (response?.getString("message")?.contains("successfully")!!) {
                        this@CrudOperations.finish()
                    }
                }

                override fun onError(anError: ANError?) {
                    loading.dismiss()
                    anError?.errorDetail?.toString()?.let { Log.d("Error...", it) }
                    Toast.makeText(applicationContext, "Failed Connection...", Toast.LENGTH_SHORT).show()
                }
            })
    }
}