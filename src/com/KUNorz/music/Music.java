package com.KUNorz.music;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.*;
import android.net.Uri;
import android.os.*;

public class Music
{
	private int id;
    private String title;
    private String artist;
    private String uri;
    private String album;
	private int albumid;
    private int time;
    private long size;
	private int type;

    public Music(int type,int id, String title, String artist,String uri, String album,int albumid,int time, long size)
	{
		this.type = type;
        this.id = id;
        this.title = title;
        this.artist = artist;
        this.uri = uri;
        this.album = album;
		this.albumid=albumid;
        this.time = time;
        this.size = size;
    }
	
	public int getType(){
		return type;
	}

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getUri() {
        return uri;
    }

    public String getAlbum() {
        return album;
    }
	
	public int getAlbumid() {
        return albumid;
    }
	
	public Bitmap getAlbumartBitmap(Context context){
		Cursor cur = context.getContentResolver().query(Uri.parse("content://media/external/audio/albums/" + Integer.toString(albumid)),new String[]{"album_art"}, null, null, null);
		String Album=null;
		if (cur.getCount() > 0 && cur.getColumnCount() > 0) 
		{
			cur.moveToNext(); 
			Album = cur.getString(0); 
		} 
		cur.close();
		Bitmap image=BitmapFactory.decodeFile(Album);
		return image;
		}
	
	public static Drawable getAlbumart(Context context,int albumid){
		Cursor cur = context.getContentResolver().query(Uri.parse("content://media/external/audio/albums/" + Integer.toString(albumid)),new String[]{"album_art"}, null, null, null);
		String Album=null;
		if (cur.getCount() > 0 && cur.getColumnCount() > 0) 
		{
			cur.moveToNext(); 
			Album = cur.getString(0); 
		} 
		cur.close();
		Bitmap img=BitmapFactory.decodeFile(Album);
		Drawable albumart = new BitmapDrawable(img);
		return albumart;
	}
	
	public int getTime(){
		return time;
	}
	
	public String getsTime(){
		int s=time/1000;
		String t;
		if((s%60)/10==0){
			t=(s/60+":0"+s%60);
		}else t=(s/60+":"+s%60);
		return t;
	}

    public long getSize() {
        return size;
    }
}
