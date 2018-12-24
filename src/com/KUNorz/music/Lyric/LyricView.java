package com.KUNorz.music.Lyric;
import android.widget.*;
import java.util.*;
import android.content.*;
import android.util.*;
import android.view.*;
import android.graphics.*;

public class LyricView extends ScrollView
{
	private LinearLayout mainview;
	private LinearLayout lrcview;
	private ArrayList<TextView> lrcitemview;
	private ArrayList<String> lrctext;
	private ArrayList<Long> lrctime;
	private ArrayList<Integer> lrcitemheight;
	private int height;
	private int width;
	private int selindex;
	private int textSize=15;
	private int textOnSelectColor=Color.YELLOW;

	private LinearLayout blank1;
	private LinearLayout blank2;
	private LyricView.OnLyricScrollChangeListener LyricScrollChangeListener;

	private Scroller scroller;
	
	public LyricView(Context context, AttributeSet attrs){
		super(context,attrs);
		scroller = new Scroller(context);
		init();
	}
	
	private void init(){
		
		mainview=new LinearLayout(getContext());
		blank1=new LinearLayout(getContext());
		blank2=new LinearLayout(getContext());
		lrcitemview=new ArrayList<TextView>();
		mainview.setOrientation(LinearLayout.VERTICAL);
		final ViewTreeObserver VTO =mainview.getViewTreeObserver();
        VTO.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
				@Override
				public void onGlobalLayout() {
					height = LyricView.this.getHeight();
					width = LyricView.this.getWidth();
					refreshMainView();
				}
		});
		setSmoothScrollingEnabled(true);
        addView(mainview);
	}
	
	private void refreshMainView(){
		mainview.removeAllViews();
		LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(width,height/2);
		mainview.addView(blank1,params);
		if(lrcview!=null)mainview.addView(lrcview);
		mainview.addView(blank2,params);
	}
	
	private void refreshLrcView(){
		if(lrcview==null)lrcview=new LinearLayout(getContext());
        lrcview.setOrientation(LinearLayout.VERTICAL);
        lrcview.removeAllViews();
        lrcitemview.clear();
        lrcitemheight=new ArrayList<Integer>();
        selindex=0;

        for(int i=0;i<lrctext.size(); i++) {
            final TextView textView = new TextView(getContext());
			textView.setPadding(80,20,80,20);
			textView.setTextSize(textSize);
			textView.setTextColor(Color.argb(225-i*20,245,245,245));
			textView.setGravity(Gravity.CENTER);
            textView.setText(lrctext.get(i));
            final ViewTreeObserver VTO1= textView.getViewTreeObserver();
            final int index = i;
            VTO1.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
					@Override
					public void onGlobalLayout() {
						lrcitemheight.add(index, textView.getHeight());
					}
				});
            lrcview.addView(textView);
            lrcitemview.add(index, textView);
			
        }
		refreshMainView();
    }
	
	public void setLyric(Lyric lyric){
		Log.d("llll", "lyric "+lyric);
		
		if(lyric.getLyric().size()!=lyric.getTime().size()){
			Toast.makeText(getContext(),"歌词加载失败",Toast.LENGTH_SHORT).show();
            throw new IllegalArgumentException();
        }
        this.lrctext=lyric.getLyric();
        this.lrctime=lyric.getTime();
		refreshLrcView();
	}
	
	private void setSelected(int index) {
        if(index==selindex)return;
		int alphaValue=450*lrcitemview.get(7).getHeight()/height-8;
        for(int i=0;i<lrctext.size();i++) {
            if(i==index){
				lrcitemview.get(i).setTextColor(textOnSelectColor);
			}else{
				lrcitemview.get(i).setTextColor(Color.argb(225-Math.abs(index-i)*alphaValue,245,245,245));
			}
        }
        selindex=index;
    }
	
	public int getIndex(){
		return selindex;
	}
	
	private int getIndex(int length) {
        int index=0;
        int sum=0;
        while(sum<=length) {
            sum+=lrcitemheight.get(index);
            index++;
        }
        return index-1;
    }
	
	@Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        setSelected(getIndex(t));
        if(LyricScrollChangeListener!=null) {
            LyricScrollChangeListener.onLyricScrollChange(getIndex(t), getIndex(oldt));
        }
    }
	
	public void scrollToIndex(int index) {
        if(index < 0) {
            smoothScrollToSlow(0, 0,1500);        }
        if(index < lrcitemview.size()) {
            int sum = 0;
            for(int i = 0; i <= index - 1; i++){
                sum += lrcitemheight.get(i);
            }
            sum += lrcitemheight.get(index) / 2;
            smoothScrollToSlow(0, sum,1500);
        }
		setSelected(index);
    }
	
	public void smoothScrollToSlow(int fx, int fy, int duration) {  
        int dx = fx - getScrollX();
        int dy = fy - getScrollY();
        smoothScrollBySlow(dx, dy, duration);  
    }
	
    public void smoothScrollBySlow(int dx, int dy,int duration) {
        scroller.startScroll(getScrollX(), getScrollY(), dx, dy,duration);
        invalidate();
    } 

    @Override  
    public void computeScroll() {  
	
        if (scroller.computeScrollOffset()) {    
            scrollTo(scroller.getCurrX(), scroller.getCurrY());  
            postInvalidate();  
        }  
        super.computeScroll();  
    }
	
	public boolean scrollFinish(){
		return scroller.computeScrollOffset();
	}

    @Override
    public void fling(int velocityY) {
        super.fling(velocityY/2);
    }
	
	public void setTextAttribute(int size,int onselectcolor,int distance){
		textSize=size;
		textOnSelectColor=onselectcolor;
	}
	
	public void setOnLyricScrollChangeListener(OnLyricScrollChangeListener onlyricscrollchangelistener){
        LyricScrollChangeListener = onlyricscrollchangelistener;
    }

    public interface OnLyricScrollChangeListener {
        public void onLyricScrollChange(int index,int oldindex);
    }
}
