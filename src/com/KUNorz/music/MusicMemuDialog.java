package com.KUNorz.music;
import android.app.*;
import android.content.*;
import android.view.*;
import android.view.WindowManager.*;
import android.widget.*;
import android.os.*;


public class MusicMemuDialog extends Dialog
{
	private Context context;
	private Music music;
	private ListDialog dialog;
	
	private TextView tittle;
	private TextView artist;
	private TextView album;
	private TextView addmusic;
	
	public MusicMemuDialog(Context context,Music m){
		super(context,R.style.MusicDialog);
		this.context=context;
		music=m;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_music);
		getWindow().setWindowAnimations(R.style.Dialog_anim);
		setview();
	}
	
	private void setview(){
		tittle=(TextView)findViewById(R.id.memu_tittle);
		artist=(TextView)findViewById(R.id.memu_artist);
		album=(TextView)findViewById(R.id.memu_album);
		tittle.setText(music.getTitle());
		artist.setText("歌手："+music.getArtist());
		album.setText("专辑："+music.getAlbum());
		addmusic=(TextView)findViewById(R.id.memu_addmusic);
		dialog=new ListDialog(context);
		addmusic.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dialog.show();
					dialog.setOnListItemClickListener(new ListDialog.OnListItemClickListener(){

							@Override
							public void OnListItemClick(AdapterView<?> p1, View p2, int p3, long p4)
							{
								if(p3==0){
									NewListDialog newlistdialog=new NewListDialog(context);
									dialog.dismiss();
									newlistdialog.setOnPositiveClickListener(new NewListDialog.OnPositiveClickListener(){

											@Override
											public void onPositiveClick(View p1,String s)
											{
												int o=MusicList.AddMusic(music,s);
												dialog.dismiss();
												if(o==1)
													Toast.makeText(context,"歌曲已存在",Toast.LENGTH_LONG).show();
												else if(o==0)
													Toast.makeText(context,"添加成功",Toast.LENGTH_LONG).show();
												else Toast.makeText(context,"添加失败",Toast.LENGTH_LONG).show();
											}
											
									});
									newlistdialog.show();
								}
								else {
									int o=MusicList.AddMusic(music,StaticDate.ListFiles2.get(p3).getListname());
									dialog.dismiss();
									if(o==1)
										Toast.makeText(context,"歌曲已存在",Toast.LENGTH_LONG).show();
									else if(o==0)
										Toast.makeText(context,"添加成功",Toast.LENGTH_LONG).show();
									else Toast.makeText(context,"添加失败",Toast.LENGTH_LONG).show();
								}
							}
							
						
					});
					dismiss();
				}
		});
	}

	@Override
	public void show()
	{
		super.show();
		LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity=Gravity.BOTTOM;
        layoutParams.width= LayoutParams.MATCH_PARENT;
        layoutParams.height= LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
	}
}
