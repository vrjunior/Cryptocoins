package us.guihouse.criptocoins.adapters;

import android.app.Application;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;

import us.guihouse.criptocoins.FavoriteFragment;
import us.guihouse.criptocoins.MainFragment;
import us.guihouse.criptocoins.R;

/**
 * Created by valmir.massoni on 03/10/2016.
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static int AMOUNT_ITEMS = 2;
    Context mContext;

    public FragmentPagerAdapter(FragmentManager fm, Context c) {
        super(fm);
        mContext = c;
    }


    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new MainFragment();
            case 1:
                return new FavoriteFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return AMOUNT_ITEMS;
    }


    @Override
    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return mContext.getString(R.string.fragmentMainTitle);
            case 1:
                return mContext.getString(R.string.fragmentFavoriteTitle);
            default:
                return null;
        }
    }
}