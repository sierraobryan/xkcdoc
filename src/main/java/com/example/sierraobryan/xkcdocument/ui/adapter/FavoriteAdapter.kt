package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import kotlinx.android.synthetic.main.row_item_tag.view.*

class FavoriteAdapter(private val clickListener: (ComicShort) -> Unit,
                      private val longClickListener: (ComicShort) -> Boolean) :
        RecyclerView.Adapter<FavoriteAdapter.ComicViewHolder>() {

    private var comics = emptyList<ComicShort>()

    internal fun setComics(questions: List<ComicShort>) {
        this.comics = questions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComicViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_comic, parent, false)
        return ComicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: ComicViewHolder, position: Int) {
        holder.bind(comics.get(position), clickListener, longClickListener)
    }


    class ComicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(comicShort: ComicShort, clickListener: (ComicShort) -> Unit,
                 longClickListener: (ComicShort) -> Boolean) {
            itemView.tag_for_list.text = comicShort.safeTitle
            itemView.setOnClickListener { clickListener(comicShort) }
            itemView.setOnLongClickListener { longClickListener(comicShort) }
        }
    }
}