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
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_welcome.*

class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var xkcdViewModel: XkcdViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_welcome, container, false)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val image = activity!!.findViewById<View>(R.id.home_image)
        xkcdViewModel = ViewModelProviders.of(activity!!).get(XkcdViewModel::class.java)
        xkcdViewModel.firstImage.observe(this, Observer {
            if (it is ApiSuccessResponse) {
                title_text.text = it.body.safeTitle
                Picasso.get().load(it.body.img).fit().into(image as ImageView)
            } else {
                title_text.text = resources.getString(R.string.oops)
                //  Picasso.get().load(resources.getDrawable(R.drawable.fixing_problems, null)).into(image as ImageView)

            }
        })

    }

}