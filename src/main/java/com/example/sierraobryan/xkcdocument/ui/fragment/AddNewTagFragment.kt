package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.Constants
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.utils.displayTagList
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_tag.*

class AddNewTagFragment : BaseFragment() {

    companion object {
        fun newInstance(comic : Comic): AddNewTagFragment {
            val fragment = AddNewTagFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constants.COMIC_KEY, comic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val comicLisViewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private lateinit var listOfTags: MutableList<String>
    private lateinit var comic: Comic

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
         return inflater.inflate(R.layout.fragment_add_tag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.app_bar_title.text = resources.getString(R.string.add_tag)

        comic = this.arguments!!.get(Constants.COMIC_KEY) as Comic

        comicLisViewModel.getComicWithID(comic)

        comicLisViewModel.comic.observe(this, Observer {
            listOfTags = it.tags
            displayTagList(it.tags)?.let {
                current_tags.text =  it } ?: run { current_tags.text =  resources.getString(R.string.no_tags) }
            save_button.setOnClickListener { save() }
        })

    }

    private fun save() {
        val tag = new_tag.text.toString()
        listOfTags.add(tag)
        comicLisViewModel.addTagToDatabase(comic.toComicWithTag(listOfTags), tag)
        removeFragment(this)
    }
}