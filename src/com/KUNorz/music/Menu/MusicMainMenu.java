package com.KUNorz.music.Menu;
import android.content.*;
import com.KUNorz.music.*;
import android.app.*;
import android.widget.*;
import android.view.*;
import android.widget.ExpandableListView.*;

public class MusicMainMenu implements View.OnClickListener
{
	
	private Activity activity;
	private SlidingMenu menu;
	
	private int[] buttonid;
	private Button[] button;
	private Button exit;
	
	public MusicMainMenu(Activity activity){
		this.activity=activity;
		setMenu();
		setonclicklistener();
	}
	
	private void setMenu(){
		menu = new SlidingMenu(activity);
		menu.setMode(SlidingMenu.LEFT);
		menu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);
		menu.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		menu.setBehindScrollScale(1.0f);
		menu.attachToActivity(activity, SlidingMenu.SLIDING_CONTENT);
		menu.setMenu(R.layout.menu_left);
		
	}
	
	private void setonclicklistener(){
		button=new Button[6];
		buttonid=new int[]{R.id.menu_left_button1,R.id.menu_left_button2,R.id.menu_left_button3,R.id.menu_left_button4,R.id.menu_left_button5,R.id.menu_left_button6};
		button[0]=(Button)activity.findViewById(R.id.menu_left_button2);
		for(int i=0;i<6;i++)button[i]=(Button)activity.findViewById(buttonid[i]);
		for(int i=0;i<6;i++)button[i].setOnClickListener(this);
		exit=(Button)activity.findViewById(R.id.menu_left_exit);
		exit.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					Toast.makeText(activity,"退出",Toast.LENGTH_SHORT).show();
				}
				
			
		});
	}
	
	@Override
	public void onClick(View p1)
	{
		for(int i=0;i<6;i++){
			if(p1.getId()==buttonid[i])Toast.makeText(activity,"点击了Button"+i,Toast.LENGTH_SHORT).show();
		}
		
	}
	
	public void toggle(){
		menu.toggle();
	}
	
	public boolean isShowing(){
		return menu.isMenuShowing();
	}
	
	public void showMenu(boolean o){
		menu.showMenu(o);
	}
}
