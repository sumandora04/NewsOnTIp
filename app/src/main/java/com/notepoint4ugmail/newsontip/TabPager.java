package com.notepoint4ugmail.newsontip;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by SUMAN SHEKHAR on 14-Jan-18.
 */

public class TabPager extends FragmentStatePagerAdapter {
    private Context mContext;

    public TabPager(FragmentManager fm, Context mContext) {
        super(fm);
        this.mContext = mContext;
    }

    @Override
    public Fragment getItem(int position) {
        if(position==0){
            return new TopStories();
        }else if (position==1){
            return new India();
        }else if (position==2){
            return new Business();
        }else if (position==3){
            return new Sports();
        }else if (position==4){
            return new Entertainment();
        }else if (position==5){
            return new Politics();
        }else if (position==6){
            return new Technology();
        }else if (position==7){
            return new Science();
        }else if (position==8){
            return new Health();
        }else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return 9;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return mContext.getString(R.string.first_tab);
            case 1:
                return mContext.getString(R.string.second_tab);
            case 2:
                return  mContext.getString(R.string.fourth_tab);
            case 3:
                return mContext.getString(R.string.fifth_tab);
            case 4:
                return mContext.getString(R.string.sixth_tab);
            case 5:
                return  mContext.getString(R.string.third_tab);
            case 6:
                return mContext.getString(R.string.seventh_tab);
            case 7:
                return mContext.getString(R.string.eighth_tab);
            case 8:
                return  mContext.getString(R.string.ninth_tab);
            default:
                return null;
        }
    }
}
