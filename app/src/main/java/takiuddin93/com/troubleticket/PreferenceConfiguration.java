package takiuddin93.com.troubleticket;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class PreferenceConfiguration {

    private SharedPreferences sharedPreferences;
    private Context context;

    public PreferenceConfiguration(Context context) {
        this.context = context;
        sharedPreferences = context.getSharedPreferences(context.getString(R.string.preferenceFile), Context.MODE_PRIVATE);
    }

    public void writeLoginStatus(boolean status) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(context.getString(R.string.preferenceLoginStatus), status);
        editor.commit();
    }

    public boolean readLoginStatus() {
        return sharedPreferences.getBoolean(context.getString(R.string.preferenceLoginStatus), false);
    }

    public void writeRole(String role) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferenceRole), role);
        editor.commit();
    }

    public String readRole() {
        return sharedPreferences.getString(context.getString(R.string.preferenceRole), "");
    }

    public void writeUsername(String name) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferenceUsername), name);
        editor.commit();
    }

    public String readUsername() {
        return sharedPreferences.getString(context.getString(R.string.preferenceUsername), "");
    }

    public void writeUserphone(String phone) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferencePhone), phone);
        editor.commit();
    }

    public String readUserphone() {
        return sharedPreferences.getString(context.getString(R.string.preferencePhone), "");
    }

    public void writePassword(String password) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferencePhone), password);
        editor.commit();
    }

    public String readPassword() {
        return sharedPreferences.getString(context.getString(R.string.preferencePhone), "");
    }

    public void writeUserid(String id) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferenceId), id);
        editor.commit();
    }

    public String readUserid() {
        return sharedPreferences.getString(context.getString(R.string.preferenceId), "");
    }

    public void writeAdminToken(String token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(context.getString(R.string.preferenceAdminToken), token);
        editor.commit();
    }

    public String readAdminToken() {
        return sharedPreferences.getString(context.getString(R.string.preferenceAdminToken), "");
    }

    public void displayToast(String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
