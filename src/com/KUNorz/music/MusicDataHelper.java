package com.KUNorz.music;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class MusicDataHelper extends SQLiteOpenHelper{
	
	public static final String LIST_NAME="create table LIST_NAME(" +
			"id integer primary key autoincrement," +
			"list_name text," +
			"list_amount integer)";
	public static final String MUSIC_LIST="create table MUSIC_LIST(" +
			"id integer primary key autoincrement," +
			"list_name text," +
			"music_name varchar(256)," +
			"music_uri varchar(128)," +
			"album_id varchar(128)," +
			"duration long," +
			"size long," +
			"music_artist varchar(256)," +
			"music_album text," +
			"music_id integer)";
	
	public MusicDataHelper(Context context, String name, CursorFactory factory,
			int version) {
		super(context, name, factory, version);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase arg0) {
		// TODO Auto-generated method stub
		arg0.execSQL(LIST_NAME);
		arg0.execSQL(MUSIC_LIST);
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}
	
}
