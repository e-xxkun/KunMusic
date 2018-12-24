package com.KUNorz.music;
import android.content.*;
import android.widget.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.text.*;
import java.util.regex.*;

public class NewListDialog extends Dialog
{

	private Context context;
	private Button positive;
	private Button negative;
	private EditText edittext;
	private OnPositiveClickListener positiveclick;

	public NewListDialog(Context context){
		super(context);
		this.context=context;
		
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_edittext);
		
		setView();
	}

	
	private void setView(){
		edittext=(EditText)findViewById(R.id.new_edit);
		positive=(Button) findViewById(R.id.edittext_positivebutton);
		negative=(Button) findViewById(R.id.edittext_negativebutton);
		positive.setEnabled(false);
		InputFilter filter=new InputFilter() {
			@Override
			public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
				String speChat="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
				Pattern pattern = Pattern.compile(speChat);
				Matcher matcher = pattern.matcher(source.toString());
				if(matcher.find())return "";
				else if(source.equals(" "))return "";
				else return null;
			}
		};
		edittext.setFilters(new InputFilter[]{filter});
		edittext.addTextChangedListener(new TextWatcher(){

				@Override
				public void beforeTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					if(TextUtils.isEmpty(edittext.getText()))positive.setEnabled(false);
					else positive.setEnabled(true);
				}

				@Override
				public void onTextChanged(CharSequence p1, int p2, int p3, int p4)
				{
					// TODO: Implement this method
				}

				@Override
				public void afterTextChanged(Editable p1)
				{
					if(TextUtils.isEmpty(edittext.getText()))positive.setEnabled(false);
					else positive.setEnabled(true);
				}
				
			
		});
		positive.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					int i=MusicList.SetMusicList(edittext.getText().toString());
					if(i==1)Toast.makeText(context,"歌单已存在",Toast.LENGTH_SHORT).show();
					else{
						positiveclick.onPositiveClick(p1,edittext.getText().toString());
						Intent broadintent=new Intent("com.KUNorz.music.List_SET");
						broadintent.putExtra("list_set",1);
						context.sendBroadcast(broadintent);
						StaticDate.ListFiles2=MusicList.getList(1);
						StaticDate.ListFiles2.add(0,new Listitem(2,null,0,0));
						dismiss();
						Toast.makeText(context,"创建成功",Toast.LENGTH_SHORT).show();
					}
				}		
			});

		negative.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					dismiss();
				}
			});
	}

	public void setOnPositiveClickListener(OnPositiveClickListener positiveclick){
		this.positiveclick=positiveclick;
	}

	public interface OnPositiveClickListener{
		public void onPositiveClick(View p1,String s);
	}
}
