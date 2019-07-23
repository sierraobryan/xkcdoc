package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
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

    private val viewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private val adapter: HistoryAdapter by lazy {
        HistoryAdapter({ comic : ComicWithFavorite -> comicItemClicked(comic) },
                { comicWithFavorite : ComicWithFavorite -> comicItemLongClicked(comicWithFavorite) })
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_fravorite, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpAdapter()
        setUpViewModel()
    }


    private fun setUpAdapter() {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpViewModel() {
        viewModel.allHistory.observe(this, Observer {
            if (it.isNotEmpty()) {
                adapter.setComics(it)
            } else {
                setUpError()
            }
        })
    }

    private fun setUpError() {
        favorite_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.no_history)
        Picasso.get().load(R.drawable.visited).fit().centerInside().into(error_image)
    }

    private fun comicItemClicked(comic : ComicWithFavorite) {
        switchFragment(SingleComicFragment.newInstance(comic.toComic()))
    }

    private fun comicItemLongClicked(comicWithFavorite: ComicWithFavorite) : Boolean {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(resources.getString(R.string.are_you_sure_from_history))
        builder.setCancelable(true)

        builder.setPositiveButton(
                resources.getString(R.string.delete),
                { _, _ -> delete(comicWithFavorite) })

        builder.setNegativeButton(
                resources.getString(R.string.cancel),
                { dialog, _ -> dialog.cancel() })

        val alert = builder.create()
        alert.show()
        return true
    }

    private fun delete(comic: ComicWithFavorite) {
        viewModel.delete(comic)
    }
}