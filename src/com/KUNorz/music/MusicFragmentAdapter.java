package com.KUNorz.music;

import android.support.v4.app.*;

public class MusicFragmentAdapter extends FragmentPagerAdapter
{
	
	protected static final String[] TITTLE = new String[] {"单曲","专辑","歌手","文件夹"};

	public MusicFragmentAdapter(FragmentManager fm) {
        super(fm);
    }
	
	@Override
	public Fragment getItem(int p1)
	{
		return new MusicFragment(p1);
	}

	@Override
	public int getCount()
	{
		return TITTLE.length;
	}
	
	@Override
	public CharSequence getPageTitle(int position)
	{
		return MusicFragmentAdapter.TITTLE[position % TITTLE.length];
    }
	
}
