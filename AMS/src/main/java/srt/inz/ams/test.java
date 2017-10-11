package srt.inz.ams;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class test extends Activity {
	
	TextView tv_date;	String s_date;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.result);
        tv_date=(TextView)findViewById(R.id.textView_date);
        
      //  DateFormat df = new SimpleDateFormat("yyyy/MM/dd hh:mm:ss");
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
		Calendar calobj = Calendar.getInstance();
		s_date=df.format(calobj.getTime());
        tv_date.setText("Date : "+s_date);
		
        Bundle b = getIntent().getExtras();
        String[] resultArr = b.getStringArray("selectedItems");
        ListView lv = (ListView) findViewById(R.id.outputList);
 
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, resultArr);
        lv.setAdapter(adapter);
        
    }
}
