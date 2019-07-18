package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import kotlinx.android.synthetic.main.row_item_tag.view.*

class FavoriteAdapter(val items: List<ComicShort>, val clickListener: (ComicShort) -> Unit) :
        RecyclerView.Adapter<FavoriteAdapter.ComicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_comic, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(items.get(position), clickListener)
    }


    class ComicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(comicShort: ComicShort, clickListener: (ComicShort) -> Unit) {
            itemView.tag_for_list.text = comicShort.safeTitle
            itemView.setOnClickListener { clickListener(comicShort) }
        }
    }
}