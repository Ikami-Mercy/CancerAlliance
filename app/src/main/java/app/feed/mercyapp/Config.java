package app.feed.mercyapp;

/**
 * Created by Mercy.Ikami on 11/29/2017.
 */

public class Config {
    // public static String Url_Register = "http://197.232.17.86:81/BillMelida/Signup"; //Url for webservice here
    public static String Url_SaveDetails = "http://197.232.17.86:81/CancerAlliance/ViewData"; //Url for webservice here
    public static String Url_SaveFeed = "http://197.232.17.86:81/CancerAlliance/saveDetails";
    public static String Url_saveFeedback = "http://197.232.17.86:81/CancerAllianceUpdate/saveFeed"; //Url for webservice here
    //feedback
   /* public static String KEY_NAME = "UserName";
    public static String KEY_PHONE = "UserPhone";
    public static String KEY_EMAIL = "UserEmail";
    public static String KEY_PASSWORD = "PassWord";*/

    //save feed

    public static String KEY_Tittle = "Tittle";
    public static String KEY_Feed = "Feed";
    public static final String KEY_Date = "Date";   //response
    public static final String KEY_Image = "Image";

    //feedback

    public static String KEY_Name = "Name";
    public static String KEY_Email = "Email";
    public static final String KEY_Subject= "Subject";   //response
    public static final String KEY_Message = "Message";

    //event
    public static String KEY_eventTittle = "eventTittle";
    public static String KEY_eventImage = "eventImage";
    public static String KEY_eventVenue = "eventVenue";
    public static String KEY_eventTime = "eventTime";
    public static String KEY_eventDescription = "eventDescription";

    //login
    public static final String usernName= "usernName";   //response
    public static final String userPwd = "userPwd";
}
