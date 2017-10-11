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
import android.graphics.Color;
import android.os.AsyncTask;
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

public class TA_View extends Activity{
	
	Spinner rq_dpt,rq_sem,rq_std,rq_dat;
	ArrayAdapter<String> s1,s2,s3,s4; String sdpt,ssem,sstd,sdat,hs,hs1,snam;
	
	Button bvw;
	
	//for mapping value from database
		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
		ArrayList<HashMap<String, String>> oslist1 = new ArrayList<HashMap<String, String>>();
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ta_view);
		
		rq_dpt=(Spinner)findViewById(R.id.spinner_dept0);
		rq_sem=(Spinner)findViewById(R.id.spinner_sem0);
		rq_std=(Spinner)findViewById(R.id.spinner_sestudent);
		rq_dat=(Spinner)findViewById(R.id.spinner_date0);
		bvw=(Button)findViewById(R.id.button_vwatn);
		
		
		
		bvw.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				SharedPreferences share=getSharedPreferences("shids2", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=share.edit();
				ed.putString("shdpt", sdpt);
				ed.putString("shsem", ssem);
				ed.putString("shstd", snam);
				ed.putString("shdate", sdat);
				ed.commit();
				
				Intent i=new Intent(getApplicationContext(),TA_Attendancevw.class);
				startActivity(i);
				
			}
		});
		
		String[] dpt = getResources().getStringArray(R.array.dept);
		 String[] sem = getResources().getStringArray(R.array.semester);
		
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
					new getstudent_det().execute();
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    
		  /*s3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,prd);
		    s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    rq_prd.setAdapter(s3);
		    rq_prd.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sprd=arg0.getItemAtPosition(arg2).toString();
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
	      */
		 
	}

	
	public class getstudent_det extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try
			{
				urlParameters =  "Department=" + URLEncoder.encode(sdpt, "UTF-8") + "&&"
                        + "Semester=" + URLEncoder.encode(ssem, "UTF-8");
				
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
				
				/*semail=data1.getString("cargoagency_email");
				sphone=data1.getString("cargoagency_phone");
				sinsure=data1.getString("insurance");*/
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Stud_Name", snam);
	            oslist.add(map);
	            
	            lables.add(oslist.get(i).get("Stud_Name"));
	            
	            
	            s3=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables);
			    s3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    rq_std.setAdapter(s3);
			    rq_std.setOnItemSelectedListener(new OnItemSelectedListener()
		        {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						snam=arg0.getItemAtPosition(arg2).toString();
					new getattendance_date().execute();
						
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
	
	public class getattendance_date extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try
			{
				urlParameters =  "Stud_Name=" + URLEncoder.encode(snam, "UTF-8");		
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			hs1=Connectivity.excutePost(Constants.GETDATE_URL, urlParameters);
			return hs1;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(hs1.contains("success"))
			{
				parsingmethod2();
			}
			else {
				Toast.makeText(getApplicationContext(), hs1, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	public void parsingmethod2()
	{
		try
		{
			JSONObject jobject=new JSONObject(hs1);
			JSONObject jobject1=jobject.getJSONObject("Event");
			JSONArray ja=jobject1.getJSONArray("Details");
			int length=ja.length();
			/*oslist.add(null);
			sp_date.setAdapter(null);*/
			List<String> lables1 = new ArrayList<String>();
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				sdat=data1.getString("Date");
				
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Date", sdat);
	            oslist1.add(map);
	            
	            lables1.add(oslist1.get(i).get("Date"));
	            
	            s4=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables1);
		        s4.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    rq_dat.setAdapter(s4);
	            
			    rq_dat.setOnItemSelectedListener(new OnItemSelectedListener()
		        {

			    	
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						sdat=arg0.getItemAtPosition(arg2).toString();
						((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
						
						Toast.makeText(getApplicationContext(), ""+sdat, Toast.LENGTH_SHORT).show();
					
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
