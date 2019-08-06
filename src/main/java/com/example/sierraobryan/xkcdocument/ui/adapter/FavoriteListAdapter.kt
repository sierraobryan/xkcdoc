package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import kotlinx.android.synthetic.main.row_item_tag.view.*

class FavoriteListAdapter(private val clickListener: (ComicWithFavorite) -> Unit,
                          private val longClickListener: (ComicWithFavorite) -> Boolean) :
        RecyclerView.Adapter<FavoriteListAdapter.ComicViewHolder>() {

    private var comics = emptyList<ComicWithFavorite>()

    internal fun setComics(questions: List<ComicWithFavorite>) {
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
        fun bind(comicWithFavorite: ComicWithFavorite, clickListener: (ComicWithFavorite) -> Unit,
                 longClickListener: (ComicWithFavorite) -> Boolean) {
            itemView.tag_for_list.text = comicWithFavorite.safeTitle
            itemView.setOnClickListener { clickListener(comicWithFavorite) }
            itemView.setOnLongClickListener { longClickListener(comicWithFavorite) }
        }
    }
}