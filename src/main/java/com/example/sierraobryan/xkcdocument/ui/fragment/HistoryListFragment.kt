package com.example.sierraobryan.xkcdocument.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryListAdapter
import kotlinx.android.synthetic.main.fragment_fravorite.*
import kotlinx.android.synthetic.main.fragment_fravorite.error_layout

class HistoryListFragment : HistoryBaseFragment() {

    companion object {
        fun newInstance() = HistoryListFragment()
    }

    private val adapter: HistoryListAdapter by lazy {
        HistoryListAdapter({ comic : ComicWithFavorite -> comicItemClicked(comic) },
                { comicWithFavorite : ComicWithFavorite -> comicItemLongClicked(comicWithFavorite) })
    }


    override fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapterComics(comics: List<ComicWithFavorite>) {
        adapter.setComics(comics)
    }

}