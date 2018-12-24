package com.KUNorz.music;
import android.util.*;

public class Folder
{
	private String folder_name;
	private int amount;
	private String filepath;
	
	public Folder(String filepath,String folder_name){
		this.filepath=filepath;
		this.folder_name=folder_name;
		amount=1;
	}
	
	public void addAmount(){
		amount++;
	}
	
	public String getAmount(){
		return new String(amount+"首  ");
	}
	
	public String getFolder(){
		return folder_name;
	}
	
	public String getFilepath(){
		return filepath;
	}
	
}
