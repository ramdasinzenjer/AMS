package srt.inz.ams;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class Adminclg extends Activity{
	
	Button vastf,vastud,up_date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.adminclg);
		
		vastf=(Button)findViewById(R.id.bt_vastaff);
		vastud=(Button)findViewById(R.id.bt_vastud);
		up_date=(Button)findViewById(R.id.bt_update_attendancedate);
		
		vastf.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(getApplicationContext(),VAStaff.class);
				startActivity(i);
				
			}
		});
		
		vastud.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				Intent i=new Intent(getApplicationContext(),VAStudent.class);
				startActivity(i);
				
			}
		});
		
		up_date.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				
				Intent i=new Intent(getApplicationContext(),Exp_dateup.class);
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
