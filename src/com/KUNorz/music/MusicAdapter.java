package com.KUNorz.music;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;
import android.view.View.*;
import android.app.*;

public class MusicAdapter extends BaseAdapter
{

	private ArrayList<Music> musicdatas;
	private Context context;
	private Music music;
	
	public MusicAdapter(Context context, ArrayList<Music> musicdata) {
		musicdatas = musicdata;
		this.context=context;

	}

	@Override
	public int getItemViewType(int position){
		return musicdatas.get(position).getType();
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getCount() {

		return musicdatas.size();
	}

	@Override
	public Object getItem(int arg0) {

		return musicdatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {

		return arg0;
	}
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {

		music=musicdatas.get(arg0);
		int type=getItemViewType(arg0);
		View1 view1=null;
		View2 view2=null;
		View3 view3=null;
		meumOnClickListener meumlistener=null;
		if(arg1==null){
			switch(type){
				case 1:
					if(arg1==null){
					arg1=LayoutInflater.from(context).inflate(R.layout.music, null);
					view1=new View1();
					view1.text1=(TextView) arg1.findViewById(R.id.music_musicname);
					view1.text2=(TextView) arg1.findViewById(R.id.music_musicartist);
					view1.button=(Button)arg1.findViewById(R.id.music_memu);
					arg1.setTag(R.id.tag_first,view1);
					}else{
						
					}
					view1.text1.setText(music.getTitle());
					view1.text2.setText(music.getArtist());
					view1.button.setTag(arg0);
					meumlistener=new meumOnClickListener(arg0,music);
					view1.button.setOnClickListener(meumlistener);
					
					break;

				case 2:
					if(arg1==null){
					arg1=LayoutInflater.from(context).inflate(R.layout.listinfo, null);
					view2=new View2();
					arg1.setTag(R.id.tag_second,view2);
					}else{}
					break;

				case 3:
					if(arg1==null){
					arg1=LayoutInflater.from(context).inflate(R.layout.music, null);
					view3=new View3();
					arg1.setTag(R.id.tag_thrid, view3);
					}else{}
					break;
			}
		}else{
			switch(type){
				case 1:
					view1=(View1) arg1.getTag(R.id.tag_first);
					view1.text1.setText(music.getTitle());
					view1.text2.setText(music.getArtist());
					meumlistener=new meumOnClickListener(arg0,music);
					view1.button.setTag(arg0);
					view1.button.setOnClickListener(meumlistener);
					break;

				case 2:
					view2=(View2) arg1.getTag(R.id.tag_second);
					break;

				case 3:
					view3=(View3) arg1.getTag(R.id.tag_thrid);
					break;
			}
		}
		
		return arg1;
	}
	
	private class meumOnClickListener implements OnClickListener
	{
		private int position;
		private Music m;
		
		public meumOnClickListener(int position,Music m){
			this.position=position;
			this.m=m;
		}
		
		@Override
		public void onClick(View p1)
		{
			MusicMemuDialog memu=new MusicMemuDialog(context,m);
			memu.show();
		}
	}

	class View1{
		TextView text1;
		TextView text2;
		Button button;
	}
	
	class View2{

	}

	class View3{
		TextView text;
	}
	
}
