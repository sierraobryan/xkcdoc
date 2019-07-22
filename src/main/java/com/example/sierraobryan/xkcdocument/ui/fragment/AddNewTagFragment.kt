package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTag
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.ui.activity.MainActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_add_tag.*

class AddNewTagFragment : BaseFragment() {

    companion object {
        fun newInstance(comic : ComicShort): AddNewTagFragment {
            val fragment = AddNewTagFragment()
            val bundle = Bundle()
            bundle.putSerializable("comic", comic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val comicLisViewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private lateinit var listOfTags: List<String>
    private lateinit var comic: ComicShort

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
         return inflater.inflate(R.layout.fragment_add_tag, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity!!.app_bar_title.text = resources.getString(R.string.add_tag)

        comic = this.arguments!!.get("comic") as ComicShort
        listOfTags = comicLisViewModel.getAllTagsforId(comic.comicId)

        current_tags.text = displayTagList(listOfTags)
        save_button.setOnClickListener { save() }

    }

    private fun save() {
        val tag = new_tag.text.toString()
        (activity as MainActivity).addTagToDatabase(ComicWithTag(comic.comicId, comic.safeTitle, tag))
        removeFragment(this)
    }
}