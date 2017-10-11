package srt.inz.ams;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

public class VAStudent extends Activity{
	
	ListView mlist; ListAdapter adapter; String hs,resdbup;
	ArrayList<HashMap<String, String>> oslist=new ArrayList<HashMap<String,String>>();
	
	String sRoll_No,sStud_Name,sDepartment,sSemester,sYear,sParent_Name,sAddress,sContact_Number,sEmail,sstatus,smsg;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vastud);
		mlist=(ListView)findViewById(R.id.mliststudents);
		
		new StudentlistApi().execute();
	}
	
	protected class StudentlistApi extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
				String urlParameters = null;
	    		try
				{	
					
	    			 urlParameters ="" ;
	    			 
				}
				
				catch(Exception e)
				{
					System.out.println("Error:"+e);
				}
				
	    		hs=Connectivity.excutePost(Constants.STUDENTDETList_URL,
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
				oslist.add(null);
				mlist.setAdapter(null);
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
			oslist.add(null);
			oslist.clear();
			mlist.setAdapter(null);
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				String Roll_No=data1.getString("Roll_No");
				String Stud_Name=data1.getString("Stud_Name");
				String Department=data1.getString("Department");
				String Semester=data1.getString("Semester");
				String Year=data1.getString("Year");
				String Parent_Name=data1.getString("Parent_Name");
				String Address=data1.getString("Address");
				String Contact_Number=data1.getString("Contact_Number");
				String Email=data1.getString("Email");
				String status=data1.getString("status");
				
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Roll_No", Roll_No);
	            map.put("Stud_Name", Stud_Name);
	            map.put("Department", Department);
	            map.put("Semester", Semester);
	            map.put("Year", Year);
	            map.put("Parent_Name", Parent_Name);
	            map.put("Address", Address);
	            map.put("Contact_Number", Contact_Number);
	            map.put("Email", Email);
	            map.put("status", status);
	            
	            map.put("myview", "Name : "+Stud_Name+"\n Roll Number : "+Roll_No
	            		+"\n Department : "+Department+"\n Email : "+Email+"\n Contact No : "
	            		+Contact_Number+"\n Parent_Name : "+Parent_Name);
	            
	            	oslist.add(map);
	            
	            	 adapter = new SimpleAdapter(getApplicationContext(), oslist,
	     	                R.layout.layout_single,
	     	                new String[] {"myview"}, new int[] {R.id.mtext_single});
	     	            mlist.setAdapter(adapter);
	     	            
	     	            mlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
	     	               
	     				@Override
	     	               public void onItemClick(AdapterView<?> parent, View view,
	     	                                            int position, long id) {               
	     	              
	     	              smsg =oslist.get(+position).get("myview").toString();
	     	              sRoll_No=oslist.get(+position).get("Roll_No").toString();
	     	              sStud_Name=oslist.get(+position).get("Stud_Name").toString();
	     	              sDepartment=oslist.get(+position).get("Department").toString();
	     	              sSemester=oslist.get(+position).get("Semester").toString();
	     	              sYear=oslist.get(+position).get("Year").toString();
	     	              sParent_Name=oslist.get(+position).get("Parent_Name").toString();
	     	              
	     	              sAddress=oslist.get(+position).get("Address").toString();
	     	              sEmail=oslist.get(+position).get("Email").toString();
	     	              sContact_Number=oslist.get(+position).get("Contact_Number").toString();
	     	              sstatus=oslist.get(+position).get("status").toString();
	     	              openDialog();
	     	               
	     	               }
	     	                });
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}

	
	
	public void openDialog(){
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
			   
	    	 alertDialogBuilder.setTitle("Please choose an action!");
		      alertDialogBuilder.setMessage(smsg);
	    	 alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {

	        	/* Intent i=new Intent(getApplicationContext(),RequestTransport.class);
	        	 startActivity(i);*/
	            
	         }
	      });
	    	 
	    	 if(sstatus.equals("0"))
	    		 
	    	 {
	      alertDialogBuilder.setNegativeButton("Approve Student",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	
	        //	 Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
	        new UpdateStudentApiTask().execute();
	        	 //finish();UpdateStudentApiTask
	         }
	      });
	    	 }
	     
	     
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
    	 
	}  
	
	public class UpdateStudentApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters=null;
			try {
				urlParameters= "Roll_No=" +URLEncoder.encode(sRoll_No,"UTF-8");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resdbup = Connectivity.excutePost(Constants.STUDENTAPPROVE_URL,
                    urlParameters);
			Log.e("AdminStudentApproval", resdbup);
			return resdbup;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), ""+resdbup, Toast.LENGTH_SHORT).show();
			oslist.clear();
			oslist.add(null);
			new StudentlistApi().execute();
		}

}

}