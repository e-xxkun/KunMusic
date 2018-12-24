package com.KUNorz.music;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.content.*;
import android.util.*;
import android.support.v4.app.*;
import android.support.v4.view.*;
import com.KUNorz.music.Pager.*;

public class PlayingActivity extends FragmentActivity
{

	private Button mstartb;
	private Button mnextb;
	private Button mfrontb;
	private TextView tittle;
	private TextView artist;
	public ImageView art;
	private SeekBar seekbar;

	CirclePageIndicator indicator;
	ViewPager viewpager;
	PlayingFragmentAdapter fragmentadapter;

	private BarThread barthread;
	private IntentFilter intentFilter;
	private MusicBroadcastReceiver musicBroadcastReceiver;

	private Intent broadintent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_playing);
		fragmentadapter = new PlayingFragmentAdapter(getSupportFragmentManager());
		viewpager = (ViewPager) findViewById(R.id.pager2);
		viewpager.setAdapter(fragmentadapter);
		indicator = (CirclePageIndicator) findViewById(R.id.indicator2);
        indicator.setViewPager(viewpager);
		viewpager.setCurrentItem(1);
		viewpager.setOffscreenPageLimit(2);
		
		findViewById();
		seekbar.setMax(1000000);
		setOnclickListener();

		intentFilter = new IntentFilter();
        intentFilter.addAction("com.KUNorz.music.MusicInfo_SET");
		intentFilter.addAction("com.KUNorz.music.Music_STOP");
        musicBroadcastReceiver = new MusicBroadcastReceiver();
        registerReceiver(musicBroadcastReceiver, intentFilter);
		broadintent = new Intent("com.KUNorz.music.MusicInfo_SET");
		sendBroadcast(broadintent);
	}

	@Override
	protected void onStart() {
		super.onStart();
	}

	private void findViewById() {
		mstartb = (Button) findViewById(R.id.playing_start);
		mnextb = (Button) findViewById(R.id.playing_next);
		mfrontb = (Button) findViewById(R.id.playing_front);
		tittle = (TextView) findViewById(R.id.playing_tittle);
		artist = (TextView) findViewById(R.id.playing_artist);
		seekbar = (SeekBar) findViewById(R.id.playing_seekbar);
	}

	private void setOnclickListener() {

		mstartb.setOnClickListener(new Button.OnClickListener() {

				public void onClick(View arg0) {
					Intent intent=new Intent(PlayingActivity.this, MusicPlayingService.class);
					if (StaticDate.isPlaying == false){
						if (barthread == null){
							barthread = new BarThread(new BarThread.Run(){

									@Override
									public void run() {
										seekbar.setProgress(StaticDate.currentTime);
									}
								});
							barthread.start();
						}
						else{
							barthread.resume();
						}
					}
					else{
						barthread.suspend();
					}
					if(StaticDate.isStop){
						intent.putExtra("playstate", StaticDate.MUSIC_PLAY);
						startService(intent);
						return;
					}
					intent.putExtra("playstate", StaticDate.MUSIC_PAUSE);
					startService(intent);
				}
			});
    	mfrontb.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(PlayingActivity.this, MusicPlayingService.class);
					intent.putExtra("playstate", StaticDate.MUSIC_PRIVIOUS);
					startService(intent);
				}		
			});

    	mnextb.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View arg0) {
					Intent intent=new Intent(PlayingActivity.this, MusicPlayingService.class);
					intent.putExtra("playstate", StaticDate.MUSIC_NEXT);
					startService(intent);

				}		
			});

		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3) {
					// TODO: Implement this method
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1) {
					if (StaticDate.Position == -1)
					{

					}
					else
					{
						barthread.suspend();
					}
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1) {
					if (StaticDate.Position == -1)
					{
						seekbar.setProgress(0);
					}
					else
					{
						StaticDate.currentTime = p1.getProgress();
						Intent intent=new Intent(PlayingActivity.this, MusicPlayingService.class);
						intent.putExtra("playstate", StaticDate.SEEKBAR_CHANGE);
						startService(intent);
						barthread.resume();
					}
				}


			});
	}

	public class MusicBroadcastReceiver extends BroadcastReceiver
	{
		@Override
		public void onReceive(Context context, Intent intent) {

			if (intent.getBooleanExtra("stop", false))
			{
				barthread.suspend();
				seekbar.setProgress(0);
				return;
			}
			Music music=StaticDate.MusicListFiles.get(StaticDate.Position);
			tittle.setText(music.getTitle());
			artist.setText(music.getArtist());
			seekbar.setMax(music.getTime());

			if (barthread == null)
			{
				barthread = new BarThread(new BarThread.Run(){

						@Override
						public void run() {
							seekbar.setProgress(StaticDate.currentTime);
						}
					});
				barthread.start();
			}
			else barthread.resume();

		}
	}

}
