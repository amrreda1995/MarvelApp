package com.marvel.app.ui.comic_images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.marvel.app.R
import com.marvel.app.databinding.ActivityComicImagesBinding
import com.marvel.app.model.Comic
import com.marvel.app.model.Thumbnail
import kotlinx.android.synthetic.main.activity_comic_images.*

class ComicImagesActivity : AppCompatActivity() {

    private lateinit var comics: ArrayList<Comic>

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comic_images)

        comics = intent.extras!!.getParcelableArrayList("comics")

        numbersTextView.text = comics.size.toString()

        setupViewPager()
        setupListeners()
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(supportFragmentManager)

        val fragmentsList = ArrayList<Fragment>()

        comics.forEach {
            val bundle = Bundle()
            bundle.putParcelable("comics", it)
            val fragment = ComicImageFragment()
            fragment.arguments = bundle

            fragmentsList.add(fragment)
        }

        adapter.setFragments(fragmentsList)

        viewPager.adapter = adapter

        viewPager.offscreenPageLimit = fragmentsList.size
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }
    }
}
