package us.guihouse.criptocoins.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import us.guihouse.criptocoins.FavoriteFragment;
import us.guihouse.criptocoins.MainFragment;

/**
 * Created by valmir.massoni on 03/10/2016.
 */

public class FragmentPagerAdapter extends FragmentStatePagerAdapter {

    private static int AMOUNT_ITEMS = 2;

    public FragmentPagerAdapter(FragmentManager fm) {
        super(fm);
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
        return "Page " + (position + 1);
    }
}