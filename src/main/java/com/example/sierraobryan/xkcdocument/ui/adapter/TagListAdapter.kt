package com.example.sierraobryan.xkcdocument.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import kotlinx.android.synthetic.main.row_item_tag.view.*

class TagListAdapter(private val items: List<String>,
                     private val clickListener: (String) -> Unit) :
        RecyclerView.Adapter<TagListAdapter.TagViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_tag, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(items.get(position), clickListener)
    }

    class TagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(tag: String, clickListener: (String) -> Unit) {
            itemView.tag_for_list.text = tag
            itemView.setOnClickListener{ clickListener(tag) }
        }
    }


}
