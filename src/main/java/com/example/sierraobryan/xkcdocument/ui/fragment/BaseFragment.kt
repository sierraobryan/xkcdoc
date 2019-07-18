package com.example.sierraobryan.xkcdocument.ui.fragment

import androidx.fragment.app.Fragment
import com.example.sierraobryan.xkcdocument.R

open class BaseFragment: Fragment() {

    fun switchFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, fragment)
                .commitNow()
    }

    fun removeFragment(fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction()
                .remove(fragment)
                .commitNow()
    }

    fun displayTagList(strings: List<String>): String {
        if (strings.isNotEmpty()) {
            var listOfTagsString: StringBuilder = java.lang.StringBuilder()
            for (tag in strings) {
                listOfTagsString.append(tag).append(", ")
            }
            return listOfTagsString.substring(0, listOfTagsString.length - 2).toString()
        }
        return resources.getString(R.string.no_tags)
    }
}