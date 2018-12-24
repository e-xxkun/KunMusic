package com.KUNorz.music;
import android.app.*;
import android.content.*;
import android.os.*;
import android.widget.*;
import android.view.*;
import com.KUNorz.music.ListDialog.*;

public class ListDialog extends Dialog
{

	private Context context;
	private ListView list;
	private ListAdapter listAdapter;
	private OnListItemClickListener listener;
	
	public ListDialog(Context context){
		super(context,R.style.MusicDialog);
		this.context=context;
	}
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.music_layout2);
        
		setview();
        setCanceledOnTouchOutside(true);
		
	}
	
	private void setview(){
		list=(ListView)findViewById(R.id.layout2_list);
		listAdapter=new ListAdapter(context,StaticDate.ListFiles2);
		list.setAdapter(listAdapter);
		list.setOnItemClickListener(new ListView.OnItemClickListener(){

				@Override
				public void onItemClick(AdapterView<?> p1, View p2, int p3, long p4)
				{
					listener.OnListItemClick(p1,p2,p3,p4);
				}
		});
	}
	
	public void setOnListItemClickListener(OnListItemClickListener listener){
		this.listener=listener;
	}
	
	public interface OnListItemClickListener{
		public void OnListItemClick(AdapterView<?> p1, View p2, int p3, long p4);
	}
	
}
