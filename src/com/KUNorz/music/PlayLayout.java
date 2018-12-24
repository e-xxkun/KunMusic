package com.KUNorz.music;
import android.widget.*;
import android.content.*;
import java.util.jar.*;
import android.util.*;
import android.view.*;
import com.KUNorz.music.PlayLayout.*;

public class PlayLayout extends RelativeLayout
{
	private Button mstartb=null;
	private Button mnextb=null;
	private TextView tittle=null;
	private TextView artist=null;
	private ImageView art=null;
	private SeekBar seekbar;
	private RelativeLayout playinglayout=null;
	
	private Context context;
	private BarThread thread;
	private IntentFilter intentFilter;
	private PlayLayout.MusicBroadcastReceiver musicBroadcastReceiver;
	
	public PlayLayout(Context context,AttributeSet attr){
		super(context,attr);
		this.context=context;
		LayoutInflater.from(context).inflate(R.layout.play_layout,this);
		
		findViewById();
		setOnclickListener();
		
		intentFilter = new IntentFilter();
        intentFilter.addAction("com.KUNorz.music.MusicInfo_SET");
		intentFilter.addAction("com.KUNorz.music.Music_STOP");
        musicBroadcastReceiver=new MusicBroadcastReceiver();
        context.registerReceiver(musicBroadcastReceiver, intentFilter);
	}
	
	private void findViewById()
	{
		 mstartb = (Button) findViewById(R.id.playlayout_start);
		 mnextb=(Button) findViewById(R.id.playlayout_next);
		 tittle=(TextView) findViewById(R.id.playlayout_tittle);
		 artist=(TextView) findViewById(R.id.playlayout_artist);
		 art=(ImageView) findViewById(R.id.playlayout_image);
		seekbar=(SeekBar) findViewById(R.id.playlayout_seekbar);
		 playinglayout=(RelativeLayout) findViewById(R.id.playlayout);
	}
	
	private void setOnclickListener(){

		 mstartb.setOnClickListener(new Button.OnClickListener() {
			 
				@Override
				public void onClick(View arg0) {
					if(StaticDate.Position==-1)return;
					Intent intent=new Intent(context,MusicPlayingService.class);
					if(StaticDate.isPlaying==false){
						if(thread==null){
							thread = new BarThread(new BarThread.Run(){

									@Override
									public void run()
									{
										seekbar.setProgress(StaticDate.currentTime);
									}
								});
							thread.start();
						}else{
							thread.resume();
						}
					}else{
						thread.suspend();
					}
					if(StaticDate.isStop){
						intent.putExtra("playstate", StaticDate.MUSIC_PLAY);
						context.startService(intent);
						return;
					}
					intent.putExtra("playstate", StaticDate.MUSIC_PAUSE);
					context.startService(intent);
				}
		 });

		 mnextb.setOnClickListener(new Button.OnClickListener(){
			 
				@Override
				public void onClick(View arg0) {
					if(StaticDate.Position==-1)return;
					Intent intent=new Intent(context,MusicPlayingService.class);
					intent.putExtra("playstate", StaticDate.MUSIC_NEXT);
					context.startService(intent);
				}		
		 });

		 playinglayout.setOnClickListener(new RelativeLayout.OnClickListener(){
			 
				@Override
				public void onClick(View p1)
				{
					if(StaticDate.Position==-1)return;
					Intent intent=new Intent(context,PlayingActivity.class);
					context.startActivity(intent);
				}
		 });

		seekbar.setMax(1000000);
		seekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					if(StaticDate.Position==-1){

					}else{
						thread.suspend();
					}
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					if(StaticDate.Position==-1){
						seekbar.setProgress(0);
					}else{
						StaticDate.currentTime=p1.getProgress();
						Intent intent=new Intent(context,MusicPlayingService.class);
						intent.putExtra("playstate", StaticDate.SEEKBAR_CHANGE);
						context.startService(intent);
						thread.resume();
					}
				}
				
		});
    }

    public class MusicBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getBooleanExtra("stop",false)){
				thread.suspend();
				seekbar.setProgress(0);
				return;
			}
			if(StaticDate.Position==-1)return;
			Music music=StaticDate.MusicListFiles.get(StaticDate.Position);
			seekbar.setMax(music.getTime());
			tittle.setText(music.getTitle());
			artist.setText(music.getArtist());
			art.setBackgroundDrawable(music.getAlbumart(context,music.getAlbumid()));

			if(thread==null){
				thread = new BarThread(new BarThread.Run(){

						@Override
						public void run()
						{
							seekbar.setProgress(StaticDate.currentTime);
						}
					});
				thread.start();
			}else{
				thread.resume();
			}
		}
	}
}
