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
import kotlinx.android.synthetic.main.fragment_browse_tags.*

class TagListFragment : BaseFragment() {
    companion object {
        fun newInstance() = TagListFragment()
    }

    private lateinit var viewModel: ComicListViewModel
    private lateinit var tagListAdapter: TagListAdapter

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_browse_tags, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
        viewModel.tagList.observe(this, Observer { setUpAdapter(it!!.toList()) })

    }

    private fun setUpAdapter(list: List<String>) {
        tagListAdapter = TagListAdapter(list, { tag : String -> tagItemClicked(tag) })
        tags_recyclerview.adapter = tagListAdapter
        tags_recyclerview.layoutManager = LinearLayoutManager(activity)
    }

    private fun tagItemClicked(tag : String) {
        switchFragment(ComicListFragment.newInstance(tag))
    }


}

