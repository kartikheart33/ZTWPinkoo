package com.ztb.pinkoo.adapter

import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.text.HtmlCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.ztb.pinkoo.databinding.ItemCatHolderBinding
import com.ztb.pinkoo.databinding.ItemUserGridHolderBinding
import com.ztb.pinkoo.models.CategoryModel
import java.util.concurrent.TimeUnit

class NewTypeAdapter(
    private val list: ArrayList<CategoryModel.Data>,
    private val listener: (CategoryModel.Data) -> Unit
) : RecyclerView.Adapter<NewTypeAdapter.ViewHolder>()  {

    inner class ViewHolder(val binding: ItemCatHolderBinding,val mContext: Context) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: CategoryModel.Data) {
            binding.catname.text = item.name
            binding.catcolor.background.setColorFilter(Color.parseColor(item.color.toString()), PorterDuff.Mode.SRC_ATOP);
            binding.fevSuvichar.setOnClickListener {
                listener(item)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemCatHolderBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return ViewHolder(binding,parent.context)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            holder.bind(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
    companion object {
        private val TAG = NewTypeAdapter::class.java.name
    }

}
