package com.ztb.pinkoo.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

import com.ztb.pinkoo.R
import com.ztb.pinkoo.models.CategoryModel
import com.ztb.pinkoo.models.UserDataModel

class CategoryAdapter(var list:ArrayList<CategoryModel.Data>, var context:Context):
    RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        val catcolor: LinearLayout = itemView.findViewById(R.id.catcolor)
        val catname: TextView = itemView.findViewById(R.id.catname)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.ViewHolder {
        val view= LayoutInflater.from(parent.context).inflate(R.layout.item_cat_holder,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.ViewHolder, position: Int) {
        holder.catcolor.getBackground().setColorFilter(Color.parseColor(list[position].color.toString()), PorterDuff.Mode.SRC_ATOP);
//        holder.catcolor.setBa(Color.parseColor(list[position].color.toString()))
        holder.catname.text = list[position].name.toString()


    }

    override fun getItemCount(): Int {
        return list.size
    }


}