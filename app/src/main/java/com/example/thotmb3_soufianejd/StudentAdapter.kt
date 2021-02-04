package com.example.thotmb3_soufianejd

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_list.view.*

class StudentAdapter(private val context: Context, private val arrayList: ArrayList<StudentClass>) : RecyclerView.Adapter<StudentAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.activity_list,parent,false))
    }

    override fun getItemCount(): Int = arrayList!!.size

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.view.lblNameList.text = arrayList?.get(position)?.name
        holder.view.lblLevelList.text = arrayList?.get(position)?.level
        holder.view.lblMailList.text = arrayList?.get(position)?.mail
        holder.view.lblGenderList.text = arrayList?.get(position)?.gender

        holder.view.studentList.setOnClickListener {
            val i = Intent(context,CrudOperations::class.java)
            i.putExtra("editmode","1")
            i.putExtra("name",arrayList?.get(position)?.name)
            i.putExtra("lastname",arrayList?.get(position)?.lastname)
            i.putExtra("level",arrayList?.get(position)?.level)
            i.putExtra("number",arrayList?.get(position)?.number)
            i.putExtra("mail",arrayList?.get(position)?.mail)
            i.putExtra("gender",arrayList?.get(position)?.gender)
            context.startActivity(i)
        }
    }

    inner class Holder(val view: View) : RecyclerView.ViewHolder(view)
}