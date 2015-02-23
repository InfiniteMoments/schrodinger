package com.infinitemoments.moments.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;
import java.util.Locale;

/**
 * Created by Salman on 2/22/2015.
 */
public class HomeFragmentsAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public HomeFragmentsAdapter(FragmentManager fm, List<Fragment> f) {
        super(fm);

        fragments = f;
    }

    @Override
    public Fragment getItem(int position) {
        return this.fragments.get(position);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "MOMENTS";
        }
        return null;
    }
}
