package srt.inz.connectors;


public interface Constants {

    //Progress Message
    String LOGIN_MESSAGE="Logging in...";
    String REGISTER_MESSAGE="Register in...";

    //Urls
    String BASE_URL="https://avantzekiel.000webhostapp.com/Ams/";
        
    String LOGIN_URL=BASE_URL+"login.php?";   
    String STREGISTER_URL=BASE_URL+"studreg.php?";
    String STAFFREGISTER_URL=BASE_URL+"staffreg.php?";
    String GETDATE_URL=BASE_URL+"stget_date.php?";  
    String STUDENTDATE2_URL=BASE_URL+"student_det2.php?";
    String STUDENTATTENDANCE_URL=BASE_URL+"stattendance_ret.php?";    
    String MONTHLYATTENDANCE_URL=BASE_URL+"stattendance_retnew.php?";   
    String ATTENDANCEUPDATE_URL=BASE_URL+"attendance_update.php?";
    String STUDENTDET_URL=BASE_URL+"student_det.php?";
    
    String STAFFDET_URL=BASE_URL+"stafflist.php?";
    String STAFFAPPROVE_URL=BASE_URL+"staffapprove.php?";
    String STUDENTDETList_URL=BASE_URL+"studentlist.php?";
    String STUDENTAPPROVE_URL=BASE_URL+"studentapprove.php?";
    String MONTHLYATTENDANCESAVE_URL=BASE_URL+"savexcell_file.php?";
    String UPDATE_EXPDATE_URL=BASE_URL+"updatedate.php?";
    String VIEW_EXPDATE_URL=BASE_URL+"viewexpdate.php?";
	String STUDENTATTENDANCE_SINGLE_URL = BASE_URL+"attendance_update_single.php?";    
  
	// example 
    String EXAMPLE_URL=BASE_URL+"example.php?";
    

    //Details
    String SMSSERVICE="telSms";
    String ENUMBER="e_num";
    String ENUMBER1="e_num1";
    String ENUMBER2="e_num2";
    String LOGINSTATUS="LoginStatus";
    String MYLOCATON="MyLocation";
    String USERID="UserID";
    
    //SharedPreference
    String PREFERENCE_PARENT="Parent_Pref";


   
}
