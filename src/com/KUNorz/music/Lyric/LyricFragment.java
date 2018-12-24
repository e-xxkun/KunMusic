package com.KUNorz.music.Lyric;
import android.view.*;
import android.content.*;
import android.os.*;
import com.KUNorz.music.*;
import android.support.v4.app.Fragment;
import android.app.*;
import java.io.*;
import java.util.*;
import android.util.*;

public class LyricFragment extends Fragment
{
	private View layout;
	private LyricView lrcview;
	private Activity activity;
	private Lyric lyric;

	private IntentFilter intentFilter;
	private MusicBroadcastReceiver musicBroadcastReceiver;

	private LyricManager lyricmanager;

	private Handler handler;

	public LyricFragment(){
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);

		intentFilter = new IntentFilter();
        intentFilter.addAction("com.KUNorz.music.MusicInfo_SET");
        musicBroadcastReceiver=new MusicBroadcastReceiver();
        activity.registerReceiver(musicBroadcastReceiver, intentFilter);
		
		lyricmanager=new LyricManager();
		handler=new Handler(){
			public void handleMessage(Message msg) {
				lrcview.scrollToIndex(msg.what);
			}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

		layout=inflater.inflate(R.layout.lyric,container,false);
		lrcview=(LyricView)layout.findViewById(R.id.lyric_lyric);
		lyricmanager.setLyricView(lrcview);
//		lyricmanager.setLyric("被遗忘的时光");		//歌词
		lyricmanager.synchrony(handler);
		if(StaticDate.isPlaying)lyricmanager.resume();
		return layout;
	}

	@Override
	public void onDestroyView() {
		lyricmanager.pause();
		super.onDestroyView();
	}

	@Override
	public void onAttach(Activity activity0) {
		activity=activity0;
		super.onAttach(activity0);
	}

	public class MusicBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			switch(intent.getIntExtra("playstate",0)){
				case StaticDate.MUSIC_PAUSE:
					lyricmanager.pause();
					break;
				case StaticDate.MUSIC_CONTINUE:
					lyricmanager.resume();
					break;
				default:
			}
		}
	}
}
