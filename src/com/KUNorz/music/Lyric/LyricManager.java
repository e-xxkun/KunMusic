package com.KUNorz.music.Lyric;
import com.KUNorz.music.*;
import java.io.*;
import java.util.*;
import android.util.*;
import android.os.*;

public class LyricManager
{
	private Lyric lyric;
	private File lyricfile;
	private LyricView lyricview;
	public boolean isPause=false;
	private boolean isStart=false;
	private BarThread thread;
	public LyricManager(){
		
	}
	
	public void setLyricView(LyricView lyricview){
		this.lyricview=lyricview;
	}
	
	public void setLyric(String tittle){
		if(!searchLyric(tittle)){
			Log.d("llll","No Find");
			return;
		}
		
		FileInputStream FIS = null;
		try
		{
			FIS=new FileInputStream(lyricfile);
		}
		catch (FileNotFoundException e)
		{
			Log.d("llll","false");
		}
		lyric=new Lyric();
		lyric.readLyric(FIS);
		lyricview.setLyric(lyric);
	}
	
	public void resume(){
		if(lyric==null)return;
		if(!isStart){
			thread.start();
			isStart=true;
		}else if(isPause){
			thread.resume();
			isPause=false;
		}else{
			
		}
	}
	
	public void stop(){
		thread.stop();
	}
	
	public void pause(){
		thread.suspend();
		isPause=true;
	}
	
	public void synchrony(final Handler handler){
		if(thread!=null)return;
		thread=new BarThread(new BarThread.Run(){
			
			int i=0;
			
				@Override
				public void run()
				{
					if(lyric==null)return;
					ArrayList<Long> time=lyric.getTime();
					if(StaticDate.currentTime>=time.get(time.size()-1)){
						if(i==time.size()-1)return;
						else{
							handler.sendEmptyMessage(time.size()-1);
							i=time.size()-1;
							}
					}else if(i<time.size()-1&&time.get(i)<=StaticDate.currentTime&&time.get(i+1)>StaticDate.currentTime){
						if(i!=lyricview.getIndex())handler.sendEmptyMessage(i);
					}else if(i<time.size()-2&&time.get(i+1)<=StaticDate.currentTime&&time.get(i+2)>StaticDate.currentTime)handler.sendEmptyMessage(++i);
					else{
						for(int n=0;n<time.size();n++){
							if(time.get(n)<=StaticDate.currentTime&&time.get(n+1)>StaticDate.currentTime){
								handler.sendEmptyMessage(n);
								i=n;
								break;
							}
						}
					}
				}
			});
	}
	
	private boolean searchLyric(String name){
		File[] FileName=StaticDate.MusicFile.listFiles();
		for(int i=0;i<FileName.length;i++){
			if(FileName[i].isFile()&&FileName[i].getName().equals(name+".lrc")){
				lyricfile=FileName[i];
				return true;
			}
		}
		return false;
	}
}
