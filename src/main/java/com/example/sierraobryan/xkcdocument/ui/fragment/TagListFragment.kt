package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.ui.adapter.TagListAdapter
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_browse_tags.*
import kotlinx.android.synthetic.main.fragment_browse_tags.error_layout
import kotlinx.android.synthetic.main.fragment_fravorite.*
import kotlinx.android.synthetic.main.layout_error.*

class TagListFragment : BaseFragment() {
    companion object {
        fun newInstance() = TagListFragment()
    }

    private val viewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private val tagListAdapter: TagListAdapter by lazy { TagListAdapter({ tag : String -> tagItemClicked(tag) }) }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_browse_tags, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.app_bar_title.text = resources.getString(R.string.categories)

        setUpAdapter()
        setUpViewModel()

    }

    private fun setUpAdapter() {
        tags_recyclerview.visibility = View.VISIBLE
        error_layout.visibility = View.GONE
        tags_recyclerview.adapter = tagListAdapter
        tags_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun setUpViewModel() {
        viewModel.tagList.observe(this, Observer {
            if (it.isNotEmpty()) {
                tagListAdapter.setComics(it.toList())
            } else {
                setUpError()
            }
        })
    }

    private fun setUpError() {
        tags_recyclerview.visibility = View.GONE
        error_layout.visibility = View.VISIBLE
        error_text.text = resources.getString(R.string.oops)
        Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(error_image)
    }


    private fun tagItemClicked(tag : String) {
        switchFragment(ComicListFragment.newInstance(tag))
    }


}

