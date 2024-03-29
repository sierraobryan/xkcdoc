package com.example.sierraobryan.xkcdocument.ui.fragment

import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.ui.adapter.FavoriteListAdapter
import kotlinx.android.synthetic.main.fragment_fravorite.*


class FavoriteListFragment : FavoriteBaseFragment() {

    companion object {
        fun newInstance() = FavoriteListFragment()
    }

    private val adapter: FavoriteListAdapter by lazy {
        FavoriteListAdapter({ comicWithFavorite: ComicWithFavorite -> comicItemClicked(comicWithFavorite) },
                { comicWithFavorite: ComicWithFavorite -> comicItemLongClicked(comicWithFavorite) })
    }

   override fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    override fun setAdapterData(list: List<ComicWithFavorite>) {
        adapter.setComics(list)
    }


}