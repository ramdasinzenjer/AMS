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
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class TA_Attendancevw extends Activity{
	
	int p_count;
	
	List<String> lables = new ArrayList<String>();
	//for mapping value from database
		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
		
	//for getting listview
		ListView listview;
		ArrayAdapter<String> adapter; 
		  String response,name2,value1;   String hr1,hr2,hr3,hr4,hr5,hr6,hr7;
		  
		  TextView tp,ta,tn; String shnam,sdate; String sel_hr,sh_usr,mresp,satt;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ta_attndance);
		tn=(TextView)findViewById(R.id.textView_tv123);
		ta=(TextView)findViewById(R.id.textView_absentabc);
		tp=(TextView)findViewById(R.id.textView_presentabc);
		listview=(ListView)findViewById(R.id.listView_attendanceabc);
		
		SharedPreferences share=getSharedPreferences("shids2", MODE_WORLD_READABLE);
		shnam = share.getString("shstd", "");
		sdate = share.getString("shdate", "");
		
		SharedPreferences sh=getSharedPreferences("keysun", MODE_WORLD_READABLE);
		sh_usr = sh.getString("srtsun", "");
		
		Toast.makeText(getApplicationContext(), ""+shnam, Toast.LENGTH_SHORT).show();
		
		
		tn.setText(shnam);
		new attendanceinfo().execute();
	}
	public class attendanceinfo extends AsyncTask<String, String, String>
	{


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try {
				
				urlParameters =  "Date=" + URLEncoder.encode(sdate, "UTF-8") + "&&"
                        + "Stud_Name=" + URLEncoder.encode(shnam, "UTF-8");
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
			response=Connectivity.excutePost(Constants.STUDENTATTENDANCE_URL, urlParameters);
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
				 lables.clear();
				 oslist.add(null);
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
			 lables.clear();
			 oslist.add(null);
			 p_count=0;
			  for(int i=0;i<length;i++)
			  {
				  JSONObject c=ja.getJSONObject(i);
				  
				 hr1="1st_hour : "+c.getString("1st_hour");	
				 hr2="2nd_hour : "+c.getString("2nd_hour");
				 hr3="3rd_hour : "+c.getString("3rd_hour");	
				 hr4="4th_hour : "+c.getString("4th_hour");
				 hr5="5th_hour : "+c.getString("5th_hour");	
				/* hr6="6th_hour : "+c.getString("6th_hour");
				 hr7="7th_hour : "+c.getString("7th_hour");	*/
				 
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
				 /* map.put("6th_hour", hr6);				  
				  map.put("7th_hour", hr7);*/

				  String ml[]={hr1,hr2,hr3,hr4,hr5}; /*,hr6,hr7};*/
				  
				  for(int j=0;j<5;j++)
				  {
					  lables.add(ml[j]);
					  
				  }
				//  map.put("abc", this.lables2.toString());		  
				  
				  oslist.add(map);
				  
				  
				  adapter = new ArrayAdapter<String>(this,
		                    android.R.layout.simple_list_item_1, lables);	
		            
		            listview.setAdapter(adapter);
		            		            
		            /*ListAdapter madapter=new SimpleAdapter(getApplicationContext(), oslist2, 
							  R.layout.listite_single,
							  new String[] {"abc"}, new int[] {R.id.textView_single2});
		            
		          listview.setAdapter(madapter);*/
		            listview.setVisibility(View.VISIBLE);
		            
		 listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			      @Override
			      public void onItemClick(AdapterView<?> parent, View view,
			                                            int position, long id) {  
			    	  
			    	  String stext=((TextView) view).getText().toString();
			    	  
			    	 String[] st= stext.split(":");
			    	 sel_hr=st[0].trim();
			            	   
			    Toast.makeText(getApplicationContext(), sel_hr,
			    		Toast.LENGTH_SHORT).show();
			            		openDialog();
			               }
			     });			  
		            
				}	
			  		  
			 }
		  catch(JSONException e)
		  {
			  e.printStackTrace();

		  }
	}
	
	
	public void openDialog(){
	      
		
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      
	     		  
	    	 alertDialogBuilder.setTitle("Please choose an action!");
		      alertDialogBuilder.setMessage("Would you like to change attendance for "+shnam);
	    
		 alertDialogBuilder.setPositiveButton("Present", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            
	        	 satt="p";
	        	 new UpdateAttendance().execute();
	        	 // Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show(); 
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Absent",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	
	        //	 Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
	        	 satt="a";
	        	 new UpdateAttendance().execute();
	         }
	      });
	      alertDialogBuilder.setNeutralButton("Cancel",new DialogInterface.OnClickListener() {
		         @Override
		         public void onClick(DialogInterface dialog, int which) {
		        	
		        //	 Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
		     
		         }
		      });
	    
	     
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
  	 
	}  
	public class UpdateAttendance extends AsyncTask<String, String, String>
	{


		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try {
				
				urlParameters =  "Date=" + URLEncoder.encode(sdate, "UTF-8") + "&&"
                        + "Stud_Name=" + URLEncoder.encode(shnam, "UTF-8")+ "&&"
                        + "Period=" + URLEncoder.encode(sel_hr, "UTF-8")+"&&"
                        + "Attendance=" + URLEncoder.encode(satt, "UTF-8")+"&&"
                        + "Staff=" + URLEncoder.encode(sh_usr, "UTF-8");
			
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Error:"+e);
			}
			mresp=Connectivity.excutePost(Constants.STUDENTATTENDANCE_SINGLE_URL, urlParameters);
			return mresp;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mresp.contains("success"))
			{
				Toast.makeText(getApplicationContext(), ""+mresp, Toast.LENGTH_SHORT).show();	
			}
			else {
				Toast.makeText(getApplicationContext(), ""+mresp, Toast.LENGTH_SHORT).show();
			}		
		}	
	}
	
}
