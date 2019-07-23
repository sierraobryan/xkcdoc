package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithFavorite
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

        fun newInstance(comic: Comic) : SingleComicFragment {
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

        val args = this.arguments
        args?.let {
            setComic(args.get("comic") as Comic)
            setUpView(comic)
        } ?: run {
            // getImage(getRandom())
            fetchImage(getRandom())
        }

        // observeOnComic()
        observeOnComicCo()
        observeOnComicShortForFavorite()
        observeOnListOfTags()


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

    private fun observeOnComic() {
        xkcdViewModel.singleImage.observe(this, Observer {
            if (it is ApiSuccessResponse) {
                setComic(it.body)
                setUpView(comic)
            } else {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(random_image)
                random_image.contentDescription = resources.getString(R.string.computer_problems_image)
            }
        })
    }

    private fun observeOnComicCo() {
        xkcdViewModel.imageFromId.observe(this, Observer {
            it?.let {
                setComic(it)
                setUpView(comic)
            } ?: run {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(random_image)
                random_image.contentDescription = resources.getString(R.string.computer_problems_image)
            }
        })
    }

    private fun observeOnComicShortForFavorite() {
        xkcdViewModel.comic.observe(this, Observer {
            if (::comic.isInitialized) {
                if (it.isFavorite) {
                    favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
                }
                xkcdViewModel.insertOrUpdate(comic)
            }
        })
    }

    private fun observeOnListOfTags() {
        comicLisViewModel.comicWithTagList.observe(this, Observer {
           if (::comic.isInitialized) {
               tag_text.text = displayTagList(comicLisViewModel.getAllTagsForId(comic.num))
           }
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
        xkcdViewModel.setCurrentComic(this.comic)
    }

    private fun setUpView(comic: ComicWithFavorite) {
        title_text.text = comic.safeTitle
        tag_text.text = displayTagList(comicLisViewModel.getAllTagsForId(comic.num))
        random_image.contentDescription = comic.safeTitle
        Picasso.get().load(comic.img).fit().centerInside().into(random_image)
    }

    private fun makeFavorite() {
        if (::comic.isInitialized) {
            comic.isFavorite = !comic.isFavorite
            if (comic.isFavorite) {
                favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_black_24dp))
            } else {
                favorite_button.setImageDrawable(resources.getDrawable(R.drawable.ic_favorite_border_black_24dp))
            }
            xkcdViewModel.insertOrUpdate(comic)
        }

    }

    private fun getRandom() = (Math.random() * NUM_OF_COMICS).toInt()

}
