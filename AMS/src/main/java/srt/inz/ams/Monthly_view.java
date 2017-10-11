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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Monthly_view extends Activity{
	
	String sh_sn,sh_dp,sh_sm,sh_mn,hs,spah,hos;
	ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
	
	List<String> lablespr = new ArrayList<String>();
	List<String> lablesab = new ArrayList<String>();
	List<String> lableshf = new ArrayList<String>();
	
	TextView tn,twd,tfp,thp,ta,tap; Button btsave;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.monthlyview);
		
		tn=(TextView)findViewById(R.id.tstname);
		twd=(TextView)findViewById(R.id.tnworking);
		tfp=(TextView)findViewById(R.id.tnpdays);
		thp=(TextView)findViewById(R.id.tnhdays);
		ta=(TextView)findViewById(R.id.tnadays);
		tap=(TextView)findViewById(R.id.tattper);
		btsave=(Button)findViewById(R.id.btsavetext);
		
		btsave.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new monthly_atsave().execute();
				
			}
		});
		
		SharedPreferences share = getSharedPreferences("mainkey", MODE_WORLD_READABLE);
		sh_sn=share.getString("student", "");
		sh_dp=share.getString("department", "");
		sh_sm=share.getString("sem", "");
		sh_mn=share.getString("month", "");
		
		Toast.makeText(getApplicationContext(), sh_mn, Toast.LENGTH_SHORT).show();
		new monthly_at().execute();
		
	}

	public class monthly_at extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "Department=" + URLEncoder.encode(sh_dp, "UTF-8") + "&&"
	                        + "Stud_Name=" + URLEncoder.encode(sh_sn, "UTF-8") + "&&"
	                        + "month=" + URLEncoder.encode(sh_mn, "UTF-8");
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
    		hs=Connectivity.excutePost(Constants.MONTHLYATTENDANCE_URL,
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
			
			int p=0,a=0,h=0;
			
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				spah=data1.getString("pah");
				
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("pah", spah);
	            oslist.add(map);
	            if(spah.equals("p"))
	            {
	            lablespr.add(oslist.get(i).get("pah"));
	            p=p+1;
	            }
	            else if(spah.equals("a"))
	            {
	            	lablesab.add(oslist.get(i).get("pah"));
	            	a=a+1;
	            }
	            else if(spah.equals("h"))
	            {
	            	lableshf.add(oslist.get(i).get("pah"));
	            	h=h+1;
	            }
	           
			}
			Toast.makeText(getApplicationContext(), ""+p+"/"+h+"/"+a, Toast.LENGTH_SHORT).show();		
			
			double totalworking=a+p+h; double x;
			if(h%2==1)
			{
				x=0.5;
			}
			else {
				x=0;
			}
			
			double att=(p+(h/2)+x)/totalworking;
			double attper=att*100;
			
			tn.setText(sh_sn);
			twd.setText("Total working days : "+totalworking);
			tfp.setText("Full day present : "+p);
			thp.setText("Half day present : "+h);
			ta.setText("Absent days : "+a);
			tap.setText("Monthly attendance percentage : "+attper+" %.");
			Toast.makeText(getApplicationContext(), "Attendance percentage :"+attper+"% .", Toast.LENGTH_SHORT).show();
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}
	
	
	
	public class monthly_atsave extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "Department=" + URLEncoder.encode(sh_dp, "UTF-8") + "&&"
	                        + "Stud_Name=" + URLEncoder.encode(sh_sn, "UTF-8") + "&&"
	                        + "month=" + URLEncoder.encode(sh_mn, "UTF-8");
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
    		hos=Connectivity.excutePost(Constants.MONTHLYATTENDANCESAVE_URL,
                    urlParameters);
    		Uri uri = Uri.parse(Constants.MONTHLYATTENDANCESAVE_URL+urlParameters); // missing 'http://' will cause crashed
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
            Log.e("You are at", "" + hos);
    		
			return hos;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(hs.contains("success"))
			{
				Toast.makeText(getApplicationContext(), hos, Toast.LENGTH_SHORT).show();			}
			else {
				Toast.makeText(getApplicationContext(), hos, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
	
	
}
