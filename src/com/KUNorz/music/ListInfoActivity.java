package com.KUNorz.music;

import android.widget.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.util.*;

public class ListInfoActivity extends BaseActivity
{
	private ListView list;
	private PlayLayout playout;
	private MusicAdapter musicadapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_listinfo);
		playout=MusicActivity.playout;				//new PlayLayout(this,null);
		setList();

		Intent broadintent=new Intent("com.KUNorz.music.MusicInfo_SET");
		sendBroadcast(broadintent);
    }

    protected void setList(){
		list=(ListView) findViewById(R.id.listinfo_list);
		musicadapter=new MusicAdapter(this,StaticDate.ListFiles);
		list.setAdapter(musicadapter);
		
		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				public void onItemClick(AdapterView<?> adapterView, View view,int position, long id)
				{
					if(StaticDate.ListFiles.get(position).getType()==2)return;
					StaticDate.MusicListFiles=StaticDate.ListFiles;
					StaticDate.Position=position;
					Intent intent=new Intent(ListInfoActivity.this,MusicPlayingService.class);
					intent.putExtra("playstate",StaticDate.MUSIC_PLAY);
					intent.putExtra("currenttime",0);
					startService(intent);
				}
		});
	}
}
