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
import com.example.sierraobryan.xkcdocument.ui.adapter.FavoriteAdapter
import kotlinx.android.synthetic.main.fragment_fravorite.*
import androidx.appcompat.app.AlertDialog
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.layout_error.*


class FavoriteListFragment : BaseFragment() {

    companion object {
        fun newInstance() = FavoriteListFragment()
    }

    private lateinit var viewModel: ComicListViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_fravorite, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.app_bar_title.text = resources.getString(R.string.favorite)

        viewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
        viewModel.allFavorites.observe(this, Observer {
            if (it.isNotEmpty()) {
                setUpAdapter(it)
            } else {
                setUpError()
            }
        })

    }

    private fun setUpAdapter(list: List<ComicShort>) {
        favorite_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        adapter = FavoriteAdapter(list, { comicShort : ComicShort -> comicItemClicked(comicShort) },
                { comicShort : ComicShort -> comicItemLongClicked(comicShort) })
        favorite_recyclerview.adapter = adapter
        favorite_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpError() {
        favorite_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.no_favorites)
        Picasso.get().load(R.drawable.duty_calls).fit().centerInside().into(error_image)
    }

    private fun comicItemClicked(comicShort : ComicShort) {
        switchFragment(SingleComicFragment.newInstance(comicShort))
    }

    private fun comicItemLongClicked(comicShort: ComicShort) : Boolean {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(resources.getString(R.string.are_you_sure))
        builder.setCancelable(true)

        builder.setPositiveButton(
                resources.getString(R.string.delete),
                { dialog, which -> dialog.cancel() })

        builder.setNegativeButton(
                resources.getString(R.string.cancel),
                { _, _ -> delete(comicShort) })

        val alert = builder.create()
        alert.show()
        return true
    }

    private fun delete(comic: ComicShort) {
        viewModel.delete(comic)
    }

}