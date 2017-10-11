package srt.inz.ams;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

public class TA_Vperc extends Activity {

	Spinner sps,spd,spm,spsem; Button bt;
	ArrayAdapter<String> s1,s2,s3,s4; String ss,sd,sm,sem,hs,snam;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ta_vperc);
		sps=(Spinner)findViewById(R.id.spinner_Sname);
		spd=(Spinner)findViewById(R.id.spinner_Depart);
		spm=(Spinner)findViewById(R.id.spinner_Month);
		spsem=(Spinner)findViewById(R.id.spinner_Sem);
		bt=(Button)findViewById(R.id.button_monthlyat);
		
		String[] dpt = getResources().getStringArray(R.array.dept);
		 String[] mnt = getResources().getStringArray(R.array.month);
		 String[] smt = getResources().getStringArray(R.array.semester);
		
		 s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dpt);
		    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spd.setAdapter(s1);
		    spd.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sd=arg0.getItemAtPosition(arg2).toString();
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    s2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,mnt);
		    s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spm.setAdapter(s2);
		    spm.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sm=arg0.getItemAtPosition(arg2).toString();
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    s4=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,smt);
		    s4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spsem.setAdapter(s4);
		    spsem.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sem=arg0.getItemAtPosition(arg2).toString();
					new getstudent_det().execute();
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    
		    bt.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					SharedPreferences share=getSharedPreferences("mainkey", MODE_WORLD_READABLE);
					SharedPreferences.Editor ed =share.edit();
					ed.putString("student", ss);
					ed.putString("department", sd);
					ed.putString("sem", sem);
					ed.putString("month", sm);
					ed.commit();
					
					Intent i=new Intent(getApplicationContext(),Monthly_view.class);
					startActivity(i);
					
				}
			});
		    
		 
	}
	
	public class getstudent_det extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try
			{
				urlParameters =  "Department=" + URLEncoder.encode(sd, "UTF-8") + "&&"
                        + "Semester=" + URLEncoder.encode(sem, "UTF-8");
				
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			hs=Connectivity.excutePost(Constants.STUDENTDET_URL, urlParameters);
			return hs;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(hs.contains("success"))
			{
				parsingmethod();
			}
			else {
				Toast.makeText(getApplicationContext(), hs, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	public void parsingmethod()
	{
		try
		{
			JSONObject jobject=new JSONObject(hs);
			JSONObject jobject1=jobject.getJSONObject("Event");
			JSONArray ja=jobject1.getJSONArray("Details");
			int length=ja.length();
			List<String> lables = new ArrayList<String>();
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				snam=data1.getString("Stud_Name");
				
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Stud_Name", snam);
	            oslist.add(map);
	            
	            lables.add(oslist.get(i).get("Stud_Name"));
	            
	            
	            s3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables);
			    s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    sps.setAdapter(s3);
			    sps.setOnItemSelectedListener(new OnItemSelectedListener()
		        {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						ss=arg0.getItemAtPosition(arg2).toString();
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub	
					}
		        	
		        });
	           
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}
	
	
}
