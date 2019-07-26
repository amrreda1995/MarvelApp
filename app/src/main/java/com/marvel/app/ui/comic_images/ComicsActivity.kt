package com.marvel.app.ui.comic_images

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.marvel.app.R
import com.marvel.app.model.ComicItemViewModel
import kotlinx.android.synthetic.main.activity_comics.*

class ComicsActivity : AppCompatActivity() {

    private lateinit var viewModels: ArrayList<ComicItemViewModel>

    private lateinit var adapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comics)

        viewModels = intent.extras!!.getParcelableArrayList("viewModels")

        numbersTextView.text = "1/${viewModels.size}"

        setupViewPager()
        setupListeners()
    }

    private fun setupViewPager() {
        adapter = ViewPagerAdapter(supportFragmentManager)

        val fragmentsList = ArrayList<Fragment>()

        addItemsToFragmentsList(fragmentsList)

        adapter.setFragments(fragmentsList)

        viewPager.adapter = adapter

        viewPager.offscreenPageLimit = fragmentsList.size

        setupViewPagerChangedPagesListener()
    }

    private fun addItemsToFragmentsList(fragmentsList: ArrayList<Fragment>) {
        viewModels.forEach {
            val bundle = Bundle()
            bundle.putParcelable("viewModel", it)
            val fragment = ComicFragment()
            fragment.arguments = bundle

            fragmentsList.add(fragment)
        }
    }

    private fun setupViewPagerChangedPagesListener() {
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                numbersTextView.text = "${position + 1}/${viewModels.size}"
            }
        })
    }

    private fun setupListeners() {
        closeButton.setOnClickListener {
            finish()
        }
    }
}
