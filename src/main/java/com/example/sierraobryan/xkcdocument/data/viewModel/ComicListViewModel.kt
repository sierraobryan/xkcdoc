package com.example.sierraobryan.xkcdocument.data.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.sierraobryan.xkcdocument.Constants.ALL_TAG_PATH_FBDB
import com.example.sierraobryan.xkcdocument.Constants.COMICS_PATH_FBDB
import com.example.sierraobryan.xkcdocument.Constants.COMICS_TAG_PATH_FBDB
import com.example.sierraobryan.xkcdocument.Constants.TAG_PATH_FBDB
import com.example.sierraobryan.xkcdocument.data.model.Comic
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTag
import com.example.sierraobryan.xkcdocument.data.model.ComicWithTagFromDatabase
import com.google.firebase.database.*

class ComicListViewModel(application: Application) : AndroidViewModel(application) {

    private val databaseReference: DatabaseReference

    val comicWithTagList : MutableLiveData<List<Comic>> = MutableLiveData()
    val comic: MutableLiveData<ComicWithTag> = MutableLiveData()
    val tagList = MutableLiveData<Set<String>>()
    var exists : Boolean = false

    init {
        databaseReference = FirebaseDatabase.getInstance().reference
        setUpDataBase()
    }

    private fun setUpDataBase() {
        databaseReference.child(ALL_TAG_PATH_FBDB).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this@MainActivity,
//                        "Database error: $databaseError.message", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                tagList.postValue((dataSnapshot.children.mapNotNull { it.getValue<String>(String::class.java) }).toSet())

            }

        })

    }

    fun getComicWithID(comicWithoutTag: Comic) {
        databaseReference.child(COMICS_PATH_FBDB).child(comicWithoutTag.num.toString()).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this@MainActivity,
//                        "Database error: $databaseError.message", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val returnedComic = dataSnapshot.getValue(ComicWithTagFromDatabase::class.java)
                returnedComic?.let {
                    exists = true
                    comic.postValue(returnedComic.convertFromDataBase())
                } ?: run {
                    exists = false
                    comic.postValue(comicWithoutTag.toComicWithTag(mutableListOf()))
                }

            }

        })
    }

    fun addTagToDatabase(comicWithTag: ComicWithTag, newTag: String ) {
        val comickey = databaseReference.child(COMICS_PATH_FBDB).push().key
        comickey?.let {
            if (exists) {
                databaseReference.child(COMICS_PATH_FBDB).child(comicWithTag.num.toString()).child(COMICS_TAG_PATH_FBDB).child(newTag).setValue(newTag)
            } else {
                databaseReference.child(COMICS_PATH_FBDB).child(comicWithTag.num.toString()).setValue(comicWithTag.convertToDataBase())
            }

        }
        val tagsKey = databaseReference.child(TAG_PATH_FBDB).push().key
        tagsKey?.let {
            databaseReference.child(TAG_PATH_FBDB).child(newTag).child(comicWithTag.num.toString()).setValue(comicWithTag.toComic())

        }
        val allTagsKey = databaseReference.child(ALL_TAG_PATH_FBDB).push().key
        allTagsKey?.let {
            databaseReference.child(ALL_TAG_PATH_FBDB).child(newTag).setValue(newTag)

        }

    }

    fun getComicsWithTag(tag: String) {
        databaseReference.child(TAG_PATH_FBDB).child(tag).addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
//                Toast.makeText(this@MainActivity,
//                        "Database error: $databaseError.message", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                comicWithTagList.postValue(dataSnapshot.children.mapNotNull { it.getValue<Comic>(Comic::class.java) })

            }

        })
    }




}