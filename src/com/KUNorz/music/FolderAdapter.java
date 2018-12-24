package com.KUNorz.music;
import android.widget.*;
import android.view.*;
import java.util.*;
import android.content.*;
import android.util.*;

public class FolderAdapter extends BaseAdapter
{
	private ArrayList<Folder> musicdatas;
	private Context context;
	private Folder folder;

	public FolderAdapter(Context context, ArrayList<Folder> musicdata) {
		musicdatas = musicdata;
		this.context=context;
	} 

	@Override
	public int getCount()
	{
		return musicdatas.size();
	}

	@Override
	public Object getItem(int p1)
	{
		return musicdatas.get(p1);
	}

	@Override
	public long getItemId(int p1)
	{
		return p1;
	}

	@Override
	public View getView(int p1, View p2, ViewGroup p3)
	{
		folder=musicdatas.get(p1);
		View1 view1=null;
		if(p2==null){
			p2=LayoutInflater.from(context).inflate(R.layout.folder, null);
			view1=new View1();
			view1.text1=(TextView) p2.findViewById(R.id.folder_name);
			view1.text2=(TextView) p2.findViewById(R.id.folder_amount);
			view1.text3=(TextView) p2.findViewById(R.id.folder_path);
			p2.setTag(R.id.tag_first,view1);
			view1.text1.setText(folder.getFolder());
			view1.text2.setText(folder.getAmount());
			view1.text3.setText(folder.getFilepath());
		}else{
			view1=(View1) p2.getTag(R.id.tag_first);
			view1.text1.setText(folder.getFolder());
			view1.text2.setText(folder.getAmount());
			view1.text3.setText(folder.getFilepath());
		}
		return p2;
	}

	class View1{
		TextView text1;
		TextView text2;
		TextView text3;
	}
	
}
