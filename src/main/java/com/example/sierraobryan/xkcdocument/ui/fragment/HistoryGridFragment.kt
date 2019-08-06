package com.example.sierraobryan.xkcdocument.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryGridAdapter
import kotlinx.android.synthetic.main.fragment_fravorite.*

class HistoryGridFragment : HistoryBaseFragment() {

    companion object {
        fun newInstance() = HistoryGridFragment()
    }

    private val adapter: HistoryGridAdapter by lazy {
        HistoryGridAdapter({ comic : ComicWithFavorite -> comicItemClicked(comic) },
                { comicWithFavorite : ComicWithFavorite -> comicItemLongClicked(comicWithFavorite) })
    }


    override fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = GridLayoutManager(activity, 3)
    }

    override fun setAdapterComics(comics: List<ComicWithFavorite>) {
        adapter.setComics(comics)
    }

}