package srt.inz.ams;

import java.net.URLEncoder;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;
import android.app.Activity;
import android.content.Intent;
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

public class Staff_reg extends Activity{
	
	EditText etsnam,etscontact,etsmail,etsid,etspas;
	String stnam,stconatact,stmail,stid,stpas;
	Spinner spsdep; String spdep;	ArrayAdapter<String> s1;
	
	Button bstfrg; String sh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.staff_reg);
		
		etsnam=(EditText)findViewById(R.id.editText_stfnam);
		etsmail=(EditText)findViewById(R.id.editText_stfmail);
		etscontact=(EditText)findViewById(R.id.editText_stfcontact);
		etsid=(EditText)findViewById(R.id.editText_stfid);
		etspas=(EditText)findViewById(R.id.editText_stfpassword);
		
		bstfrg=(Button)findViewById(R.id.bt_stfrgt);
		
		spsdep=(Spinner)findViewById(R.id.spinner_stfdeptst);
		
		 String[] dpt = getResources().getStringArray(R.array.dept);	
		 
	        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,dpt);
		    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spsdep.setAdapter(s1);
		    spsdep.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

		    	
				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					spdep=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });	
		    bstfrg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					stnam=etsnam.getText().toString();
					stmail=etsmail.getText().toString();
					stconatact=etscontact.getText().toString();
					stid=etsid.getText().toString();
					stpas=etspas.getText().toString();
					if(getValidate())
					{
					new staffreg().execute();
					}
					else
					{
						Toast.makeText(getApplicationContext(), "Error in details...Try again.", Toast.LENGTH_SHORT).show();
					}
				}
			});
	}
	
	public class staffreg extends AsyncTask<String, String, String>
    {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "staff_name=" + URLEncoder.encode(stnam, "UTF-8") + "&&"
	                        + "department=" + URLEncoder.encode(spdep, "UTF-8") + "&&"
	                        + "contact_no=" + URLEncoder.encode(stconatact, "UTF-8") + "&&"
	                        + "email=" + URLEncoder.encode(stmail, "UTF-8") + "&&"
	                        + "staff_userid=" + URLEncoder.encode(stid, "UTF-8") + "&&"
	                        + "staff_password=" + URLEncoder.encode(stpas, "UTF-8");
			}
			
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
    		sh=Connectivity.excutePost(Constants.STAFFREGISTER_URL,
                    urlParameters);
            Log.e("You are at", "" + sh);
			return sh;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			
			if(sh.contains("success"))
			{
				Toast.makeText(getApplicationContext(), "Successfully registered . . .", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), Main_page.class);
				startActivity(i);
				finish();
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "User id or Password error.", Toast.LENGTH_SHORT).show();							
			}
			super.onPostExecute(result);
		
		}
		
	}
	
	private boolean getValidate() {
		      
		if (stnam.length()==0)
        {
			etsnam.setError("Please enter Name");
            return false;
        }
        
        if (stconatact.length()!=10)
        {
            etscontact.setError("Invalid phone number");
            return false;
        }

        if(stmail.indexOf("@")==-1)
        {
        	etsmail.setError("Invalid emailId");
            return false;
        }
        if (stid.length()==0)
        {
            etsid.setError("Can't be Empty");
            return false;
        }
        if (stpas.length()==0)
        {
        	etspas.setError("Can't be Empty");
            return false;
        }
        return true;
    }

}
