package com.KUNorz.music;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.*;
import java.util.*;
import android.view.View.*;
import android.content.*;
import android.app.*;
import android.view.*;

public class ListAdapter extends BaseAdapter
{
	
	private ArrayList<Listitem> listDatas;
	private Context context;
	private Listitem listitem;

	public ListAdapter(Context context, ArrayList<Listitem> listData) {
		listDatas = listData;
		this.context=context;

	}

	@Override
	public int getItemViewType(int position){
		return listDatas.get(position).getType();
	}

	@Override
	public int getViewTypeCount() {
		return 3;
	}

	@Override
	public int getCount() {
	
		return listDatas.size();
	}

	@Override
	public Object getItem(int arg0) {
		
		return listDatas.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		
		return arg0;
	}

	/*@Override
	public boolean dispatchTouchEvent(MotionEvent me){
		
		return false;
	}*/
	
	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2) {
		
		listitem=listDatas.get(arg0);
		int type=getItemViewType(arg0);
		View1 view1=null;
		View2 view2=null;
		ListMemuOnClickListener memulisten=null;
		if(arg1==null){
			switch(type){
				case 0:
					arg1=LayoutInflater.from(context).inflate(R.layout.music_list2, null);
					view1=new View1();
					view1.text1=(TextView) arg1.findViewById(R.id.list2_name);
					view1.text2=(TextView) arg1.findViewById(R.id.list2_amount);
					view1.image=(ImageView) arg1.findViewById(R.id.list2_image);
					view1.button=(Button) arg1.findViewById(R.id.list2_memu);
					arg1.setTag(R.id.tag_first,view1);
					view1.text1.setText(listitem.getListname());
					view1.text2.setText(listitem.getListamount());
					view1.image.setImageDrawable(Music.getAlbumart(context,listitem.getAlbumid()));
					view1.button.setTag(arg0);
					memulisten=new ListMemuOnClickListener(arg0,listitem);
					view1.button.setOnClickListener(memulisten);
					break;
					
				case 1:
					arg1=LayoutInflater.from(context).inflate(R.layout.music_list3, null);
					view1=new View1();
					view1.text1=(TextView) arg1.findViewById(R.id.list3_name);
					view1.text2=(TextView) arg1.findViewById(R.id.list3_amount);
					view1.image=(ImageView) arg1.findViewById(R.id.list3_image);
					arg1.setTag(R.id.tag_second,view1);
					view1.text1.setText(listitem.getListname());
					view1.text2.setText(listitem.getListamount());
					view1.image.setImageDrawable(Music.getAlbumart(context,listitem.getAlbumid()));
					
					break;
					
				case 2:
					arg1=LayoutInflater.from(context).inflate(R.layout.new_playinglist,null);
					view2=new View2();
					arg1.setTag(R.id.tag_thrid,view2);
					break;
					
			}
		 }else{
			switch(type){
				case 0:
					view1=(View1) arg1.getTag(R.id.tag_first);
				    view1.text1.setText(listitem.getListname());
					view1.text2.setText(listitem.getListamount());
					view1.image.setImageDrawable(Music.getAlbumart(context,listitem.getAlbumid()));
					memulisten=new ListMemuOnClickListener(arg0,listitem);
					view1.button.setOnClickListener(memulisten);
					break;
					
				case 1:
					view1=(View1) arg1.getTag(R.id.tag_second);
					view1.text1.setText(listitem.getListname());
					view1.text2.setText(listitem.getListamount());
					view1.image.setImageDrawable(Music.getAlbumart(context,listitem.getAlbumid()));
					break;
					
				case 2:
					view2=(View2) arg1.getTag(R.id.tag_thrid);
					break;
			}
		 }
		return arg1;
	}
	
	class ListMemuOnClickListener implements OnClickListener
	{

		private int position;
		Listitem listitem;
		
		public ListMemuOnClickListener(int position,Listitem listitem){
			this.position=position;
			this.listitem=listitem;
		}
		
		@Override
		public void onClick(View p1)
		{
			ListMemuDialog memu=new ListMemuDialog(context,listitem);
			memu.show();
			
		}
		
	}

	class View1{
		TextView text1;
		TextView text2;
		ImageView image;
		Button button;
	}

	class View2{
		Button button;
	}
		
}
