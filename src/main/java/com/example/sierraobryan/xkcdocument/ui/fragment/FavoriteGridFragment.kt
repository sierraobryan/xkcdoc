package com.example.sierraobryan.xkcdocument.ui.fragment

import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.ui.adapter.FavoriteGridAdapter
import com.example.sierraobryan.xkcdocument.ui.adapter.FavoriteListAdapter
import kotlinx.android.synthetic.main.fragment_fravorite.*


class FavoriteGridFragment : FavoriteBaseFragment() {

    companion object {
        fun newInstance() = FavoriteGridFragment()
    }

    private val adapter: FavoriteGridAdapter by lazy {
        FavoriteGridAdapter({ comicWithFavorite: ComicWithFavorite -> comicItemClicked(comicWithFavorite) },
                { comicWithFavorite: ComicWithFavorite -> comicItemLongClicked(comicWithFavorite) })
    }

    override fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = GridLayoutManager(activity, 3)
    }

    override fun setAdapterData(list: List<ComicWithFavorite>) {
        adapter.setComics(list)
    }


}