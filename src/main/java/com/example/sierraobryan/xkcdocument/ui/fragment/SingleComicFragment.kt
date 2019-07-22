package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import com.example.sierraobryan.xkcdocument.data.model.ComicShort
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_single_comic.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.title_text

class SingleComicFragment : BaseFragment() {
    companion object {
        fun newInstance() = SingleComicFragment()

        fun newInstance(comic: ComicShort) : SingleComicFragment {
            val fragment = SingleComicFragment()
            val bundle = Bundle()
            bundle.putSerializable("comic", comic)
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
    private lateinit var comic: ComicShort
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

        val args = this.arguments
        if (args != null) {
            comic = args.get("comic") as ComicShort
            getImage(comic.comicId)
            comic.isFavorite = comicLisViewModel.isFavoriteFromId(comic)
            comicLisViewModel.setCurrentComic(comic)
        } else {
            getImage(getRandom())
        }

        observeOnComic()
        observeOnComicShortForFavorite()
        observeOnListOfTags()


        refresh.setOnClickListener { getImage(getRandom()) }
        add_tag.setOnClickListener { startAddTagFragment() }
        favorite_button.setOnClickListener { makeFavorite() }


    }

    private fun getImage(comicId : Int) {
        xkcdViewModel.getSpecificImage(comicId)

    }

    private fun observeOnComic() {
        xkcdViewModel.singleImage.observe(this, Observer {
            if (it is ApiSuccessResponse) {
                comic = it.body.toComicShort()
                comic.isFavorite = comicLisViewModel.isFavoriteFromId(comic)
                comicLisViewModel.setCurrentComic(comic)
                title_text.text = it.body.safeTitle
                tag_text.text = displayTagList(comicLisViewModel.getAllTagsforId(it.body.num))
                random_image.contentDescription = it.body.safeTitle
                Picasso.get().load(it.body.img).fit().centerInside().into(random_image)
            } else {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(random_image)
                home_image.contentDescription = resources.getString(R.string.computer_problems_image)
            }
        })
    }

    private fun observeOnComicShortForFavorite() {
        comicLisViewModel.comic.observe(this, Observer {
            if (::comic.isInitialized) {
                if (it.isFavorite) {
                    favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
                }
                comicLisViewModel.insertOrUpdate(comic)
            }
        })
    }

    private fun observeOnListOfTags() {
        comicLisViewModel.comicWithTagList.observe(this, Observer {
            if (::comic.isInitialized) {
                tag_text.text = displayTagList(comicLisViewModel.getAllTagsforId(comic.comicId))
            }
        })
    }

    private fun startAddTagFragment() {
        if (::comic.isInitialized) {
            switchFragment(AddNewTagFragment.newInstance(comic))
        }
    }

    private fun makeFavorite() {
        if (::comic.isInitialized) {
            val favoriteComic = ComicShort(comic.comicId, comic.safeTitle)
            if (!comic.isFavorite) {
                favoriteComic.isFavorite = true
                favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
            } else {
                favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_black_24dp))
            }
            comicLisViewModel.insertOrUpdate(favoriteComic)
        }
    }

    private fun getRandom() = (Math.random() * NUM_OF_COMICS).toInt()

}
