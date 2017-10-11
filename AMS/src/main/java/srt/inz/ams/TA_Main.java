package srt.inz.ams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class TA_Main extends Activity{
	
	Button bmark,bview,bperc;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ta_main);
		bmark=(Button)findViewById(R.id.button_mark);
		bview=(Button)findViewById(R.id.button_view);
		bperc=(Button)findViewById(R.id.button_percentage);
		
		bmark.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), TA_Home.class);
				startActivity(i);
			}
		});
		
		bview.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), TA_View.class);
				startActivity(i);
			}
		});
		bperc.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent i=new Intent(getApplicationContext(), TA_Vperc.class);
				startActivity(i);
			}
		});
		
	}
	
	public void logOutfunction(View view)
	{
		Intent loginscreen=new Intent(this,Main_page.class);
		loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginscreen);
		this.finish();
	}


}
