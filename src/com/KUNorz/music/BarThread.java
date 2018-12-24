package com.KUNorz.music;

public class BarThread implements Runnable
{
	private Thread t;
	private boolean suspended=false;
	private boolean onStop=false;
	private int time=250;
	private Run run;
	
	public BarThread(Run run){
		this.run=run;
	}
	
	@Override
	public void run()
	{
		while(!onStop){
			try{
				run.run();
				Thread.sleep(time);
				synchronized(this){
					while(suspended){
						wait();
					}
				}
			}catch(InterruptedException e){

			}
		}
	}
	
	public void stop(){
		onStop=true;
	}
	
	public void start(){
		if(t==null){
			t=new Thread(this);
		}
		t.start();
	}
	
	public void suspend(){
		suspended = true;
	}
	
	public synchronized void resume(){
		suspended = false;
		notify();
	}
	
	public int getTime() {
		return time;
	}

	public void setTime(int time) {
		this.time = time;
	}

	public interface Run{
		public void run();
	}
	
}
