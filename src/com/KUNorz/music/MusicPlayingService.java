package com.KUNorz.music;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.widget.Toast;
import android.util.*;
import java.io.*;
import android.widget.*;
import android.app.*;
import android.app.Notification.*;
import android.content.*;
import com.KUNorz.music.MusicPlayingService.*;

public class MusicPlayingService extends Service{

	private MediaPlayer mediaPlayer;
	private int PlayState=0;
	private int CurrentTime=0;
	private int startid;
	private BarThread t;
	private Music music;

	private RemoteViews remoteViews;
	private static final boolean NOTIFICATION_CLOSE = true;
	private Notification.Builder builder;
	private MusicBroadcastReceiver receiver;
	private Notification notification;
	
	@Override
	public IBinder onBind(Intent arg0) {
		return null;
	}
	
	public void onCreate(){
		super.onCreate();
		mediaPlayer=new MediaPlayer();
		remoteViews = new RemoteViews(this.getPackageName(),R.layout.music_notification);
		
		IntentFilter intentFilter = new IntentFilter();
		intentFilter.addAction("com.KUNorz.music.Notification");
		receiver=new MusicBroadcastReceiver();
		registerReceiver(receiver, intentFilter);
		
		setNotification();
		
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		startid=startId;
		if(mediaPlayer==null)return super.onStartCommand(intent, flags, startId);
		PlayState=intent.getIntExtra("playstate", 1);
		Intent broadintent=new Intent("com.KUNorz.music.MusicInfo_SET");
		
		switch (PlayState) {
		case StaticDate.MUSIC_PLAY:
			broadintent.putExtra("playstate",StaticDate.MUSIC_PLAY);
			sendBroadcast(broadintent);
			CurrentTime=0;
			play();
			updatenotification();
			break;
		
			case StaticDate.MUSIC_PAUSE:
			case StaticDate.MUSIC_CONTINUE:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.pause();
				t.suspend();
				broadintent.putExtra("playstate",StaticDate.MUSIC_PAUSE);
			}else{
				mediaPlayer.start();
				t.resume();
				broadintent.putExtra("playstate",StaticDate.MUSIC_CONTINUE);
			}
			break;
		
		case StaticDate.MUSIC_PRIVIOUS:
			if(StaticDate.status==3){
				StaticDate.Position=(int) Math.random()*(StaticDate.MusicListFiles.size()-1);
			}else if(StaticDate.Position==1)StaticDate.Position=StaticDate.MusicListFiles.size()-1;
			else StaticDate.Position--;
			broadintent.putExtra("playstate",StaticDate.MUSIC_PRIVIOUS);
			CurrentTime=0;
			play();
			updatenotification();
			break;
			
		case StaticDate.MUSIC_NEXT:
			if(StaticDate.status==3){
				StaticDate.Position=(int) Math.random()*(StaticDate.MusicListFiles.size()-1);
			}else if(StaticDate.Position>=StaticDate.MusicListFiles.size()-1)StaticDate.Position=1;
			else StaticDate.Position++;
			broadintent.putExtra("playstate",StaticDate.MUSIC_NEXT);
			CurrentTime=0;
			play();
			updatenotification();
			break;
			
		case StaticDate.SEEKBAR_CHANGE:
			CurrentTime=StaticDate.currentTime;
			broadintent.putExtra("playstate",StaticDate.SEEKBAR_CHANGE);
			try{
				if (StaticDate.isStop)mediaPlayer.prepare();
			}catch (IOException e){
				Toast.makeText(this,"播放失败",Toast.LENGTH_SHORT).show();
			}
			play();
			break;
			
		default:
		}
		StaticDate.isStop=false;
		StaticDate.isPlaying=mediaPlayer.isPlaying();
		sendBroadcast(broadintent);
		
		notification.bigContentView=remoteViews;
		startForeground(100, notification);
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private void updatenotification(){
		remoteViews.setTextViewText(R.id.notification_tittle,music.getTitle());
		remoteViews.setTextViewText(R.id.notification_artist,music.getArtist());
		remoteViews.setImageViewBitmap(R.id.notification_image,music.getAlbumartBitmap(this));
	}

	private void setNotification()
	{
		Intent startintent=new Intent("com.KUNorz.music.Notification");
		startintent.putExtra("playstate",StaticDate.MUSIC_PAUSE);
		Intent nextintent=new Intent("com.KUNorz.music.Notification");
		nextintent.putExtra("playstate",StaticDate.MUSIC_NEXT);
		Intent frontintent=new Intent("com.KUNorz.music.Notification");
		frontintent.putExtra("playstate",StaticDate.MUSIC_PRIVIOUS);
		Intent closeintent=new Intent("com.KUNorz.music.Notification");
		closeintent.putExtra("close",NOTIFICATION_CLOSE);
		PendingIntent startpendingintent=PendingIntent.getBroadcast(this,200,startintent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent nextpendingintent=PendingIntent.getBroadcast(this,201,nextintent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent frontpendingintent=PendingIntent.getBroadcast(this,202,frontintent, PendingIntent.FLAG_UPDATE_CURRENT);
		PendingIntent closependingintent=PendingIntent.getBroadcast(this,203,closeintent, PendingIntent.FLAG_UPDATE_CURRENT);
		remoteViews.setOnClickPendingIntent(R.id.notification_start,startpendingintent);
		remoteViews.setOnClickPendingIntent(R.id.notification_next,nextpendingintent);
		remoteViews.setOnClickPendingIntent(R.id.notification_front,frontpendingintent);
		remoteViews.setOnClickPendingIntent(R.id.notification_close,closependingintent);
		
		builder = new Notification.Builder(this.getApplicationContext());
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setWhen(System.currentTimeMillis());
		Intent notificationintent = new Intent(this,PlayingActivity.class);
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, notificationintent, PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(pendingIntent);
		notification = builder.build();
	}
	
	private void play() {
		
		if(mediaPlayer!=null && CurrentTime!=0){
			mediaPlayer.seekTo(CurrentTime);
		}else{
			try{
				mediaPlayer.stop();
				music=StaticDate.MusicListFiles.get(StaticDate.Position);
				mediaPlayer.reset();
				mediaPlayer.setDataSource(music.getUri());
				mediaPlayer.prepare();
				mediaPlayer.start();
			}catch (Exception e) {
				Toast.makeText(this,"播放失败",Toast.LENGTH_SHORT).show();
				Intent intent=new Intent();
				intent.putExtra("playstate",StaticDate.MUSIC_PLAY);
				onStartCommand(intent,0,1);
			}
			
			if(t==null){
				t = new BarThread(new BarThread.Run(){

						@Override
						public void run()
						{
							StaticDate.currentTime=mediaPlayer.getCurrentPosition();
						}
					});
				t.start();
			}else{
				t.resume();
			}
		}
		
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
			public void onCompletion(MediaPlayer mPlayer){
				
				if(mPlayer.isPlaying())return;
				switch (StaticDate.status) {
				case 1:break;
				case 2:
					StaticDate.Position++;
					if(StaticDate.Position==StaticDate.MusicListFiles.size()){
						StaticDate.Position=0;
					}
					break;
					
				case 3:
					StaticDate.Position=(int) Math.random()*(StaticDate.MusicListFiles.size()-1);
					break;
					
				default:break;
				}
				play();
				Intent broadintent=new Intent("com.KUNorz.music.MusicInfo_SET");
				sendBroadcast(broadintent);
				updatenotification();
			}
		});
	}
	
	@Override
	public void onDestroy(){
		t.suspend();
		unregisterReceiver(receiver);
		stopForeground(true);
		mediaPlayer.stop();
		StaticDate.currentTime=0;
		StaticDate.isStop=true;
		StaticDate.isPlaying=false;
		StaticDate.Position=-1;
		//mediaPlayer.release();
		super.onDestroy();
	}
	
	public class MusicBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(intent.getBooleanExtra("close",false)){
				Intent stopintent=new Intent("com.KUNorz.music.Music_STOP");
				stopintent.putExtra("stop",true);
				sendBroadcast(stopintent);
				stopSelf();
			}else{
				onStartCommand(intent,0,startid+1);
			}
		}
	}
}

