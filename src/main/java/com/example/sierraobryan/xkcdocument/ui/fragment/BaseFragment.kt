package com.example.sierraobryan.xkcdocument.ui.fragment

import androidx.fragment.app.Fragment
import com.example.sierraobryan.xkcdocument.R

open class BaseFragment: Fragment() {

    fun switchFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
    }

    fun removeFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()
    }

}