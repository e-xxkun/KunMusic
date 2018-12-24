package com.KUNorz.music;
import android.widget.*;
import android.content.*;
import android.util.*;
import android.view.*;

public class MusicListView extends ListView
{
	public MusicListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev)
	{
		float Downx=0,Downy=0;
		switch(ev.getAction()){
			case MotionEvent.ACTION_DOWN:
				Downx=ev.getX();
				Downy=ev.getY();
				getParent().requestDisallowInterceptTouchEvent(true);
				break;

			case MotionEvent.ACTION_MOVE:
				if(Math.abs(ev.getX()-Downx)>Math.abs(ev.getY()-Downy))
					getParent().requestDisallowInterceptTouchEvent(true);
				else getParent().requestDisallowInterceptTouchEvent(false);
				break;

			case MotionEvent.ACTION_UP:

			case MotionEvent.ACTION_CANCEL:
				getParent().requestDisallowInterceptTouchEvent(false);
				break;
		}
		return super.dispatchTouchEvent(ev);
	}
	
	
}
