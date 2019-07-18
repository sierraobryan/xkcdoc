package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import com.example.sierraobryan.xkcdocument.data.model.ComicTag
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_single_comic.*
import kotlinx.android.synthetic.main.fragment_welcome.title_text

class SingleComicFragment : BaseFragment() {
    companion object {
        fun newInstance() = SingleComicFragment()

        fun newInstance(comicTag: ComicTag) : SingleComicFragment {
            val fragment = SingleComicFragment()
            val bundle = Bundle()
            bundle.putSerializable("comic", comicTag)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var xkcdViewModel: XkcdViewModel
    private lateinit var comicLisViewModel: ComicListViewModel
    private lateinit var comic: ComicTag
    private val NUM_OF_COMICS = 2177

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_single_comic, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        xkcdViewModel = ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java)
        comicLisViewModel = ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)

        val args = this.arguments
        if (args != null) {
            comic = args.get("comic") as ComicTag
            getImage(comic.comicId)
        } else {
            getImage(getRandom())
        }

        val image = activity!!.findViewById<View>(R.id.random_image)
        xkcdViewModel.singleImage.observe(this, Observer {
            if (it is ApiSuccessResponse) {
                comic = ComicTag(it.body.num, it.body.safeTitle, "")
                title_text.text = it.body.safeTitle
                tag_text.text = displayTagList(comicLisViewModel.getAllTagsforId(it.body.num))
                Picasso.get().load(it.body.img).into(image as ImageView)
            } else {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).into(image as ImageView)

            }
        })
        comicLisViewModel.comicTagList.observe(this, Observer {
            if (::comic.isInitialized) {
                displayTagList(comicLisViewModel.getAllTagsforId(comic.comicId))
            }
        })

        refresh.setOnClickListener { getImage(getRandom()) }
        add_tag.setOnClickListener {
            if (::comic.isInitialized) {
                switchFragment(AddNewTagFragment.newInstance(comic))
            }
        }


    }

    private fun getImage(comicId : Int) {
        xkcdViewModel.getSpecificImage(comicId)

    }

    private fun getRandom() = (Math.random() * NUM_OF_COMICS).toInt()

}
