package srt.inz.ams;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.annotation.SuppressLint;
import android.app.Activity;
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
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
@SuppressLint({ "WorldReadableFiles", "ResourceAsColor" }) public class Mytestclass extends Activity{
		
		Spinner sp_date;
		ArrayAdapter<String> s1; String sdate,hs,hs1,snam;
		Button bview;
		
		String shsun; List<String> lables = new ArrayList<String>();
		
		//for mapping value from database
  		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
  		
  	//for getting listview
  		ListView listview;
  		  
  		  String response,name2,value1;   String hr1,hr2,hr3,hr4,hr5,hr6,hr7;
  		  
  		ArrayAdapter<String> adapter;
  		  ArrayList<HashMap<String, String>> oslist2 = new ArrayList<HashMap<String, String>>();
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.st_home);
		sp_date=(Spinner)findViewById(R.id.spinner_date);
		bview=(Button)findViewById(R.id.button_viewatndnce);
		listview=(ListView)findViewById(R.id.listView_attendance);
				
		SharedPreferences sh=getSharedPreferences("keysun", MODE_WORLD_READABLE);
        shsun=sh.getString("srtsun", "");
        
        new getstudent_det2().execute();
        
        
    bview.setOnClickListener(new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			
			new attendanceinfo().execute();
			
		}
	});
	    
        
	}

	public class getattendance_date extends AsyncTask<String, String, String>
	{

	
		@SuppressWarnings("deprecation")
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
				
			}
		//	Toast.makeText(getApplicationContext(), ""+snam, Toast.LENGTH_SHORT).show();
			
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
			 List<String> lables = new ArrayList<String>();
			  for(int i=0;i<length;i++)
			  {
				  JSONObject c=ja.getJSONObject(i);
				  
				 hr1=c.getString("1st_hour");	
				 hr2=c.getString("2nd_hour");
				 hr3=c.getString("3rd_hour");	
				 hr4=c.getString("4th_hour");
				 hr5=c.getString("5th_hour");	
				 hr6=c.getString("6th_hour");
				 hr7=c.getString("7th_hour");	
				  
				  //adding value hashmap key to value
				  HashMap<String, String> map=new HashMap<String, String>();
				  
				  map.put("1st_hour", hr1);				  
				  map.put("2nd_hour", hr2);
				  map.put("3rd_hour", hr3);				  
				  map.put("4th_hour", hr4);
				  map.put("5th_hour", hr5);
				  map.put("6th_hour", hr6);				  
				  map.put("7th_hour", hr7);

				  String ml[]={hr1,hr2,hr3,hr4,hr5,hr6,hr7};
				  
				  for(int j=0;j<7;j++)
				  {
					  lables.add(ml[j]);
					  
				  }
				  //map.put("abc", lables);		  
				  
				  oslist2.add(map);
				  
				  
				  adapter = new ArrayAdapter<String>(this,
		                    android.R.layout.simple_list_item_1, lables);	
		            
		          //  listview.setAdapter(adapter);
		            		            
		            ListAdapter madapter=new SimpleAdapter(getApplicationContext(), oslist2, 
							  R.layout.listite_single,
							  new String[] {"1st_hour","2nd_hour"}, new int[] {R.id.textView_single2});
		            
		            listview.setAdapter(madapter);
		            
		            listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			               @Override
			               public void onItemClick(AdapterView<?> parent, View view,
			                                            int position, long id) {  
			            	   
			            		   Toast.makeText(getApplicationContext(), "clicked...."+((TextView) view).getText() ,Toast.LENGTH_SHORT).show();
			            		//   ((TextView) view).setBackgroundColor(android.R.color.holo_orange_light);
			               }
			                });			  
				}	
			  
			  
			  
			 }
		  catch(JSONException e)
		  {
			  e.printStackTrace();

		  }
	}
	
	
}



