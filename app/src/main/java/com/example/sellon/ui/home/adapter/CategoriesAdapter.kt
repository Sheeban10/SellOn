package com.example.sellon.ui.home.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.sellon.R
import com.example.sellon.model.CategoriesModel
import de.hdodenhof.circleimageview.CircleImageView

class CategoriesAdapter(var categoriesList: MutableList<CategoriesModel>)
    : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>()
{
    private lateinit var context:Context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.adapter_categories,
            parent, false)

        return  ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.textViewTitle.setText(categoriesList.get(position).key)
        Glide.with(context)
            .load(categoriesList.get(position).image)
            .placeholder(R.drawable.ic_placeholder)
            .into(holder.imageView)


    }

    override fun getItemCount(): Int {
        return categoriesList.size


    }

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        val textViewTitle: TextView = itemView.findViewById(R.id.tvTitle)
        val imageView: CircleImageView = itemView.findViewById (R.id.ivIcon)

    }

}