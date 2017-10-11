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

public class Stud_reg extends Activity{
	
	EditText etsnam,etsroll,etsparent,etsaddr,etsmail,etscontact,etsyear,etspass;
	Spinner spsdep,spssem; String spdep,spsem;	ArrayAdapter<String> s1,s2;
	
	String snam,sroll,sparent,saddr,semail,scontact,syear,spass,sh;
	
	Button bstdrg;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.stud_reg);
		
		etsnam=(EditText)findViewById(R.id.editText_stnam);
		etsroll=(EditText)findViewById(R.id.editText_rollno);
		etsparent=(EditText)findViewById(R.id.editText_parentname);
		etsaddr=(EditText)findViewById(R.id.editText_address);
		etsmail=(EditText)findViewById(R.id.editText_email);
		etscontact=(EditText)findViewById(R.id.editText_phone);
		etsyear=(EditText)findViewById(R.id.editText_year);
		etspass=(EditText)findViewById(R.id.editText_pswrd);
		bstdrg=(Button)findViewById(R.id.bt_streg);
		
		spsdep=(Spinner)findViewById(R.id.spinner_deptst);
		spssem=(Spinner)findViewById(R.id.spinner_semst);
		
		
		 String[] dpt = getResources().getStringArray(R.array.dept);
		 String[] sem = getResources().getStringArray(R.array.semester);
		
		 
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
		    s2=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,sem);
		    s2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		    spssem.setAdapter(s2);
		    spssem.setOnItemSelectedListener(new OnItemSelectedListener()
	        {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					spsem=arg0.getItemAtPosition(arg2).toString();
					((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub	
				}
	        	
	        });
		    
		    bstdrg.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					sroll=etsroll.getText().toString();
					snam=etsnam.getText().toString();
					syear=etsyear.getText().toString();
					sparent=etsparent.getText().toString();
					saddr=etsaddr.getText().toString();
					scontact=etscontact.getText().toString();
					semail=etsmail.getText().toString();
					spass=etspass.getText().toString();
					if(getValidate())				
						{
						new studentreg().execute();
						}
					else
					{
						Toast.makeText(getApplicationContext(), "Error in details...Try again.", Toast.LENGTH_SHORT).show();
					}
				}
			});
		    		
	}
	
	public class studentreg extends AsyncTask<String, String, String>
    {

		@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
			String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "Roll_No=" + URLEncoder.encode(sroll, "UTF-8") + "&&"
	                        + "Stud_Name=" + URLEncoder.encode(snam, "UTF-8") + "&&"
	                        + "Department=" + URLEncoder.encode(spdep, "UTF-8") + "&&"
	                        + "Semester=" + URLEncoder.encode(spsem, "UTF-8") + "&&"
	                        + "Year=" + URLEncoder.encode(syear, "UTF-8") + "&&"
	                        + "Department=" + URLEncoder.encode(spdep, "UTF-8") + "&&"
	                        + "Parent_Name=" + URLEncoder.encode(sparent, "UTF-8") + "&&"
	                        + "Address=" + URLEncoder.encode(saddr, "UTF-8") + "&&"
	                        + "Contact_Number=" + URLEncoder.encode(scontact, "UTF-8") + "&&"
	                        + "Email=" + URLEncoder.encode(semail, "UTF-8") + "&&"
	                        + "Stud_Pass=" + URLEncoder.encode(spass, "UTF-8");
			}
			
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
    		sh=Connectivity.excutePost(Constants.STREGISTER_URL,
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
	      
			if (sroll.length()==0)
	        {
	            etsroll.setError("Please enter Roll number");
	            return false;
	        }
	        if (snam.length()==0)
	        {
	            etsnam.setError("Can't be Empty");
	            return false;
	        }
	        if (syear.length()==4)
	        {
	            etsyear.setError("Please enter valid year");
	            return false;
	        }
	        if (sparent.length()==0)
	        {
	            etsparent.setError("Can't be Empty");
	            return false;
	        }
	        if (saddr.length()==0)
	        {
	            etsaddr.setError("Please enter address");
	            return false;
	        }
	        if (scontact.length()!=10)
	        {
	            etscontact.setError("Invalid phone number");
	            return false;
	        }

	        if(semail.indexOf("@")==-1)
	        {
	        	etsmail.setError("Invalid emailId");
	            return false;
	        }
	        if (spass.length()==0)
	        {
	            etspass.setError("Can't be Empty");
	            return false;
	        }
	        return true;
	    }

}
