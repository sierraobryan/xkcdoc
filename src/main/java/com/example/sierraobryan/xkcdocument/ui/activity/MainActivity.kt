package com.example.sierraobryan.xkcdocument.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.sierraobryan.xkcdocument.R
import com.example.sierraobryan.xkcdocument.data.model.ComicTag
import com.example.sierraobryan.xkcdocument.data.viewModel.ComicListViewModel
import com.example.sierraobryan.xkcdocument.data.viewModel.XkcdViewModel
import com.example.sierraobryan.xkcdocument.ui.fragment.HomeFragment
import com.example.sierraobryan.xkcdocument.ui.fragment.TagListFragment
import com.example.sierraobryan.xkcdocument.ui.fragment.SingleComicFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*


class MainActivity : AppCompatActivity() {

    private lateinit var xkcdViewModel: XkcdViewModel
    private lateinit var comicListViewModel: ComicListViewModel
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        if (savedInstanceState == null) {
            switchFragment(HomeFragment.newInstance())
        }

        xkcdViewModel = ViewModelProviders.of(this).get(XkcdViewModel::class.java)
        comicListViewModel = ViewModelProviders.of(this).get(ComicListViewModel::class.java)
        setUpDataBase()


    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                switchFragment(HomeFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                switchFragment(SingleComicFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                switchFragment(TagListFragment.newInstance())
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun switchFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commitNow()
    }

    private fun setUpDataBase() {
        databaseReference = FirebaseDatabase.getInstance().reference
        databaseReference.child("comics").addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError) {
                Toast.makeText(this@MainActivity,
                        "Database error: $databaseError.message", Toast.LENGTH_SHORT).show()
            }

            override fun onDataChange(dataSnapshot: DataSnapshot) {
                comicListViewModel.setComicTagList(dataSnapshot.children.mapNotNull { it.getValue<ComicTag>(ComicTag::class.java) })

            }

        })

    }

    fun addTagToDatabase(comicTag: ComicTag) {
        val key = databaseReference.child("comics").push().key
        key?.let {
            databaseReference.child("comics").child(System.currentTimeMillis().toString()).setValue(comicTag)

        }
    }
}
