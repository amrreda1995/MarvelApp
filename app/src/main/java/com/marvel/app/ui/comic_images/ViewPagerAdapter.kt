package com.marvel.app.ui.comic_images

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class ViewPagerAdapter(fm: FragmentManager?) : FragmentStatePagerAdapter(fm) {

    private var fragmentsList: ArrayList<Fragment> = ArrayList()

    fun setFragments(fragmentsList: ArrayList<Fragment>) {
        this.fragmentsList = fragmentsList
    }

    override fun getItem(position: Int): Fragment {
        return fragmentsList[position]
    }

    override fun getCount(): Int {
        return fragmentsList.size
    }
}