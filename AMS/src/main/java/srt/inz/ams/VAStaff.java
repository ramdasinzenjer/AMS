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

public class VAStaff extends Activity{
	
	ListView mlist; ListAdapter adapter; String hs,resdbup;
	ArrayList<HashMap<String, String>> oslist=new ArrayList<HashMap<String,String>>();
	
	String sstaff_userid,sstaff_name,sdepartment,scontact_no,semail,sstatus,smsg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.vastaff);
		mlist=(ListView)findViewById(R.id.mliststaff);
				
		new StafflistApi().execute();
	}
	
	protected class StafflistApi extends AsyncTask<String, String, String>
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
				
	    		hs=Connectivity.excutePost(Constants.STAFFDET_URL,
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
				String staff_userid=data1.getString("staff_userid");
				String staff_name=data1.getString("staff_name");
				String department=data1.getString("department");
				String contact_no=data1.getString("contact_no");
				String email=data1.getString("email");
				String status=data1.getString("status");
				
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("staff_userid", staff_userid);
	            map.put("staff_name", staff_name);
	            map.put("department", department);
	            map.put("contact_no", contact_no);
	            map.put("email", email);
	            map.put("status", status);
	            
	            map.put("myview", "Name : "+staff_name+"\n Staff Id : "+staff_userid+"\n Email : "+email
	            		+"\n Department : "+department+"\n Contact No : "+contact_no);
	            
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
	     	              sstaff_userid=oslist.get(+position).get("staff_userid").toString();
	     	              sstaff_name=oslist.get(+position).get("staff_name").toString();
	     	              sdepartment=oslist.get(+position).get("department").toString();
	     	              scontact_no=oslist.get(+position).get("contact_no").toString();
	     	              semail=oslist.get(+position).get("email").toString();
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
	      alertDialogBuilder.setNegativeButton("Approve Staff",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	
	        //	 Toast.makeText(getApplicationContext(),"Back",Toast.LENGTH_SHORT).show();
	        new UpdateStaffApiTask().execute();
	        	 //finish();
	         }
	      });
	    	 }
	     
	     
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
    	 
	}  
	
	public class UpdateStaffApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			
			String urlParameters=null;
			try {
				urlParameters= "staff_userid=" +URLEncoder.encode(sstaff_userid,"UTF-8");
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resdbup = Connectivity.excutePost(Constants.STAFFAPPROVE_URL,
                    urlParameters);
			Log.e("AdminStaffApproval", resdbup);
			return resdbup;
			
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), ""+resdbup, Toast.LENGTH_SHORT).show();
			oslist.clear();
			oslist.add(null);
			new StafflistApi().execute();
		}

}
	
}
