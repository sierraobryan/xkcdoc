package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fravorite.*
import kotlinx.android.synthetic.main.fragment_fravorite.error_layout
import kotlinx.android.synthetic.main.layout_error.*

class HistoryListFragment : BaseFragment() {

    companion object {
        fun newInstance() = HistoryListFragment()
    }

    private lateinit var viewModel: ComicListViewModel
    private lateinit var adapter: HistoryAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fravorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpAdapter()

        viewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
        viewModel.allHistory.observe(this, Observer {
                if (it.isNotEmpty()) {
                    adapter.setComics(it)
                } else {
                    setUpError()
                }
            })
    }


    private fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        adapter = HistoryAdapter({ comic : ComicShort -> comicItemClicked(comic) })
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpError() {
        favorite_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.no_history)
        Picasso.get().load(R.drawable.visited).fit().centerInside().into(error_image)
    }

    private fun comicItemClicked(comic : ComicShort) {
        switchFragment(SingleComicFragment.newInstance(comic))
    }
}