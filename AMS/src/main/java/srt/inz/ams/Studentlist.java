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
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Studentlist extends Activity{
	
	Button button;
    ListView listView;
    ArrayAdapter<String> adapter;
    
    String hs,shdp,shsm,shpd,snam;	String[] mlist;
    
  //for mapping value from database
  		ArrayList<HashMap<String, String>> oslist = new ArrayList<HashMap<String, String>>();
  		List<String> lables = new ArrayList<String>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.studentlist);
		listView = (ListView) findViewById(R.id.list);
        button = (Button) findViewById(R.id.testbutton);
        
        SharedPreferences share=getSharedPreferences("shids", MODE_WORLD_READABLE);
        shdp =  share.getString("shdpt", "");
        shsm = share.getString("shsem", "");
        shpd = share.getString("shprd", "");
        
   //     mlist = getResources().getStringArray(R.array.sports_array);
       /* adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_multiple_choice, sports);*/
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        listView.setAdapter(adapter);    
        
        button.setOnClickListener(new OnClickListener() {
        	
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 SparseBooleanArray checked = listView.getCheckedItemPositions();
			        ArrayList<String> selectedItems = new ArrayList<String>();
			        for (int i = 0; i < checked.size(); i++) {
			            // Item position in adapter
			            int position = checked.keyAt(i);
			            // Add sport if it is checked i.e.) == TRUE!
			            if (checked.valueAt(i))
			                selectedItems.add(adapter.getItem(position));
			        }
			 
			        String[] outputStrArr = new String[selectedItems.size()];
			 
			        for (int i = 0; i < selectedItems.size(); i++) {
			            outputStrArr[i] = selectedItems.get(i);
			        }
			 
			        Intent intent = new Intent(getApplicationContext(),
			                ResultActivity.class);
			 
			        String[] outputStrArrall = new String[lables.size()];
			        for (int i = 0; i <lables.size(); i++) {
			        	outputStrArrall[i] = lables.get(i);
			        }
			        
			        // Create a bundle object
			        Bundle b = new Bundle();
			        b.putStringArray("selectedItems", outputStrArr);
			        b.putStringArray("all_item", outputStrArrall);
			 
			        // Add the bundle to the intent.
			        intent.putExtras(b);
			 
			        // start the ResultActivity
			        startActivity(intent);
			}
		});
		
        new getstudent_det().execute();
	}
	
	public class getstudent_det extends AsyncTask<String, String, String>
	{

	
		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String urlParameters=null;
			try
			{
				urlParameters =  "Department=" + URLEncoder.encode(shdp, "UTF-8") + "&&"
                        + "Semester=" + URLEncoder.encode(shsm, "UTF-8");
				
			}
			catch(Exception e)
			{
				System.out.println("Error:"+e);
			}
			hs=Connectivity.excutePost(Constants.STUDENTDET_URL, urlParameters);
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
			
			for(int i=0;i<length;i++)
			{
				JSONObject data1=ja.getJSONObject(i);
				snam=data1.getString("Stud_Name");
				
				/*semail=data1.getString("cargoagency_email");
				sphone=data1.getString("cargoagency_phone");
				sinsure=data1.getString("insurance");*/
				
				// Adding value HashMap key => value
	            HashMap<String, String> map = new HashMap<String, String>();
	            map.put("Stud_Name", snam);
	            oslist.add(map);
	            
	            lables.add(oslist.get(i).get("Stud_Name"));
	            
	            adapter = new ArrayAdapter<String>(this,
	                    android.R.layout.simple_list_item_multiple_choice, lables);	
	            
	            listView.setAdapter(adapter);
	        /*    SharedPreferences shr=getSharedPreferences("all_items_key", MODE_WORLD_READABLE);
	            SharedPreferences.Editor ed=shr.edit();
	            ed.putString("all_items", oslist)*/
	            
	            
	            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
		               @Override
		               public void onItemClick(AdapterView<?> parent, View view,
		                                            int position, long id) {  
		            	String value=oslist.get(+position).get("Stud_Name");
		            	   
		            		   Toast.makeText(getApplicationContext(), ""+value, Toast.LENGTH_SHORT).show();
				       
		              
		              /* value=oslist.get(+position).get("cargoagency_name");
		               SharedPreferences shar=getSharedPreferences("key_cgnam", MODE_WORLD_READABLE);
		                 SharedPreferences.Editor ed=shar.edit();
		                 ed.putString("e_nam", value);
		                 ed.putString("e_mode", s_mode);
		                 ed.commit();  */
		            	   
		               }
		                });
			}
			
			
		}
		catch(Exception e)
		{
			System.out.println("error:"+e);
		}
	}

}
