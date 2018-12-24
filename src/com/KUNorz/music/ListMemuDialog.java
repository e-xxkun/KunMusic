package com.KUNorz.music;
import com.KUNorz.music.Listitem;
import com.KUNorz.music.MusicList;
import com.KUNorz.music.R;
import com.KUNorz.music.StaticDate;
import com.KUNorz.music.R.id;
import com.KUNorz.music.R.layout;
import com.KUNorz.music.R.style;

import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.WindowManager.*;
import android.widget.*;
import android.util.*;

public class ListMemuDialog extends Dialog
{
	private Context context;
	private Listitem listitem;

	private TextView play;
	private TextView newname;
	private TextView delete;
	private TextView tittle;
	private TextView t;
	private Button positive;
	private Button negative;
	private View view;
	private AlertDialog D;

	public ListMemuDialog(Context context,Listitem listitem){
		super(context,R.style.MusicDialog);
		this.context=context;
		this.listitem=listitem;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_list);
		getWindow().setWindowAnimations(R.style.Dialog_anim);
		setview();
	}
	
	private void setview()
	{
		tittle=(TextView)findViewById(R.id.memu_list_tittle);
		delete=(TextView)findViewById(R.id.memu_list_del);
		newname=(TextView)findViewById(R.id.memu_list_newname);
		tittle.setText(listitem.getListname());
		delete.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dismiss();
					setD();
				}
				
			
		});
		
	}
	
	private void setD(){
		D=new AlertDialog.Builder(context).create();
		view=LayoutInflater.from(context).inflate(R.layout.dialog_judge,null);
		D.setView(view);
		t=(TextView)view.findViewById(R.id.judge_tittle);
		t.setText(listitem.getListname());
		positive=(Button)view.findViewById(R.id.judge_positivebutton);
		negative=(Button)view.findViewById(R.id.judge_negativebutton);
		positive.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					MusicList.DelMusicList(new String[]{listitem.getListname()});
					D.dismiss();
					Toast.makeText(context,"删除成功",Toast.LENGTH_SHORT).show();
					Intent broadintent=new Intent("com.KUNorz.music.List_SET");
					broadintent.putExtra("list_set",1);
					context.sendBroadcast(broadintent);
					StaticDate.ListFiles2=MusicList.getList(1);
					StaticDate.ListFiles2.add(0,new Listitem(2,null,0,0));
				}
				
			
		});
		negative.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					D.dismiss();
				}
				
		});
		D.show();
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
