
import android.content.Context;
import android.content.SharedPreferences;
import android.telecom.Call;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;




public class SahredPreferences {

    private static final String STORED_FEED = "_feed" ;
    private static final String FEED_USER = "feed_user" ;
    private static final String USER_NAME ="username" ;
    private static final String USER_IMAGE ="image_url" ;
    private static final String FIRTS_STATUS = "first_time" ;
    private static final String MINE_FEED = "mine_feed";
    private static final String BOOKMARKS = "bookmarkss";
    private static final String PHONE_UUID = "phone_imei";
    private static final String FIREBASE_TOKEN = "firebase_token";
    private SharedPreferences sharedPreferences;
    public static final String STORED_USER ="_user";
    public static final String FIRST_TIME = "first_time";
    private Context ctx;

    private static final String PREF_NAME = "sliceapp-welcome";

    private static final String IS_FIRST_TIME_LAUNCH = "IsFirstTimeLaunch";
    public SahredPreferences(Context ctx){
        this.ctx = ctx;
    }

    public void firstTime(){
        sharedPreferences =  ctx.getSharedPreferences(FIRST_TIME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putInt(FIRTS_STATUS, 1);
        editor.apply();
    }

    public void setFirstTimeLaunch(boolean isFirstTime) {
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();

        prefsEditor.putBoolean(IS_FIRST_TIME_LAUNCH, isFirstTime);
        prefsEditor.apply();
    }

    public boolean isFirstTimeLaunch() {
        sharedPreferences = ctx.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(IS_FIRST_TIME_LAUNCH, true);
    }

    public int checkFirstTime(){
        int a = 0;

        sharedPreferences = ctx.getSharedPreferences(FIRST_TIME, Context.MODE_PRIVATE);
        if (sharedPreferences != null && sharedPreferences.getInt(FIRTS_STATUS,0) != 0){
            a = 1;
        }
        return a;
    }





    public boolean checkPrefisEmpty(){

        boolean isEmpty = false;
        if (sharedPreferences.getString(STORED_USER,null) == null){
            isEmpty = true;
        }

        return isEmpty;
    }


}
