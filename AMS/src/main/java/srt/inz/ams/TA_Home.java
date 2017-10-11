package srt.inz.ams;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class TA_Home extends Activity{
	
	Spinner rq_dpt,rq_sem,rq_prd;
	ArrayAdapter<String> s1,s2,s3; String sdpt,ssem,sprd;
	
	Button bcn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ta_home);
		
		rq_dpt=(Spinner)findViewById(R.id.spinner_dept);
		rq_sem=(Spinner)findViewById(R.id.spinner_sem);
		rq_prd=(Spinner)findViewById(R.id.spinner_period);
		bcn=(Button)findViewById(R.id.button_cont);
		
		bcn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences share=getSharedPreferences("shids", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=share.edit();
				ed.putString("shdpt", sdpt);
				ed.putString("shsem", ssem);
				ed.putString("shprd", sprd);
				ed.commit();
				
				Intent i=new Intent(getApplicationContext(),Studentlist.class);
				startActivity(i);
				
			}
		});
		
		 String[] dpt = getResources().getStringArray(R.array.dept);
		 String[] sem = getResources().getStringArray(R.array.semester);
		 String[] prd = getResources().getStringArray(R.array.period);
		 
	        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dpt);
		    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    rq_dpt.setAdapter(s1);
		    rq_dpt.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sdpt=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    s2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sem);
		    s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    rq_sem.setAdapter(s2);
		    rq_sem.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					ssem=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    
		    s3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prd);
		    s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    rq_prd.setAdapter(s3);
		    rq_prd.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sprd=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
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
