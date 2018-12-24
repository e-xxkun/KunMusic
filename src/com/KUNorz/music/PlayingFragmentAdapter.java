package com.KUNorz.music;
import android.support.v4.app.*;
import android.view.*;
import android.util.*;
import com.KUNorz.music.Lyric.*;

public class PlayingFragmentAdapter extends FragmentPagerAdapter
{

	public PlayingFragmentAdapter(FragmentManager fm){
		super(fm);
	}
	
	@Override
	public Fragment getItem(int p1)
	{
		switch(p1){
			case 0:return new LyricFragment();
			case 1:return new PlayingFragment();
			case 2:return new PlayingFragment();
		}
		return new PlayingFragment();
	}

	@Override
	public int getCount()
	{
		return 3;
	}
	
}
