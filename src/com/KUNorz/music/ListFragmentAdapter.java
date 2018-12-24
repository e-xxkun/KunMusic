package com.KUNorz.music;
import android.support.v4.app.*;
import android.util.*;


public class ListFragmentAdapter extends FragmentPagerAdapter
{
	
	public ListFragmentAdapter(FragmentManager fm) {
        super(fm);
    }

	@Override
	public Fragment getItem(int p1)
	{
		return new ListFragment(p1);
	}

	@Override
	public int getCount()
	{
		return 2;
	}
	
}
