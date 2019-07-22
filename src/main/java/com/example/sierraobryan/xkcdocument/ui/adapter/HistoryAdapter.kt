package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import kotlinx.android.synthetic.main.row_item_history_comic.view.*
import kotlinx.android.synthetic.main.row_item_tag.view.*
import kotlinx.android.synthetic.main.row_item_tag.view.tag_for_list
import java.text.DateFormat
import java.util.*

class HistoryAdapter(private val clickListener: (ComicShort) -> Unit) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var comics = emptyList<ComicShort>()

    internal fun setComics(questions: List<ComicShort>) {
        this.comics = questions
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.row_item_history_comic, parent, false)
        return HistoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return comics.size
    }

    override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
        holder.bind(comics.get(position), clickListener)
    }


    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(comic: ComicShort, clickListener: (ComicShort) -> Unit) {
            itemView.tag_for_list.text = comic.safeTitle
            if (comic.isFavorite) {
                itemView.favorite.visibility = View.VISIBLE
            } else {
                itemView.favorite.visibility = View.INVISIBLE
            }
            itemView.date_view.text = DateFormat.getDateInstance().format(Date(comic.timeStamp))
            itemView.setOnClickListener { clickListener(comic) }
        }
    }
}