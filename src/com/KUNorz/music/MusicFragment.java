package com.KUNorz.music;
import android.app.*;
import android.support.v4.app.Fragment;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.widget.AdapterView.*;
import android.content.*;
import android.util.*;
import java.util.*;

public class MusicFragment extends Fragment
{
	private ListView listview;
	private MusicAdapter musicadapter;
	private AlbumArtistAdapter albumartistadapter;
	private FolderAdapter folderadapter;
	private MusicFilesActivity activity;
	private int type;
	
	ArrayList<AlbumArtists> albumartistlist;
	ArrayList<Folder> folderlist;
	
	public MusicFragment(int t){
		type=t;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}
	
	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {
	
		View layout = inflater.inflate(R.layout.music_layout,null);
		setView(layout);
		return layout;
	}
	
	@Override
	public void onAttach(Activity activity0) {
		super.onAttach(activity0);
		activity = (MusicFilesActivity) activity0;
	}
	
	private void setView(View layout){
		listview=(ListView) layout.findViewById(R.id.layout_list);
		switch(type){
			case 0:
				StaticDate.ListFiles=MusicList.getAllMusicList(activity,null,1);
				musicadapter=new MusicAdapter(activity,StaticDate.ListFiles);
				listview.setAdapter(musicadapter);
				listview.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							StaticDate.MusicListFiles= MusicList.getAllMusicList(activity,null,1);
							if(p3==StaticDate.Position){
								
							}else{
								StaticDate.Position=p3;
								Intent intent=new Intent(activity,MusicPlayingService.class);
								intent.putExtra("playstate",StaticDate.MUSIC_PLAY);
								intent.putExtra("currenttime",0);
								activity.startService(intent);
							}

						}


				});

				listview.setOnItemLongClickListener(new OnItemLongClickListener(){

						@Override
						public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
						{

							return false;
						}
				});
				break;
			
			case 1:
				albumartistlist=MusicList.getAlbumList(activity);
				albumartistadapter=new AlbumArtistAdapter(activity,albumartistlist);
				listview.setAdapter(albumartistadapter);
				listview.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							AlbumArtists album=albumartistlist.get(p3);
							StaticDate.ListFiles=MusicList.getAllMusicList(activity,new String[]{album.getAlbum()},2);
							StaticDate.ListFiles.add(0,new Music(2,0,null,null,null,null,0,0,0L));
							Intent intent=new Intent(activity,ListInfoActivity.class);
							activity.startActivity(intent);
						}


					});

				listview.setOnItemLongClickListener(new OnItemLongClickListener(){

						@Override
						public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
						{

							return false;
						}
					});
				break;
				
			case 2:
				albumartistlist=MusicList.getArtistList(activity);
				albumartistadapter=new AlbumArtistAdapter(activity,albumartistlist);
				listview.setAdapter(albumartistadapter);
				listview.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							AlbumArtists artist=albumartistlist.get(p3);
							StaticDate.ListFiles=MusicList.getAllMusicList(activity,new String[]{artist.getArtist()},3);
							Intent intent=new Intent(activity,ListInfoActivity.class);
							activity.startActivity(intent);
						}


					});

				listview.setOnItemLongClickListener(new OnItemLongClickListener(){

						@Override
						public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
						{

							return false;
						}
					});
				break;
				
			case 3:
				folderlist=MusicList.getFolderList(activity);
				folderadapter=new FolderAdapter(activity,folderlist);
				listview.setAdapter(folderadapter);
				listview.setOnItemClickListener(new OnItemClickListener(){

						@Override
						public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
						{
							Folder folder=folderlist.get(p3);
							StaticDate.ListFiles=MusicList.getFolderMusicList(activity,folder.getFilepath());
							Intent intent=new Intent(activity,ListInfoActivity.class);
							activity.startActivity(intent);
						}


					});

				listview.setOnItemLongClickListener(new OnItemLongClickListener(){

						@Override
						public boolean onItemLongClick(AdapterView<?> p1, View p2, int p3, long p4)
						{

							return false;
						}
					});
				break;
		}
	}
}
