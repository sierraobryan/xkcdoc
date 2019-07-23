package com.example.sierraobryan.xkcdocument.utils

fun displayTagList(strings: List<String>): String? {
    if (strings.isNotEmpty()) {
        val listOfTagsString: StringBuilder = java.lang.StringBuilder()
        for (tag in strings) {
            listOfTagsString.append(tag).append(", ")
        }
        return listOfTagsString.substring(0, listOfTagsString.length - 2).toString()
    }
    return null
}