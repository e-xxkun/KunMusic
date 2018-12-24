package com.KUNorz.music;

import java.util.ArrayList;

import android.database.sqlite.SQLiteDatabase;
import java.util.*;
import java.io.*;

public class StaticDate {
	
	public static File MusicFile;
	
	protected static List<Music> ALLMusicListFiles;
	
	protected static ArrayList<Music> MusicListFiles;
	
	protected static ArrayList<Music> ListFiles;
	
	protected static ArrayList<Listitem> ListFiles2;
	
	protected static SQLiteDatabase musicDatabase;
	
	public static final int MUSIC_PLAY=1;
	
	public static final int MUSIC_PAUSE=2;
	
	public static final int MUSIC_CONTINUE=3;
			
	public static final int MUSIC_PRIVIOUS=4;
	
	public static final int MUSIC_NEXT=5;
	
	public static final int SEEKBAR_CHANGE=6;

	public static int Position=-1;
	
	protected static int status=2;
	
	public static boolean isPlaying=false;
	
	public static boolean isStop=false;
	
	public static int currentTime=1;
}
