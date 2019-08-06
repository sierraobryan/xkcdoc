package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.data.viewModel.ProfileViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.example.sierraobryan.xkcdocument.ui.adapter.HistoryListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_fravorite.*
import kotlinx.android.synthetic.main.layout_error.*

abstract class HistoryBaseFragment : BaseFragment() {

    private val viewModel: XkcdViewModel by lazy {
        ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java)
    }
    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ProfileViewModel::class.java)
    }

    abstract fun setUpAdapter()

    abstract fun setAdapterComics(comics: List<ComicWithFavorite>)

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpAdapter()
        setUpViewModel()
        // setUpProfile()
    }

    private fun setUpViewModel() {
        viewModel.allHistory.observe(this, Observer {
            if (it.isNotEmpty()) {
                setAdapterComics(it)
            } else {
                setUpError()
            }
        })
    }

//    private fun setUpProfile() {
//        profileViewModel.isGrid.observe(this, Observer {
//            if (it && this is HistoryListFragment) {
//                switchRecyclerViewLayout(HistoryGridFragment.newInstance())
//            } else if (this is HistoryGridFragment ){
//                switchRecyclerViewLayout(HistoryListFragment.newInstance())
//            }
//        })
//    }

    private fun setUpError() {
        favorite_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.no_history)
        Picasso.get().load(R.drawable.visited).fit().centerInside().into(error_image)
    }

    fun comicItemClicked(comic : ComicWithFavorite) {
        switchFragment(SingleComicFragment.newInstance(comic.toComic()))
    }

    fun comicItemLongClicked(comicWithFavorite: ComicWithFavorite) : Boolean {
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