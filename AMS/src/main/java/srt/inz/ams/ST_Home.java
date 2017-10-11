package srt.inz.ams;

import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@SuppressLint({ "WorldReadableFiles", "ResourceAsColor" }) public class ST_Home extends Activity{
		
		Spinner sp_date;
		ArrayAdapter<String> s1; String sdate,hs,hs1,snam,sem,enddate,resp;
		Button bview;
		int p_count;
		String shsun; 
		List<String> lables = new ArrayList<String>(); 
		List<String> lables2 = new ArrayList<String>();
		
		Date date1,date2;
		
		//for mapping value from database
  		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
  		
  	//for getting listview
  		ListView listview;
  		  
  		  String response,name2,value1;   String hr1,hr2,hr3,hr4,hr5,hr6,hr7;
  		  
  		ArrayAdapter<String> adapter;
  		  ArrayList<HashMap<String, String>> oslist2 = new ArrayList<HashMap<String, String>>();
	
  		  
  		  TextView tp,ta;

  		  String currentdate;		  
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.st_home);
		sp_date=(Spinner)findViewById(R.id.spinner_date);
		bview=(Button)findViewById(R.id.button_viewatndnce);
		listview=(ListView)findViewById(R.id.listView_attendance);
		ta=(TextView)findViewById(R.id.textView_absent);
		tp=(TextView)findViewById(R.id.textView_present);
		
		listview.setVisibility(View.INVISIBLE);
				
		SharedPreferences sh=getSharedPreferences("keysun", MODE_WORLD_READABLE);
        shsun=sh.getString("srtsun", "");
        
        new getstudent_det2().execute();
        
        
        
        
    bview.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			 
			new DateVewApi().execute();
			new attendanceinfo().execute();
			
		}
	});
	    
        
	}

	public class getattendance_date extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
				String urlParameters = null;
	    		try
				{	
					
	    			 urlParameters =  "Stud_Name=" + URLEncoder.encode(snam, "UTF-8") ;
	    			 
				}
				
				catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				
	    		hs=Connectivity.excutePost(Constants.GETDATE_URL,
	                    urlParameters);
	            Log.e("You are at", "" + hs);
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
			/*oslist.add(null);
			sp_date.setAdapter(null);*/
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				sdate=data1.getString("Date");
				
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Date", sdate);
	            oslist.add(map);
	            
	            lables.add(oslist.get(i).get("Date"));
	            
	            s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,lables);
		        s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
			    sp_date.setAdapter(s1);
	            
			    sp_date.setOnItemSelectedListener(new OnItemSelectedListener()
		        {

			    	
					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						sdate=arg0.getItemAtPosition(arg2).toString();
						((TextView) arg0.getChildAt(0)).setTextColor(Color.WHITE);
						
						Toast.makeText(getApplicationContext(), ""+sdate, Toast.LENGTH_SHORT).show();
						
						
					
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
	
	
	
	public class getstudent_det2 extends AsyncTask<String, String, String>
	{

	

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "Roll_No=" + URLEncoder.encode(shsun, "UTF-8") ;
		
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
    		hs1=Connectivity.excutePost(Constants.STUDENTDATE2_URL,
                    urlParameters);
            Log.e("You are at", "" + hs1);
            
			return hs1;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(hs1.contains("success"))
			{
				parsingmethod2();
				// Toast.makeText(getApplicationContext(), hs1, Toast.LENGTH_SHORT).show();
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
			JSONObject abjobject=new JSONObject(hs1);
			JSONObject abjobject1=abjobject.getJSONObject("Event");
			JSONArray abja=abjobject1.getJSONArray("Details");
			int length=abja.length();
			for(int i=0;i<length;i++)
			{
				JSONObject adata1=abja.getJSONObject(i);
				snam=adata1.getString("Stud_Name");
				sem=adata1.getString("Semester");
				
			}
			Toast.makeText(getApplicationContext(), ""+sem, Toast.LENGTH_SHORT).show();
			
			new getattendance_date().execute();
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}
	
	
	public class attendanceinfo extends AsyncTask<String, String, String>
	{


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "Date=" + URLEncoder.encode(sdate, "UTF-8") +"&&"
    					 +"Stud_Name=" + URLEncoder.encode(snam, "UTF-8");
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
			
			response=Connectivity.excutePost(Constants.STUDENTATTENDANCE_URL,
                    urlParameters);
            Log.e("You are at", "" + response);
			return response;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (response.contains("success"))
			{
				parsingmethod3();
			}
			else {
				Toast.makeText(getApplicationContext(), "No status available..", Toast.LENGTH_SHORT).show();
				listview.setAdapter(null);
				 lables2.clear();
				 oslist2.add(null);
				 listview.setVisibility(View.INVISIBLE);
			}		
		}	
	}
	
	public void parsingmethod3(){
		  try
		  {
			  JSONObject job=new JSONObject(response);
			  JSONObject job1=job.getJSONObject("Event");
			  JSONArray ja=job1.getJSONArray("Details");
			 int length=ja.length();
		//	 lables2.add(null);
			 listview.setAdapter(null);
			 lables2.clear();
			 oslist2.add(null);
			 p_count=0;
			  for(int i=0;i<length;i++)
			  {
				  JSONObject c=ja.getJSONObject(i);
				  
				 hr1="1st_hour : "+c.getString("1st_hour");	
				 hr2="2nd_hour : "+c.getString("2nd_hour");
				 hr3="3rd_hour : "+c.getString("3rd_hour");	
				 hr4="4th_hour : "+c.getString("4th_hour");
				 hr5="5th_hour : "+c.getString("5th_hour");	
				 /*hr6="6th_hour : "+c.getString("6th_hour");
				 hr7="7th_hour : "+c.getString("7th_hour");*/	
				 
				 if(hr1.contains("p"))
				 {
					 hr1="1st_hour : Present";
					 p_count++;
				 }
				 else if (hr1.contains("")) {
					 hr1="1st_hour : Absent";
				}
				 if(hr2.contains("p"))
				 {
					 hr2="2nd_hour : Present";
					 p_count++;
				 }
				 else if (hr2.contains("")) {
					 hr2="2nd_hour : Absent";
				}
				 if(hr3.contains("p"))
				 {
					 hr3="3rd_hour : Present";
					 p_count++;
				 }
				 else if (hr3.contains("")) {
					 hr3="3rd_hour : Absent";
				}
				 if(hr4.contains("p"))
				 {
					 hr4="4th_hour : Present";
					 p_count++;
				 }
				 else if (hr4.contains("")) {
					 hr4="4th_hour : Absent";
				}
				 if(hr5.contains("p"))
				 {
					 hr5="5th_hour : Present";
					 p_count++;
				 }
				 else if (hr5.contains("")) {
					 hr5="5th_hour : Absent";
				}
				 /*if(hr6.contains("p"))
				 {
					 hr6="6th_hour : Present";
					 p_count++;
				 }
				 else if (hr6.contains("")) {
					 hr6="6th_hour : Absent";
				}
				 if(hr7.contains("p"))
				 {
					 hr7="7th_hour : Present";
					 p_count++;
				 }
				 else if (hr7.contains("")) {
					 hr7="7th_hour : Absent";
				}*/
				 
				 tp.setText("Present \nHrs: "+p_count);
				 ta.setText("Absent \nHrs: "+(5-p_count));
				 if((5-p_count)>3)
				 {
					 ta.setTextColor(Color.parseColor("#FF0000"));
				 }
				 else {
					 ta.setTextColor(Color.parseColor("#FFFFFF"));
				}
				  Toast.makeText(getApplicationContext(), "Total Present Hours : "+p_count, Toast.LENGTH_SHORT).show();
				  //adding value hashmap key to value
				  HashMap<String, String> map=new HashMap<String, String>();
				  
				  map.put("1st_hour", hr1);				  
				  map.put("2nd_hour", hr2);
				  map.put("3rd_hour", hr3);				  
				  map.put("4th_hour", hr4);
				  map.put("5th_hour", hr5);
				  /*map.put("6th_hour", hr6);				  
				  map.put("7th_hour", hr7);*/

				  String ml[]={hr1,hr2,hr3,hr4,hr5};
				  
				  for(int j=0;j<5;j++)
				  {
					  lables2.add(ml[j]);
					  
				  }
				//  map.put("abc", this.lables2.toString());		  
				  
				  oslist2.add(map);
				  
				  
				  adapter = new ArrayAdapter<String>(this,
		                    android.R.layout.simple_list_item_1, lables2);	
		            
		            listview.setAdapter(adapter);
		            
		            
		            
		          //  listview.setVisibility(View.VISIBLE);
		            
		 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			      @Override
			      public void onItemClick(AdapterView<?> parent, View view,
			                                            int position, long id) {  
			            	   
			    Toast.makeText(getApplicationContext(), ""+((TextView) view).getText() ,
			    		Toast.LENGTH_SHORT).show();
			            		
			               }
			     });			  
		            
				}	
			  		  
			 }
		  catch(JSONException e)
		  {
			  e.printStackTrace();

		  }
	}
	
	
	protected class DateVewApi extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
				String urlParameters = null;
	    		try
				{	
					
	    			 urlParameters ="sem="+URLEncoder.encode(sem,"UTF-8") ;
	    			 
				}
				
				catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				
	    		resp=Connectivity.excutePost(Constants.VIEW_EXPDATE_URL,
	                    urlParameters);
	            Log.e("You are at", "" + resp);
			return resp;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(resp.contains("success"))
			{
				parsingmethodt();
			}
			else {
				Toast.makeText(getApplicationContext(), "No date Updated", Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	public void parsingmethodt()
	{
		try
		{
			JSONObject jobject=new JSONObject(resp);
			JSONObject jobject1=jobject.getJSONObject("Event");
			JSONArray ja=jobject1.getJSONArray("Details");
			int length=ja.length();
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				 enddate=data1.getString("date");
				
				Toast.makeText(getApplicationContext(), "End date : "+enddate,  Toast.LENGTH_SHORT).show();							
				
			}
			
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Calendar calobj = Calendar.getInstance();
			currentdate=df.format(calobj.getTime());
			
			SimpleDateFormat mydateformat = new SimpleDateFormat("yyyy-MM-dd");

             date1 = mydateformat.parse(currentdate); //24-05-16
             date2 = mydateformat.parse(enddate); //25-07-16

            if (date2.after(date1)) {
               
                
              //  Toast.makeText(getApplicationContext(), date1+"----"+date2,  Toast.LENGTH_LONG).show();
                listview.setVisibility(View.VISIBLE);
                tp.setVisibility(View.VISIBLE);
				 ta.setVisibility(View.VISIBLE);
                Log.e("Copmare", date1+"----"+date2);
            }
            else
            {
            	listview.setVisibility(View.INVISIBLE);
            	tp.setVisibility(View.INVISIBLE);
				 ta.setVisibility(View.INVISIBLE);
            	Toast.makeText(getApplicationContext(), "Service Unavailable, Contact Admin",  Toast.LENGTH_SHORT).show();
            }

		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}
	
	public void logOutfunction(View view)
	{
		Intent loginscreen=new Intent(this,Main_page.class);
		loginscreen.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(loginscreen);
		this.finish();
	}
	
}
