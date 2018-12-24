package com.KUNorz.music;
import android.os.*;
import android.support.v4.app.Fragment;
import android.view.*;
import android.app.*;
import android.media.*;
import android.widget.*;
import android.content.*;
import java.util.*;
import com.KUNorz.music.PlayingFragment.*;
import android.util.*;

public class PlayingFragment extends Fragment
{
	private ImageView image;
	private View layout;
	private Activity activity;

	private IntentFilter intentFilter;
	private PlayingFragment.MusicBroadcastReceiver musicBroadcastReceiver;
	
	public PlayingFragment(){
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
		
		intentFilter = new IntentFilter();
        intentFilter.addAction("com.KUNorz.music.MusicInfo_SET");
        musicBroadcastReceiver=new MusicBroadcastReceiver();
        activity.registerReceiver(musicBroadcastReceiver, intentFilter);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
	
		layout=inflater.inflate(R.layout.album,container,false);
		
		if(StaticDate.Position==-1){

		}else{
			image=(ImageView) layout.findViewById(R.id.album);
			Music music=StaticDate.MusicListFiles.get(StaticDate.Position);
			image.setBackgroundDrawable(music.getAlbumart(activity,music.getAlbumid()));

		}
		
		return layout;
	}
	
	@Override
	public void onAttach(Activity activity0) {
		activity=activity0;
		super.onAttach(activity0);
	}
		
	
	public class MusicBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			if(StaticDate.Position==-1){

			}else{
				Music music=StaticDate.MusicListFiles.get(StaticDate.Position);
				image.setBackgroundDrawable(music.getAlbumart(context,music.getAlbumid()));

			}
		}
	}
}
