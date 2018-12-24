package com.KUNorz.music;

public class AlbumArtists
{
	private String album;
	private String artist;
	private int albumid;
	private int amount;
	private int type;
	
	public AlbumArtists(String album,String artist,int albumid,int amount,int type){
		this.album=album;
		this.artist=artist;
		this.albumid=albumid;
		this.amount=amount;
		this.type=type;
	}
	
	public String getAlbum(){
		return album;
	}
	
	public String getArtist(){
		return artist;
	}
	
	public String getAmount(){
		return new String(amount+"首  ");
	}
	
	public int getAlbumid(){
		return albumid;
	}
	
	public int getType(){
		return type;
	}
}
