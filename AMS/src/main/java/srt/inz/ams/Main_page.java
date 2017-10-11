package srt.inz.ams;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import srt.inz.connectors.Connectivity;
import srt.inz.connectors.Constants;


import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressWarnings("deprecation")
public class Main_page extends Activity {
	
	//for spinner
		Spinner rq_styp;
		ArrayAdapter<String> s1; String styp;
		
		EditText eusr,eps;
		String sh,susr,sps; Button blg,brg; String srgtyp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_page);
        rq_styp=(Spinner)findViewById(R.id.spinner_typ);
        eusr=(EditText)findViewById(R.id.et_uname);
        eps=(EditText)findViewById(R.id.et_password);
        blg=(Button)findViewById(R.id.bt_login);
        brg=(Button)findViewById(R.id.bt_register);
        
        blg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				susr=eusr.getText().toString();
				sps=eps.getText().toString();
				
				SharedPreferences sh=getSharedPreferences("keysun", MODE_WORLD_READABLE);
				SharedPreferences.Editor ed=sh.edit();
				ed.putString("srtsun", susr);
				ed.commit();
				if(getValidate())
				{
				new loginn().execute();

				}
				
				}
		});
        
        brg.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				openDialog();
			}
		});
        
        String[] typ = getResources().getStringArray(R.array.usertype);
        
        s1=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item,typ);
	    s1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	    rq_styp.setAdapter(s1);
	    rq_styp.setOnItemSelectedListener(new OnItemSelectedListener()
        {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				styp=arg0.getItemAtPosition(arg2).toString();
				((TextView) arg0.getChildAt(0)).setTextColor(Color.BLUE);
				
				if(styp.equals("Teacher"))
				{
					eusr.setHint("Staff Id");
				}
				else if(styp.equals("Student"))
				{
					eusr.setHint("Roll Number");
				}
				else
				{
					eusr.setHint("Admin Id");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub	
			}
        	
        });
	    	    
        
    }
    public void openDialog(){
	      AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
	      alertDialogBuilder.setMessage("Select mode of registration");
	      
	      alertDialogBuilder.setPositiveButton("Student", new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface arg0, int arg1) {
	            
	            srgtyp="Student";
	            Toast.makeText(getApplicationContext(),srgtyp,Toast.LENGTH_SHORT).show();
	            Intent i=new Intent(getApplicationContext(),Stud_reg.class);
				startActivity(i);
				finish();
	            
	         }
	      });
	      
	      alertDialogBuilder.setNegativeButton("Staff",new DialogInterface.OnClickListener() {
	         @Override
	         public void onClick(DialogInterface dialog, int which) {
	        	 srgtyp="Teacher";
	        	 Toast.makeText(getApplicationContext(),srgtyp,Toast.LENGTH_SHORT).show();
	        	 Intent i=new Intent(getApplicationContext(),Staff_reg.class);
					startActivity(i);
					finish();
	        	 
	         }
	      });
	      
	      
	      AlertDialog alertDialog = alertDialogBuilder.create();
	      alertDialog.show();
	   }
    public class loginn extends AsyncTask<String, String, String>
    {

    	@Override
		protected String doInBackground(String... arg0) {
			// TODO Auto-generated method stub
    		String urlParameters = null;
    		try
			{	
				
    			 urlParameters =  "user_name=" + URLEncoder.encode(susr, "UTF-8") + "&&"
	                        + "password=" + URLEncoder.encode(sps, "UTF-8") + "&&"
	                        + "user_type=" + URLEncoder.encode(styp, "UTF-8");
    			 
			}
			
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			
    		sh=Connectivity.excutePost(Constants.LOGIN_URL,
                    urlParameters);
            Log.e("You are at", "" + sh);
            
			return sh;
		}
		
		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if(sh.contains("success"))
			{
				if(styp.contains("Teacher"))
				{
				Toast.makeText(getApplicationContext(), "Successfully Logged in.", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), TA_Main.class);
				startActivity(i);
				}
				if(styp.contains("Student"))
				{
				Toast.makeText(getApplicationContext(), "Successfully Logged in.", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), ST_Home.class);
				startActivity(i);
				}
				if(styp.contains("Admin"))
				{
				Toast.makeText(getApplicationContext(), "Successfully Logged in.", Toast.LENGTH_SHORT).show();
				Intent i=new Intent(getApplicationContext(), Adminclg.class);
				startActivity(i);
				}
				
				eusr.setText(""); eps.setText("");
				
			}
			else
			{
				Toast.makeText(getApplicationContext(), "Username or Password error.", Toast.LENGTH_SHORT).show();							
			}
			
		
		}
		
	}
    
    private boolean getValidate() {
	      
		
		if (susr.length()==0)
        {
            eusr.setError("Please enter Username");
            return false;
        }
        if (sps.length()==0)
        {
            eps.setError("Can't be Empty");
            return false;
        }
       

        return true;
    }

    
}
