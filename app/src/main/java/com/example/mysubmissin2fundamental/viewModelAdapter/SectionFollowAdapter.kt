package com.example.mysubmissin2fundamental.viewModelAdapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.mysubmissin2fundamental.viewActivity.fragment.FollowersFragment
import com.example.mysubmissin2fundamental.viewActivity.fragment.Following_Fragment
import com.example.mysubmissin2fundamental.R

class SectionFollowAdapter(private val mContext: Context, fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var username: String? = null

    @StringRes
    private val TAB_TITLES = intArrayOf(
        R.string.tab_text_1,
        R.string.tab_text2
    )

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position){
            0 -> fragment = username?.let {
                FollowersFragment.newInstanse(
                    it
                )
            }
            1 -> fragment = username?.let {
                Following_Fragment.newInstanse(
                    it
                )
            }
        }

        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }


}