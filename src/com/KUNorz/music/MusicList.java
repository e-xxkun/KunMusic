package com.KUNorz.music;

import java.util.*;

import android.database.*;
import android.content.*;
import android.provider.*;
import android.widget.*;
import android.database.sqlite.*;
import java.io.*;
import android.util.*;

public class MusicList
{
	
	public static int SetMusicList(String list_name) {
		Cursor cursor = StaticDate.musicDatabase.query("LIST_NAME",new String[]{"list_name"},"list_name=?",new String[]{list_name},null, null, null);
		if(cursor.getCount()>0)return 1;
		else{
			ContentValues values = new ContentValues();
			values.put("list_name", list_name);
			StaticDate.musicDatabase.insert("LIST_NAME", null, values);	
			return 0;
		}
	}
	
	public static int AddMusic(Music music,String list_name){
		
		Cursor cursor = StaticDate.musicDatabase.query("MUSIC_LIST",new String[]{"music_id","list_name"},"list_name=? AND music_id=?",new String[]{list_name,music.getId()+""},null, null, null);
		if(cursor.getCount()>0){
			return 1;
		}else{
			ContentValues values = new ContentValues();
			values.put("list_name", list_name);
			values.put("music_id", music.getId());
			values.put("music_name", music.getTitle());
			values.put("music_uri", music.getUri());
			values.put("music_album", music.getAlbum());
			values.put("album_id", music.getAlbumid());
			values.put("duration", music.getTime());
			values.put("size", music.getSize());
			values.put("music_artist",music.getArtist());
			StaticDate.musicDatabase.insert("MUSIC_LIST", null, values);
			return 0;
		}
	}
	
	public static void DelMusicList(String[] list_name){
		StaticDate.musicDatabase.delete("LIST_NAME", "list_name = ?", list_name);
		StaticDate.musicDatabase.delete("MUSIC_LIST", "list_name = ?", list_name);
	}
	
	public static void DelMusic(String music_uri,String list_name){
		StaticDate.musicDatabase.delete("LIST_NAME",new String("list_name =? AND music_name = ?"),new String[]{list_name,music_uri});
	}
	
	public static ArrayList<Listitem> getList(int type)
	{
		ArrayList<Listitem> listFiles = new ArrayList<Listitem>();
		Cursor dbCursor=StaticDate.musicDatabase.query("LIST_NAME",null , null,null,null, null, null);
        if (dbCursor.moveToFirst()){
            do{
				String list_name = dbCursor.getString(dbCursor.getColumnIndex("list_name"));
				int list_amount = dbCursor.getInt((dbCursor.getColumnIndex("list_amount")));
				//int albumid = dbCursor.getInt(dbCursor.getColumnIndex("album_id"));
				
				Listitem listitem=new Listitem(type,list_name,list_amount,0);
				listFiles.add(listitem);
			}while(dbCursor.moveToNext());
		}
		dbCursor.close();
		return listFiles;
	}
	
	public static ArrayList<Music> getMusicList(String[] list_name,String order)
	{  
		ArrayList<Music> MusicFiles = new ArrayList<Music>();
		Cursor dbCursor=StaticDate.musicDatabase.query("MUSIC_LIST",null,"list_name=?",list_name,null, null, order);
        if (dbCursor.moveToFirst()){
            do{
            	int id=dbCursor.getInt(dbCursor.getColumnIndex("music_id"));
                String title = dbCursor.getString(dbCursor.getColumnIndex("music_name"));
                String album = dbCursor.getString(dbCursor.getColumnIndex("music_album"));
				int albumid = dbCursor.getInt(dbCursor.getColumnIndex("album_id"));
                String artist = dbCursor.getString(dbCursor.getColumnIndex("music_artist"));
                String url = dbCursor.getString(dbCursor.getColumnIndex("music_uri"));
                int time = dbCursor.getInt(dbCursor.getColumnIndex("duration"));
                long size = dbCursor.getLong(dbCursor.getColumnIndex("size"));
				
                Music music = new Music(1,id ,title, artist,url, album,albumid,time, size);
				MusicFiles.add(music);
            }while(dbCursor.moveToNext());
        }
        dbCursor.close();
		return MusicFiles;
	}
	
	public  static ArrayList<Music> getAllMusicList(Context context,String[] selectionArgs, int sortOrder)
	{
		String selection=null;
		String order=MediaStore.Audio.Media.DEFAULT_SORT_ORDER;
		switch(sortOrder){
			case 1:selection=null;
			break;
			
			case 2:selection=MediaStore.Audio.Media.ALBUM+"=?";
			break;
			
			case 3:selection=MediaStore.Audio.Media.ARTIST+"=?";
			break;
		}
        ArrayList<Music> MusicFiles = new ArrayList<Music>();
        Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,selection,selectionArgs, order);
		if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
                String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				int albumid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
                int time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
                long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));

				if (size > 1024 * 800) {
					if (title.equals("<unknown>") || title.equals("")) {
						title ="N/A";
                    }
                    if ("<unknown>".equals(artist) || "".equals(artist)) {
                        artist = "N/A";
                    }
					Music music = new Music(1,id, title, artist,url, album,albumid,time, size);
					MusicFiles.add(music);
                }
                cursor.moveToNext();
            }
        }
		cursor.close();
        return MusicFiles;
	}
	
	public static ArrayList<AlbumArtists> getAlbumList(Context context){
		ArrayList<AlbumArtists> AlbumFiles=new ArrayList<AlbumArtists>();
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.ALBUM);
		if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ALBUM));
				int albumid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Albums._ID));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.ARTIST));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Albums.NUMBER_OF_SONGS));

				AlbumArtists albumartist = new AlbumArtists(album, artist,albumid,amount,1);
				AlbumFiles.add(albumartist);
                cursor.moveToNext();
            }
        }
		cursor.close();
		return AlbumFiles;
	}
	
	public static ArrayList<AlbumArtists> getArtistList(Context context){
		ArrayList<AlbumArtists> ArtistFiles=new ArrayList<AlbumArtists>();
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.ARTIST);
		if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
				int artistid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Artists._ID));
                String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.ARTIST));
                int amount = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Artists.NUMBER_OF_TRACKS));

				AlbumArtists albumartist = new AlbumArtists(null, artist,artistid,amount,2);
				ArtistFiles.add(albumartist);
                cursor.moveToNext();
            }
        }
		cursor.close();
		return ArtistFiles;
	}
	
	public static ArrayList<Folder> getFolderList(Context context){
		ArrayList<Folder> FolderFiles=new ArrayList<Folder>();
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.ARTIST);
		if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				int sdd=url.lastIndexOf(File.separatorChar);
				String filepath0=url.substring(0, sdd);
				String filepath=filepath0.substring(0,url.lastIndexOf(File.separatorChar,sdd-1));
				
				if(j(FolderFiles,filepath)){
					
				}else{
					String folder_name=filepath0.substring(url.lastIndexOf(File.separatorChar,sdd-1)+1,sdd);
					Folder folder = new Folder(filepath,folder_name);
					FolderFiles.add(folder);
				}
                cursor.moveToNext();
            }
        }
		cursor.close();
		return FolderFiles;
	}
	
	public static ArrayList<Music> getFolderMusicList(Context context,String foldername){
		ArrayList<Music> FolderMusicFiles=new ArrayList<Music>();
		Cursor cursor = context.getContentResolver().query(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,null,null,null,MediaStore.Audio.Media.TITLE);
		if (cursor.moveToFirst()){
            while (!cursor.isAfterLast()){
				int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID));
				String title = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));
				String album = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM));
				int albumid = cursor.getInt(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID));
				String artist = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
				int time = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION));
				long size = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE));
                String url = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
				int sdd=url.lastIndexOf(File.separatorChar);
				String filepath0=url.substring(0, sdd);
				String filepath=filepath0.substring(0,url.lastIndexOf(File.separatorChar,sdd-1));

				if(filepath.equals(foldername)&&size > 1024 * 800){
					if (title.equals("<unknown>") || title.equals("")) {
						title ="N/A";
					}
					if ("<unknown>".equals(artist) || "".equals(artist)) {
						artist = "N/A";
					}
					Music music = new Music(1,id, title, artist,url, album,albumid,time, size);
					FolderMusicFiles.add(music);
				}else{
					
				}
                cursor.moveToNext();
            }
        }
		cursor.close();
		return FolderMusicFiles;
	}
	
	public static boolean j(ArrayList<Folder> FL,String filepath){
		if(FL.size()==0)return false;
		for(Folder f:FL){
			if(filepath.equals(f.getFilepath())){
				f.addAmount();
				return true;
			}
		}
		return false;
	}
	
}
