package com.example.sierraobryan.xkcdocument.ui.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.sierraobryan.xkcdocument.ui.fragment.FavoriteListFragment
import com.example.sierraobryan.xkcdocument.ui.fragment.HistoryListFragment

class HistoryAndFavoritesPagerAdapter (fm: FragmentManager) : FragmentPagerAdapter(fm) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> HistoryListFragment()
            1 -> FavoriteListFragment()
            else -> HistoryListFragment()
        }
    }

    override fun getCount(): Int {
        return 2
    }

    override fun getPageTitle(position: Int): CharSequence {
        return when (position) {
            0 -> "History"
            1 -> "Favorites"
            else -> "History"
        }
    }

}