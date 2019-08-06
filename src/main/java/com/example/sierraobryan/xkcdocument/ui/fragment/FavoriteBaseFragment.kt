package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import kotlinx.android.synthetic.main.fragment_fravorite.*
import androidx.appcompat.app.AlertDialog
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.layout_error.*


abstract class FavoriteBaseFragment : BaseFragment() {

    private val viewModel: XkcdViewModel by lazy {
        ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_fravorite, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpAdapter()
        setUpViewModel()

    }

    abstract fun setUpAdapter()

    abstract fun setAdapterData(list: List<ComicWithFavorite>)

    private fun setUpViewModel() {
        viewModel.allFavorites.observe(this, Observer {
            if (it.isNotEmpty()) {
                setAdapterData(it)
            } else {
                setUpError()
            }
        })
    }

    private fun setUpError() {
        favorite_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.no_favorites)
        Picasso.get().load(R.drawable.duty_calls).fit().centerInside().into(error_image)
    }

    fun comicItemClicked(comicWithFavorite : ComicWithFavorite) {
        switchFragment(SingleComicFragment.newInstance(comicWithFavorite.toComic()))
    }

    fun comicItemLongClicked(comicWithFavorite: ComicWithFavorite) : Boolean {
        val builder = AlertDialog.Builder(context!!)
        builder.setMessage(resources.getString(R.string.are_you_sure))
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
        comic.isFavorite = false
        viewModel.insertOrUpdate(comic)
    }

}