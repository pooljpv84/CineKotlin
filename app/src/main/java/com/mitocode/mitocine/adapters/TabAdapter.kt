package com.mitocode.mitocine.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.manager.Lifecycle
import com.mitocode.mitocine.fragments.movies.MovieListFragment
import com.mitocode.mitocine.fragments.movies.SoonListFragment

class TabAdapter(fragmentManager: FragmentManager, lifecycle: androidx.lifecycle.Lifecycle, private val options:List<String>):
        FragmentStateAdapter(fragmentManager, lifecycle)
{
    override fun getItemCount(): Int
    {
        return options.size
        //cantidad de elementos del Tablayout
    }

    override fun createFragment(position: Int): Fragment
    {
       //retorna un WHEN
        return when (position)
        {
            0->
            {
                MovieListFragment.newInstance()
            }
            1->
            {
                SoonListFragment.newInstance()
            }
            else->
            {
                MovieListFragment.newInstance()
            }

        }

    }


}