package com.KUNorz.music;
import android.support.v4.app.*;
import android.os.*;
import android.view.*;
import android.util.*;

public class ListFragment extends Fragment
{
	private int type;
	
	public ListFragment(int t){
		type=t;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setRetainInstance(true);
	}

	public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

		View layout = inflater.inflate(R.layout.music_list1,null);
		return layout;
	}

}
