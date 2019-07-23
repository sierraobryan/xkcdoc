package com.example.sierraobryan.xkcdocument.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import kotlinx.android.synthetic.main.row_item_history_comic.view.*
import kotlinx.android.synthetic.main.row_item_tag.view.tag_for_list
import java.lang.StringBuilder
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class HistoryAdapter(private val clickListener: (ComicWithFavorite) -> Unit,
                     private val longClickListener: (ComicWithFavorite) -> Boolean) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

    private var comics = emptyList<ComicWithFavorite>()

    internal fun setComics(questions: List<ComicWithFavorite>) {
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
        holder.bind(comics.get(position), clickListener, longClickListener)
    }


    class HistoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        fun bind(comic: ComicWithFavorite, clickListener: (ComicWithFavorite) -> Unit, longClickListener: (ComicWithFavorite) -> Boolean) {
            itemView.tag_for_list.text = comic.safeTitle
            if (comic.isFavorite) {
                itemView.favorite.visibility = View.VISIBLE
            } else {
                itemView.favorite.visibility = View.INVISIBLE
            }
            if (withinLastThreeDays(comic.day, comic.month, comic.year)) {
                itemView.new_comic_tag.visibility = View.VISIBLE
            } else {
                itemView.new_comic_tag.visibility = View.GONE
            }
            itemView.date_view.text = DateFormat.getDateInstance().format(Date(comic.timeStamp))
            itemView.setOnClickListener { clickListener(comic) }
            itemView.setOnLongClickListener { longClickListener(comic) }
        }

        private fun withinLastThreeDays(day: String, month: String, year: String) : Boolean {
            try {
                val comicDate = StringBuilder(day).append("/").append(month).append("/").append(year).toString()
                val date : Date = SimpleDateFormat("dd/MM/yyyy", Locale.US).parse(comicDate)
                val currentDate: Calendar = Calendar.getInstance()
                val currentDateMinusThree: Calendar = Calendar.getInstance()
                currentDateMinusThree.add(Calendar.DAY_OF_YEAR, -3)
                return !(date.before(currentDateMinusThree.time as Date) || date.after(currentDate.time as Date))
            } catch (e : ParseException) {
                return false
            }
        }
    }
}