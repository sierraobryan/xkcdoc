package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import kotlinx.android.synthetic.main.row_item_tag.view.*
import kotlinx.android.synthetic.main.row_item_tag_for_single_comic.view.*

class TagsForSingleComicAdapter(private val clickListener: (String) -> Unit) :
        RecyclerView.Adapter<TagsForSingleComicAdapter.TagViewHolder>() {

    private var tags = emptyList<String>()

    internal fun setTags(tags: List<String>) {
        this.tags = tags
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TagViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_tag_for_single_comic, parent, false)
        return TagViewHolder(view)
    }

    override fun getItemCount(): Int {
        return tags.size
    }

    override fun onBindViewHolder(holder: TagViewHolder, position: Int) {
        holder.bind(tags.get(position), clickListener)
    }

    class TagViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        fun bind(tag: String, clickListener: (String) -> Unit) {
            itemView.single_tag.text = tag
            itemView.setOnClickListener{ clickListener(tag) }
        }
    }


}
