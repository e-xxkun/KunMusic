package com.KUNorz.music;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.view.*;
import android.util.*;

public class AlbumArtistAdapter extends BaseAdapter
{

	private ArrayList<AlbumArtists> musicdatas;
	private Context context;
	private AlbumArtists albumartist;

	public AlbumArtistAdapter(Context context, ArrayList<AlbumArtists> musicdata) {
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

		albumartist=musicdatas.get(arg0);
		int type=getItemViewType(arg0);
		View1 view1=null;
		View2 view2=null;
		if(arg1==null){
			switch(type){
				case 1:
					arg1=LayoutInflater.from(context).inflate(R.layout.albumartist, null);
					view1=new View1();
					view1.text1=(TextView) arg1.findViewById(R.id.albumartist_album);
					view1.text2=(TextView) arg1.findViewById(R.id.albumartist_amount);
					view1.text3=(TextView) arg1.findViewById(R.id.albumartist_artist);
					//view1.image=(ImageView) arg1.findViewById(R.id.albumartist_image);
					arg1.setTag(R.id.tag_first,view1);
					view1.text1.setText(albumartist.getAlbum());
					view1.text2.setText(albumartist.getAmount());
					view1.text3.setText(albumartist.getArtist());
					//view1.image.setImageDrawable(Music.getAlbumart(context,albumartist.getAlbumid()));
					break;

				case 2:
					arg1=LayoutInflater.from(context).inflate(R.layout.music, null);
					view2=new View2();
					view2.text1=(TextView) arg1.findViewById(R.id.music_musicname);
					view2.text2=(TextView) arg1.findViewById(R.id.music_musicartist);
					arg1.setTag(R.id.tag_second,view2);
					view2.text1.setText(albumartist.getArtist());
					view2.text2.setText(albumartist.getAmount());
					break;
			}
		}else{
			switch(type){
				case 1:
					view1=(View1) arg1.getTag(R.id.tag_first);
					view1.text1.setText(albumartist.getAlbum());
					view1.text2.setText(albumartist.getAmount());
					view1.text3.setText(albumartist.getArtist());
					//view1.image.setImageDrawable(Music.getAlbumart(context,albumartist.getAlbumid()));
					break;

				case 2:
					view2=(View2) arg1.getTag(R.id.tag_second);
					view2.text1.setText(albumartist.getArtist());
					view2.text2.setText(albumartist.getAmount());
					break;
			}
		}
		return arg1;
	}

	class View1{
		TextView text1;
		TextView text2;
		TextView text3;
		ImageView image;
	}

	class View2{
		TextView text1;
		TextView text2;
		
	}
}
