package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.viewModel.ProfileViewModel
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryAndFavoritesPagerAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_tab_history_favorites.*

class HistoryAndFavoritesFragment : BaseFragment() {

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)
    }

    private var isGrid = false

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

            profileViewModel.isGrid.value?.let { isGrid = it }
            toggle.isChecked = isGrid

            val fragmentAdapter = HistoryAndFavoritesPagerAdapter(childFragmentManager, isGrid)
            viewpager_main.adapter = fragmentAdapter
            tabs_main.setupWithViewPager(viewpager_main)

            setCompactSwitch()

        }

    private fun setCompactSwitch() {
        toggle.setOnCheckedChangeListener { _, b ->
            profileViewModel.isGrid(b)
            if (b) {
                switchRecyclerViewLayout(HistoryGridFragment.newInstance())
                switchRecyclerViewLayout(FavoriteGridFragment.newInstance())
            } else {
                switchRecyclerViewLayout(HistoryListFragment.newInstance())
                switchRecyclerViewLayout(FavoriteListFragment.newInstance())
            }
        }
    }

    private fun switchRecyclerViewLayout(fragment: Fragment) {
        if (fragment is HistoryBaseFragment) {
            childFragmentManager.beginTransaction()
                    .add(R.id.list_grid_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        } else if (fragment is FavoriteBaseFragment) {
            childFragmentManager.beginTransaction()
                    .add(R.id.favorite_fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }
}