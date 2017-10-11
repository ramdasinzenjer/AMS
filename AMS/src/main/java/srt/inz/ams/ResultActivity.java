package srt.inz.ams;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
 
@SuppressWarnings("deprecation")
public class ResultActivity extends Activity {
	
	TextView tv_date;	String s_date,hs,snam,snam1,hs1;
	
	String shdp,shsm,shpd,shsun; Button bsub,bday; String[] resultArr,allstudents; String month = "wrong";
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        tv_date=(TextView)findViewById(R.id.textView_date);
        bsub=(Button)findViewById(R.id.button_attndance);
        bday=(Button)findViewById(R.id.bun_daily);
        
        SharedPreferences share=getSharedPreferences("shids", MODE_WORLD_READABLE);
        shdp =  share.getString("shdpt", "");
        shsm = share.getString("shsem", "");
        shpd = share.getString("shprd", "");
        
        SharedPreferences sh=getSharedPreferences("keysun", MODE_WORLD_READABLE);
        shsun=sh.getString("srtsun", "");
        
      //  DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        DateFormat df2 = new SimpleDateFormat("MM");
		Calendar calobj = Calendar.getInstance();
		s_date=df.format(calobj.getTime());
		String mon=df2.format(calobj.getTime());
		int mn=Integer.valueOf(mon)-1;
	//	Toast.makeText(getApplicationContext(), ""+mon, Toast.LENGTH_SHORT).show();
		getMonthForInt(mn);
		
        tv_date.setText("Date : "+s_date+" Month : "+month);
        
        
		
        Bundle b = getIntent().getExtras();
         resultArr = b.getStringArray("selectedItems");
         allstudents= b.getStringArray("all_item");
         
        ListView lv = (ListView) findViewById(R.id.outputList);
 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, resultArr);
        lv.setAdapter(adapter);
        
        bday.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 Log.e("Length of allstudents ",""+allstudents.length);
			        for(int i=0;i<allstudents.length;i++)
			        {
			        	snam1=allstudents[i];			        	
			        	   
			        	   new upattendance().execute(snam1);
			   //    	Toast.makeText(getApplicationContext(), ""+allstudents[i], Toast.LENGTH_SHORT).show();
			        	
			        }
			        
			Log.e("Message",""+snam);
				
			}
		});
        
        bsub.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				 Log.e("Length of resultArr ",""+resultArr.length);
			        for(int i=0;i<resultArr.length;i++)
			        {
			        	
			        	snam=resultArr[i];			        	
			        	   new getstudent_det().execute(snam);
			        //	Toast.makeText(getApplicationContext(), ""+resultArr[i], Toast.LENGTH_SHORT).show();
	
			        	   /*Toast.makeText(getApplicationContext(), ""+shpd, Toast.LENGTH_SHORT).show();   
			        	   if(shpd.equals("5th_hour"))
							{
								new upattendance().execute(snam);
							}
							else
							{
								Toast.makeText(getApplicationContext(), "Not last hour", Toast.LENGTH_SHORT).show();
							}*/
						
			        	
			        }
			        
			Log.e("Message",""+snam);
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
				String s = params[0];
				
				urlParameters =  "Stud_Name=" + URLEncoder.encode(s, "UTF-8") + "&&"
                        + "Date=" + URLEncoder.encode(s_date, "UTF-8") + "&&"
                        + "Department=" + URLEncoder.encode(shdp, "UTF-8")+ "&&"
                        + "Period=" + URLEncoder.encode(shpd, "UTF-8")+ "&&"
                        + "Staff_username=" + URLEncoder.encode(shsun, "UTF-8")+ "&&"
                        +"month=" + URLEncoder.encode(month, "UTF-8");
				
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			hs=Connectivity.excutePost(Constants.ATTENDANCEUPDATE_URL,
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
				Toast.makeText(getApplicationContext(), "Updating attendance ...", Toast.LENGTH_SHORT).show();
			}
			else {
				Toast.makeText(getApplicationContext(), hs, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
    String getMonthForInt(int num) {
        
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11 ) {
            month = months[num];
        }
        return month;
    }
    public class upattendance extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			String s = params[0];
			try
			{
				urlParameters =  "Stud_Name=" + URLEncoder.encode(s, "UTF-8") + "&&"
                        + "Date=" + URLEncoder.encode(s_date, "UTF-8");		
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			hs1=Connectivity.excutePost(Constants.EXAMPLE_URL, urlParameters);
			return hs1;
		}
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(hs1.contains("success"))
			{
			/*	Toast.makeText(getApplicationContext(), "Updating attendance_daily"+hs1, Toast.LENGTH_SHORT).show();
			}
			else {*/
				Toast.makeText(getApplicationContext(), hs1, Toast.LENGTH_SHORT).show();
			}
		}
		
	}
    
}
