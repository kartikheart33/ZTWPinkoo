package com.ztb.pinkoo.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.ztb.pinkoo.R
import com.ztb.pinkoo.models.UserDataModel

class UserAdapter(var list:ArrayList<UserDataModel.Data>, var context:Context,var type:Int):
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){

        val username: TextView = itemView.findViewById(R.id.username)
        val useremail: TextView = itemView.findViewById(R.id.useremail)
        val useravtar: ImageView = itemView.findViewById(R.id.userimage)

    }

    override fun getItemViewType(position: Int): Int {
        if (type==0){
            return 0
        }else{
            return 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserAdapter.ViewHolder {
        if (viewType==0){
            val view= LayoutInflater.from(parent.context).inflate(R.layout.item_user_list_holder,parent,false)
            return ViewHolder(view)
        }else{
            val view= LayoutInflater.from(parent.context).inflate(R.layout.item_user_grid_holder,parent,false)
            return ViewHolder(view)
        }

    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: UserAdapter.ViewHolder, position: Int) {
        holder.username.text = list[position].firstName.toString()+""+list[position].lastName.toString()
        holder.useremail.text = list[position].email.toString()
        Glide.with(context)
            .load(list[position].avatar.toString())
            .placeholder(R.drawable.ic_placeholder).error(R.drawable.ic_placeholder).into(holder.useravtar)

    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun addData(dataViews: List<UserDataModel.Data>) {
        this.list.addAll(dataViews)
        notifyDataSetChanged()
    }
}