package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicTag
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.example.sierraobryan.xkcdocument.ui.activity.MainActivity
import kotlinx.android.synthetic.main.fragment_add_tag.*

class AddNewTagFragment : BaseFragment() {

    companion object {
        fun newInstance(comic : ComicTag): AddNewTagFragment {
            val fragment = AddNewTagFragment()
            val bundle = Bundle()
            bundle.putSerializable("comic", comic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var comicLisViewModel: ComicListViewModel
    private lateinit var listOfTags: List<String>
    private lateinit var comic: ComicTag

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_add_tag, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        comic = this.arguments!!.get("comic") as ComicTag

        comicLisViewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
        listOfTags = comicLisViewModel.getAllTagsforId(comic.comicId)

        current_tags.text = displayTagList(listOfTags)

        save_button.setOnClickListener { save() }

    }

    private fun save() {
        var tag = new_tag.text.toString()
        (activity as MainActivity).addTagToDatabase(ComicTag(comic.comicId, comic.safeTitle, tag))
        removeFragment(this)


    }
}