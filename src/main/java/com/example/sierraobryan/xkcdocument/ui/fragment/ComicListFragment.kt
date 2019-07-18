package com.example.sierraobryan.xkcdocument.ui.fragment

import com.example.sierraobryan.xkcdocument.ui.adapter.ComicListAdapter
import kotlinx.android.synthetic.main.fragment_comic_list.*
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import kotlinx.android.synthetic.main.activity_main.*

class ComicListFragment : BaseFragment() {

    companion object {
        fun newInstance(tag: String): ComicListFragment {
            val fragment = ComicListFragment()
            val bundle = Bundle()
            bundle.putString("tag", tag)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var viewModel: ComicListViewModel
    private lateinit var adapter: ComicListAdapter
    private lateinit var listOfComics: List<ComicShort>
    private var comicTag: String = "tag"

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_comic_list, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.app_bar_title.text = resources.getString(R.string.comics)

        comicTag = this.arguments!!.get("tag") as String

        viewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
        listOfComics = viewModel.getAllComicsForTag(comicTag)
        setUpAdapter(listOfComics)

    }

    private fun setUpAdapter(list: List<ComicShort>) {
        adapter = ComicListAdapter(list, { comic : ComicShort -> comicItemClicked(comic) })
        comics_recyclerview.adapter = adapter
        comics_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun comicItemClicked(comic : ComicShort) {
        switchFragment(SingleComicFragment.newInstance(comic))
    }
}