package com.KUNorz.music;
import android.widget.*;
import android.os.*;
import android.view.*;
import android.content.*;
import java.util.*;
import android.app.*;
import android.view.View.*;
import android.support.v4.app.*;
import android.util.*;
import com.KUNorz.music.MusicActivity.*;
import com.KUNorz.music.Menu.*;
import java.io.*;

public class MusicActivity extends FragmentActivity {
	
	private View view1;
	private View view2;
	private ImageView image;
	private MusicListView list;
	
	private Button musicfileb;
	private Button bmenu;
	private Button headmenu;
	protected static PlayLayout playout;
	private ListAdapter listAdapter;
	private MusicDataHelper musicDataHelper;
	private ArrayList<Listitem> playinglist;
	
	private MusicMainMenu mainmenu;
	
	private VerticalAdapter verticaladapter;
	private VerticalViewPager verticalpager;
	private List<View> verticalviews;

	private IntentFilter intentFilter;
	private MusicBroadcastReceiver musicBroadcastReceiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.music_main);
		playout=new PlayLayout(this,null);
		
		setDatabase();
		LayoutInflater inflater = getLayoutInflater();
		view1=inflater.inflate(R.layout.music_list1,null);
		view2=inflater.inflate(R.layout.music_layout3,null);
		findviewbyid();
		setlist();
		setonclicklistener();
		verticalviews=new ArrayList<View>();
		verticalviews.add(view1);
		verticalviews.add(view2);
		verticalpager=(VerticalViewPager) findViewById(R.id.verticalviewpager);
		verticaladapter=new VerticalAdapter(verticalviews);
		verticalpager.setAdapter(verticaladapter);
		
		String sdcardPath = System.getenv("EXTERNAL_STORAGE")+"/KunMusic";
		StaticDate.MusicFile=new File(sdcardPath);
		if (!StaticDate.MusicFile.exists()) {
			try {
				StaticDate.MusicFile.mkdirs();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		mainmenu=new MusicMainMenu(this);
		
		intentFilter = new IntentFilter();
        intentFilter.addAction("com.KUNorz.music.MusicInfo_SET");
		intentFilter.addAction("com.KUNorz.music.List_SET");
        musicBroadcastReceiver=new MusicBroadcastReceiver();
        registerReceiver(musicBroadcastReceiver, intentFilter);
		Intent broadintent=new Intent("com.KUNorz.music.MusicInfo_SET");
		sendBroadcast(broadintent);
		
		new Thread(new Runnable(){

				@Override
				public void run()
				{
					StaticDate.ListFiles2=MusicList.getList(1);
					StaticDate.ListFiles2.add(0,new Listitem(2,null,0,0));
				}
				
			
		}).start();
    }
    
	private void setDatabase()
    {
    	musicDataHelper=new MusicDataHelper(this, "MUSIC_DB.db", null, 1);
    	StaticDate.musicDatabase=musicDataHelper.getWritableDatabase();
    }
	
	public void findviewbyid()
	{
		headmenu=(Button)findViewById(R.id.head_menu);
		musicfileb=(Button) view1.findViewById(R.id.list1_allfiles);
		bmenu=(Button) view2.findViewById(R.id.layout3_memu);
		list=(MusicListView) view2.findViewById(R.id.layout3_list);
		image=(ImageView)view1.findViewById(R.id.list1_image);
	}
	
    protected void setonclicklistener()
	{
		musicfileb.setOnClickListener(new OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Intent intent=new Intent(MusicActivity.this,MusicFilesActivity.class);
					startActivity(intent);
					overridePendingTransition(0,0);
				}
		});
		
		bmenu.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					ListManageDialog listmenu=new ListManageDialog(p1.getContext());
					listmenu.show();
				}
		});
		
		headmenu.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					mainmenu.showMenu(true);
				}
				
		});
		
	}
	
	private void setlist(){
		playinglist=MusicList.getList(0);
		listAdapter=new ListAdapter(MusicActivity.this,playinglist);
		list.setAdapter(listAdapter);

		list.setOnItemClickListener(new AdapterView.OnItemClickListener(){

				private Listitem listitem;

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					listitem=playinglist.get(p3);
					StaticDate.ListFiles=MusicList.getMusicList(new String[]{listitem.getListname()},null);
					StaticDate.ListFiles.add(0,new Music(2,0,null,null,null,null,0,0,0));
					Intent intent=new Intent(MusicActivity.this,ListInfoActivity.class);
					startActivity(intent);
				}

		});
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event)
	{
		// TODO: Implement this method
		if(keyCode==KeyEvent.KEYCODE_MENU){
			mainmenu.toggle();
		}else if(keyCode==KeyEvent.KEYCODE_BACK){
			if(mainmenu.isShowing()){
				mainmenu.toggle();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}
	
	
	public class MusicBroadcastReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {
			int s=intent.getIntExtra("list_set",0);
			
			if(s==1)setlist();
			else{
				if(StaticDate.Position==-1){

				}else{
					Music music=StaticDate.MusicListFiles.get(StaticDate.Position);
					image.setBackgroundDrawable(music.getAlbumart(context,music.getAlbumid()));
				}
			}
		}
	}
}
