package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.sierraobryan.xkcdocument.Constants
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTag
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.example.sierraobryan.xkcdocument.ui.adapter.TagListAdapter
import com.example.sierraobryan.xkcdocument.ui.adapter.TagsForSingleComicAdapter
import com.example.sierraobryan.xkcdocument.utils.displayTagList
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_browse_tags.*
import kotlinx.android.synthetic.main.fragment_single_comic.*
import kotlinx.android.synthetic.main.fragment_single_comic.tags_recyclerview
import kotlinx.android.synthetic.main.fragment_welcome.title_text
import java.lang.Exception

class SingleComicFragment : BaseFragment() {

    companion object {
        fun newInstance() = SingleComicFragment()

        fun newInstance(comic: Comic) : SingleComicFragment {
            val fragment = SingleComicFragment()
            val bundle = Bundle()
            bundle.putSerializable(Constants.COMIC_KEY, comic)
            fragment.arguments = bundle
            return fragment
        }
    }

    private val xkcdViewModel: XkcdViewModel by lazy {
        ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java)
    }
    private val comicLisViewModel: ComicListViewModel by lazy {
        ViewModelProviders.of(activity!!).get(ComicListViewModel::class.java)
    }
    private val tagListAdapter: TagsForSingleComicAdapter by lazy {
        TagsForSingleComicAdapter({ tag : String -> tagItemClicked(tag) })
    }


    private lateinit var comic: ComicWithFavorite
    private var NUM_OF_COMICS = 2177

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_single_comic, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.app_bar_title.text = resources.getString(R.string.comic)

        NUM_OF_COMICS = xkcdViewModel.numberOfComics
        favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_black_24dp))

        val args = this.arguments
        args?.let {
            setComic(it.get(Constants.COMIC_KEY) as Comic)
            setUpView(comic)
            comicLisViewModel.getComicWithID(comic.toComic())
        } ?: run {
            fetchImage(getRandom())
        }

        setUpAdapter()

        observeOnComicCo()
        observeOnComicTags()

        refresh.setOnClickListener { getImage(getRandom()) }
        add_tag.setOnClickListener { startAddTagFragment() }
        favorite_button.setOnClickListener { makeFavorite() }


    }

    private fun getImage(comicId : Int) {
        xkcdViewModel.getSpecificImage(comicId)

    }

    private fun fetchImage(comicId: Int) {
        xkcdViewModel.getSpecificImageCo(comicId)
    }

    private fun setUpAdapter() {
        tags_recyclerview.visibility = View.VISIBLE
        tags_recyclerview.adapter = tagListAdapter
        val layoutManager = FlexboxLayoutManager(activity!!, FlexDirection.ROW, FlexWrap.WRAP)
        layoutManager.justifyContent = JustifyContent.CENTER
        tags_recyclerview.layoutManager = layoutManager
    }

    private fun observeOnComicCo() {
        xkcdViewModel.imageFromId.observe(this, Observer {
            it?.let {
                comicLisViewModel.getComicWithID(it)
                setComic(it)
                setUpView(comic)
            } ?: run {
                onError()
            }
        })
    }

    private fun observeOnComicTags() {
        comicLisViewModel.comic.observe(this, Observer {
            it?.let { tagListAdapter.setTags(it.tags) }
        })
    }


    private fun startAddTagFragment() {
        if (::comic.isInitialized) {
            switchFragment(AddNewTagFragment.newInstance(comic.toComic()))
        }
    }

    private fun setComic(comic: Comic) {
        this.comic = comic.toComicWithFavorite()
        this.comic.isFavorite = xkcdViewModel.isFavoriteFromId(comic.num)
    }

    private fun setUpView(comic: ComicWithFavorite) {
        title_text.text = comic.safeTitle
        setFavoriteDrawable(comic.isFavorite)
//        displayTagList(comicLisViewModel.getAllTagsForId(comic.num))?.let {
//            tag_text.text =  it } ?: run { tag_text.text =  resources.getString(R.string.no_tags) }
        random_image.contentDescription = comic.safeTitle
        try {
            Picasso.get().load(comic.img).fit().centerInside().into(random_image)
        } catch (e : Exception){
            onError()
        }
    }

    private fun makeFavorite() {
        if (::comic.isInitialized) {
            comic.isFavorite = !comic.isFavorite
            setFavoriteDrawable(comic.isFavorite)
            xkcdViewModel.insertOrUpdate(comic)
        }

    }

    private fun onError() {
        title_text.text = resources.getString(R.string.oops)
        Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(random_image)
        random_image.contentDescription = resources.getString(R.string.computer_problems_image)
    }

    private fun setFavoriteDrawable(isFavorite: Boolean) {
        if (isFavorite) {
            favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
        } else {
            favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_black_24dp))
        }
    }

    private fun tagItemClicked(tag : String) {
    }

    private fun getRandom() = (Math.random() * NUM_OF_COMICS).toInt()

}
