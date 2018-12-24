package com.KUNorz.music.Lyric;
import java.io.*;
import java.util.*;
import java.util.regex.*;
import com.KUNorz.music.*;
import android.app.*;
import android.util.*;

public class Lyric
{
	private ArrayList<String> lyriclist=new ArrayList<String>();
	private ArrayList<Long> timelist=new ArrayList<Long>();
	
	public boolean readLyric(FileInputStream fileInputStream){
		try
		{
			InputStreamReader inputStreamReader =new InputStreamReader(fileInputStream,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
			String word="";
			while ((word= bufferedReader.readLine())!=null){
                if(addTime(word))continue;
                if ((word.indexOf("[ar:")!=-1)||(word.indexOf("[ti:")!=-1)||(word.indexOf("[by:")!=-1)){
                    word=word.substring(word.indexOf(":") + 1, word.indexOf("]"));
                } else {
                    String ss=word.substring(word.indexOf("["), word.indexOf("]") + 1);
                    word=word.replace(ss,"");
                }
                lyriclist.add(word);
            }
            bufferedReader.close();
            inputStreamReader.close();
            fileInputStream.close();
		}catch (FileNotFoundException e){
			e.printStackTrace();
			lyriclist.add("没有找到歌词文件");
			return false;
		}catch (IOException e) {
            e.printStackTrace();
            lyriclist.add("没有读取到歌词");
			return false;
        }
		return true;
	}
	
	public ArrayList<String> getLyric(){
		return lyriclist;
	}
	
	public ArrayList<Long> getTime(){
		return timelist;
	}
	
	private long timeHandler(String string) {
		string = string.replace(".", ":");
		String timeData[] = string.split(":");
		
        int minute = Integer.parseInt(timeData[0]);
        int second = Integer.parseInt(timeData[1]);
        int millisecond = Integer.parseInt(timeData[2]);

        long currentTime=(minute*60+second)*1000+millisecond*10;

        return currentTime;
    }
	
	private boolean addTime(String string) {
        Matcher matcher=Pattern.compile("\\[\\d{1,2}:\\d{1,2}([\\.:]\\d{1,2})?\\]").matcher(string);
        if(matcher.find()) {
            String s=matcher.group();
            timelist.add(timeHandler(s.substring(1,s.length()-1)));
			return false;
        }
		return true;
    }
}
