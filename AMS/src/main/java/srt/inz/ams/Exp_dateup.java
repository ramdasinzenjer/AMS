package srt.inz.ams;

import java.net.URLEncoder;

import org.json.JSONArray;
import org.json.JSONObject;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class Exp_dateup extends Activity{
	
	Spinner sp_sem; ArrayAdapter<String > s_adapter; String sem,sdt,resp,hs;
	EditText etdate;
	Button btup;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.exp_dateadd);
		
		sp_sem=(Spinner)findViewById(R.id.spinn_exdatesem);
		etdate=(EditText)findViewById(R.id.edit_expdate);
		btup=(Button)findViewById(R.id.bexpup);
		
		 String[] typ = getResources().getStringArray(R.array.semester);
	        
	        s_adapter=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typ);
	        s_adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    sp_sem.setAdapter(s_adapter);
		    sp_sem.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					sem=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
					new DateVewApi().execute();
					
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    
		    btup.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					sdt=etdate.getText().toString();
					
					new UpdateDateApiTask().execute();
				}
			});
	}

	
	
	
	protected class UpdateDateApiTask extends AsyncTask<String, String, String>
	{

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlPararmeters=null;
			try {
				
				urlPararmeters="date="+URLEncoder.encode(sdt,"UTF-8")+"&&"
						+"sem="+URLEncoder.encode(sem,"UTF-8");
				
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			resp=Connectivity.excutePost(Constants.UPDATE_EXPDATE_URL, urlPararmeters);
			return resp;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			Toast.makeText(getApplicationContext(), ""+resp, Toast.LENGTH_SHORT).show();
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
				
	    		hs=Connectivity.excutePost(Constants.VIEW_EXPDATE_URL,
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
				Toast.makeText(getApplicationContext(), "No date Updated", Toast.LENGTH_SHORT).show();
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
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				String date=data1.getString("date");
				
				etdate.setText(date);
				
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}
	
}
