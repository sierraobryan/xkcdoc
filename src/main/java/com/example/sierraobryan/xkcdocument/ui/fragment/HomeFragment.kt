package com.example.sierraobryan.xkcdocument.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ApiSuccessResponse
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_single_comic.*
import kotlinx.android.synthetic.main.fragment_welcome.*
import kotlinx.android.synthetic.main.fragment_welcome.title_text

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private val xkcdViewModel: XkcdViewModel by lazy { ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_welcome, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.app_bar_title.text = resources.getString(R.string.welcome)

        xkcdViewModel.getFirstImage()

        xkcdViewModel.firstImageFromCoroutine.observe( this, Observer {
            it?.let {
                xkcdViewModel.numberOfComics = it.num
                title_text.text = it.safeTitle
                home_image.contentDescription = it.safeTitle
                Picasso.get().load(it.img).fit().centerInside()
                        .into(home_image)
                xkcdViewModel.insertOrUpdate(it.toComicWithFavorite(false))
            } ?: run {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(home_image)
                home_image.contentDescription = resources.getString(R.string.computer_problems_image)
            }
        })

        /**

         WITHOUT COROUTINES

        xkcdViewModel.firstImage.observe(this, Observer {
            if (it is ApiSuccessResponse) {
                xkcdViewModel.numberOfComics = it.body.num
                title_text.text = it.body.safeTitle
                home_image.contentDescription = it.body.safeTitle
                Picasso.get().load(it.body.img).fit().centerInside()
                        .into(home_image)
            } else {
                title_text.text = resources.getString(R.string.oops)
                Picasso.get().load(R.drawable.fixing_problems).fit().centerInside().into(home_image)
                home_image.contentDescription = resources.getString(R.string.computer_problems_image)

            }
        })
        **/

    }

}