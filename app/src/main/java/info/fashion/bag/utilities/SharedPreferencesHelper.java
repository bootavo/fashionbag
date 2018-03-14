package info.fashion.bag.utilities;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by bootavo on 6/09/2017.
 */

public class SharedPreferencesHelper {

    private String PREFS_NAME = "midoc";
    private SharedPreferences mSharedPreferences;
    private Context ctx;
    //private String PREFS_KEY = "key";

    public SharedPreferencesHelper(Context ctx) {
        this.ctx = ctx;
    }

    public void saveString(String key, String value){
        mSharedPreferences =ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public String getValueString(String key){
        mSharedPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String value = mSharedPreferences.getString(key, null);
        return value;
    }

    public void saveInt(String key, int value){
        mSharedPreferences =ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public int getValueInt(String key){
        mSharedPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        int value = mSharedPreferences.getInt(key, 0);
        return value;
    }

    public void saveBoolean(String key, boolean value){
        mSharedPreferences =ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key){
        mSharedPreferences = ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        boolean value = mSharedPreferences.getBoolean(key, false);
        return value;
    }

    public void clearSharedPreference() {
        mSharedPreferences =ctx.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = mSharedPreferences.edit();
        editor.clear();
        editor.commit();
        //editor.apply();
    }

    public int getId(){
        if(getValueInt("id") == 0){
            return 0;
        }else{
            return getValueInt("id");
        }
    }

    public String getToken(){
        if(getValueString("token") == null || getValueString("token").equals("")){
            return "No definido";
        }else{
            return Constant.TOKEN_FORMAT+getValueString("token");
        }
    }

    public String getSataus(){
        if(getValueString("status") == null || getValueString("status").equals("")){
            return "";
        }else{
            return getValueString("status");
        }
    }

    public String getAddress(){
        if(getValueString("address") == null || getValueString("address").equals("")){
            return "No definido";
        }else{
            return getValueString("address");
        }
    }

    public String getBirthDate(){
        if(getValueString("birth_date") == null || getValueString("birth_date").equals("")){
            return "No definido";
        }else{
            return getValueString("birth_date");
        }
    }

    public String getDni(){
        if(getValueString("dni") == null || getValueString("dni").equals("")){
            return "No definido";
        }else{
            return getValueString("dni");
        }
    }

    public int getReservationDays(){
        if(getValueInt("reservation_days") == 0){
            return 0;
        }else{
            return getValueInt("reservation_days");
        }
    }

    public String getFirstname(){
        if(getValueString("first_name") == null || getValueString("first_name").equals("")){
            return "No definido";
        }else{
            return getValueString("first_name");
        }
    }

    public String getLastName(){
        if(getValueString("last_name") == null || getValueString("last_name").equals("")){
            return "No definido";
        }else{
            return getValueString("last_name");
        }
    }

    public String getFullName(){

        String full_name= "";

        if(getValueString("first_name") != null || !getValueString("first_name").equals("")){
            full_name = getValueString("first_name");
            if(getValueString("last_name") != null || !getValueString("last_name").equals("")){
                full_name = getValueString("first_name")+" "+getValueString("last_name");
            }else{
                full_name = getValueString("first_name");
            }
        }else{
            if(getValueString("last_name") != null || !getValueString("last_name").equals("")){
                full_name = getValueString("last_name");
            }else{
                full_name = "No definido";
            }
        }

        return full_name;
    }

    public String getEmail(){
        if(getValueString("email") == null || getValueString("email").equals("")){
            return "No definido";
        }else{
            return getValueString("email");
        }
    }

    public String getGender(){
        String gender = "";
        if(getValueString("gender") == null || getValueString("gender").equals("")){
            gender = "No definido";
        }else{
            if(getValueString("gender").equals("m")){
                gender = "Hombre";
            }else if(getValueString("gender").equals("f")){
                gender = "Mujer";
            }
        }
        return gender;
    }

    public String getProfilePicture(){
        String photo = "";
        if(getValueString("profile_picture") == null || getValueString("profile_picture").equals("")){
            photo = "No Definido";
        }else{
            photo = getValueString("profile_picture");
        }
        return photo;
    }

    public String getPhoneNumber(){
        if(getValueString("phone_number") == null || getValueString("phone_number").equals("")){
            return "No Definido";
        }else{
            return getValueString("phone_number");
        }
    }

    public String getMobilePhone(){
        if(getValueString("mobile_phone") == null || getValueString("mobile_phone").equals("")){
            return "No Definido";
        }else{
            return getValueString("mobile_phone");
        }
    }

    public int getMinMountJewels(){
        if(getValueInt("min_mount_jewels") == 0){
            return 0;
        }else{
            return getValueInt("min_mount_jewels");
        }
    }

    public int getMinSalesUnits(){
        if(getValueInt("min_sales_units") == 0){
            return 0;
        }else{
            return getValueInt("min_sales_units");
        }
    }

    public int getMinShoesUnits(){
        if(getValueInt("min_shoes_units") == 0){
            return 0;
        }else{
            return getValueInt("min_shoes_units");
        }
    }

    public void showAllSPData(){
        JSONObject json = new JSONObject();
        try {
            json.put("id", getValueInt("id"));
            json.put("token", getValueString("token"));
            json.put("status", getValueString("status"));
            json.put("address", getValueString("address"));
            json.put("birth_date", getValueString("birth_date"));
            json.put("reservation_days", getValueInt("reservation_days"));
            json.put("first_name", getValueString("first_name"));
            json.put("last_name", getValueString("last_name"));
            json.put("email", getValueString("email"));
            json.put("gender", getValueString("gender"));
            json.put("profile_picture", getValueString("profile_picture"));
            json.put("phone_number", getValueString("phone_number"));
            json.put("mobile_phone", getValueString("mobile_phone"));
            json.put("min_mount_jewels", getValueInt("min_mount_jewels"));
            json.put("min_sales_units", getValueInt("min_sales_units"));
            json.put("min_shoes_units", getValueInt("min_shoes_units"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Log.d("Shared Preferences: ", ""+JsonPretty.getPrettyJson(json));
    }

}
