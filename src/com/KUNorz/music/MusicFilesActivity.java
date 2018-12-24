package com.KUNorz.music;
import android.os.*;
import android.support.v4.view.*;
import android.view.*;
import android.content.*;
import android.util.*;
import android.view.View.*;
import android.support.v4.app.*;
import com.KUNorz.music.Pager.*;

public class MusicFilesActivity extends FragmentActivity
{
	TabPageIndicator indicator;
	ViewPager viewpager;
	MusicFragmentAdapter fragmentadapter;
	private PlayLayout playout;
	
	protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_allfiles);
		
		playout=MusicActivity.playout;
		
		fragmentadapter=new MusicFragmentAdapter(getSupportFragmentManager());
		viewpager=(ViewPager)findViewById(R.id.pager);
		viewpager.setAdapter(fragmentadapter);
		indicator=(TabPageIndicator)findViewById(R.id.indicator);
		indicator.setViewPager(viewpager);
		Intent broadintent=new Intent("com.KUNorz.music.MusicInfo_SET");
		sendBroadcast(broadintent);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode==KeyEvent.KEYCODE_BACK){
			onBackPressed();
			overridePendingTransition(0,0);
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
}
