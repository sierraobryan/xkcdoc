package com.example.sierraobryan.xkcdocument.ui.fragment

import com.example.sierraobryan.xkcdocument.ui.adapter.ComicListAdapter
import kotlinx.android.synthetic.main.fragment_comic_list.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.Constants
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ComicListFragment : BaseFragment() {

    companion object {
        fun newInstance(tag: String): ComicListFragment {
            val fragment = ComicListFragment()
            val bundle = Bundle()
            bundle.putString(Constants.TAG_KEY, tag)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val viewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private lateinit var adapter: ComicListAdapter
    private var comicTag: String = "tags"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_comic_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.app_bar_title.text = resources.getString(R.string.comics)

        comicTag = this.arguments!!.get(Constants.TAG_KEY) as String
        viewModel.getComicsWithTag(comicTag)

        viewModel.comicWithTagList.observe(this, Observer {
            setUpAdapter(it)
        })

    }

    private fun setUpAdapter(list: List<Comic>) {
        adapter = ComicListAdapter(list, { comic : Comic -> comicItemClicked(comic) })
        comics_recyclerview.adapter = adapter
        comics_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun comicItemClicked(comic : Comic) {
        switchFragment(SingleComicFragment.newInstance(comic))
    }
}