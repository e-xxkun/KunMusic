package com.KUNorz.music;

public class Listitem
{
	private int type;
	private String list_name;
	private int list_amount;
	private int albumid;
	
	public Listitem(int t,String n,int a,int album){
		type=t;
		list_name=n;
		list_amount=a;
		albumid=album;
	}
	
	public int getType(){
		return type;
	}
	
	public String getListname(){
		return list_name;
	}
	
	public String getListamount(){
		return new String(list_amount+"");
	}
	
	public int getAlbumid(){
		return albumid;
	}
}
