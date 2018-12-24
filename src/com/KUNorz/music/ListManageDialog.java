package com.KUNorz.music;
import android.app.*;
import android.content.*;
import android.os.*;
import android.view.*;
import android.view.WindowManager.*;
import android.widget.*;

public class ListManageDialog extends Dialog
{
	private Context context;

	private Button newlist;
	private Button manage;

	public ListManageDialog(Context context){
		super(context,R.style.MusicDialog);
		this.context=context;
	}

	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_listmanage);
		getWindow().setWindowAnimations(R.style.Dialog_anim);
		setview();
	}

	private void setview()
	{
		manage=(Button)findViewById(R.id.menu_listmanage_manage);
		newlist=(Button)findViewById(R.id.menu_listmanage_new);
		manage.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
				}
			
		});

		newlist.setOnClickListener(new Button.OnClickListener(){

				@Override
				public void onClick(View p1)
				{
					// TODO: Implement this method
					dismiss();
					NewListDialog newlistdialog=new NewListDialog(context);
					newlistdialog.setOnPositiveClickListener(new NewListDialog.OnPositiveClickListener(){

							@Override
							public void onPositiveClick(View p1,String s)
							{
								dismiss();
							}

						});
					newlistdialog.show();
				}

			});
	}
	
	@Override
	public void show()
	{
		super.show();
		LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity=Gravity.BOTTOM;
        layoutParams.width= LayoutParams.MATCH_PARENT;
        layoutParams.height= LayoutParams.WRAP_CONTENT;
        getWindow().getDecorView().setPadding(0, 0, 0, 0);
        getWindow().setAttributes(layoutParams);
	}
}
