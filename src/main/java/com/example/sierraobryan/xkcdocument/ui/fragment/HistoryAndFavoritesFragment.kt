package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryAndFavoritesPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tab_history_favorites.*

class HistoryAndFavoritesFragment : BaseFragment() {

        companion object {
            fun newInstance() = HistoryAndFavoritesFragment()
        }

        override fun onCreateView(
                inflater: LayoutInflater, container: ViewGroup?,
                savedInstanceState: Bundle?
        ): View {
            return inflater.inflate(R.layout.fragment_tab_history_favorites, container, false)
        }

        override fun onActivityCreated(savedInstanceState: Bundle?) {
            super.onActivityCreated(savedInstanceState)
            activity!!.app_bar_title.text = resources.getString(R.string.your_comics)

            val fragmentAdapter = HistoryAndFavoritesPagerAdapter(childFragmentManager)
            viewpager_main.adapter = fragmentAdapter
            tabs_main.setupWithViewPager(viewpager_main)

        }
}