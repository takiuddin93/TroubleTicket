package takiuddin93.com.troubleticket;

import androidx.appcompat.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements SignInFragment.OnSignInFormActivityListener, SignUpFragment.OnSignUpFormActivityListener {

    public static PreferenceConfiguration preferenceConfiguration;
    public static API api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        preferenceConfiguration = new PreferenceConfiguration(MainActivity.this);
        api = APIClient.getAPIClient().create(API.class);

        Log.d("MainStateActivity", preferenceConfiguration.readRole() + " " + preferenceConfiguration.readLoginStatus());

        if (findViewById(R.id.frameLayout) != null) {
            if (savedInstanceState != null){
                return;
            }
            if (preferenceConfiguration.readLoginStatus()) {

                if (preferenceConfiguration.readRole().equals("0")) {

                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new AdminFragment()).commit();
                    Log.d("MainStateActivity", preferenceConfiguration.readUserphone());

                } else if(preferenceConfiguration.readRole().equals("1")) {

                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new VendorFragment()).commit();
                    Log.d("MainStateActivity", preferenceConfiguration.readUserphone());

                } else {

                    getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new ComplainFragment()).commit();
                    Log.d("MainStateActivity", preferenceConfiguration.readUserphone());

                }
            } else {

                getSupportFragmentManager().beginTransaction().add(R.id.frameLayout, new SignInFragment()).commit();

            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //if not implemented
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {

            if (preferenceConfiguration.readLoginStatus()) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
                alertDialogBuilder.setMessage("Logout?");
                alertDialogBuilder.setPositiveButton("Logout", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        preferenceConfiguration.writeLoginStatus(false);
                        preferenceConfiguration.writeRole("");
                        preferenceConfiguration.writeUsername("");
                        preferenceConfiguration.writeUserphone("");
                        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignInFragment()).commit();
                    }
                });
                alertDialogBuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialogBuilder.create().show();

            } else {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setMessage("You are not Logged in yet!");

                alertDialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });

                alertDialogBuilder.create().show();
            }

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void performSignUp() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignUpFragment()).addToBackStack(null).commit();
    }

    @Override
    public void performSignIn() {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignInFragment()).addToBackStack(null).commit();
    }

//    @Override
//    public void performAdminSignUp() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignUpFragment()).addToBackStack(null).commit();
//    }

    @Override
    public void performAdminSignIn(String name, String phone) {

        preferenceConfiguration.writeUsername(name);
        preferenceConfiguration.writeUserphone(phone);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new AdminFragment()).commit();
    }

//    @Override
//    public void performVendorSignUp() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignUpFragment()).addToBackStack(null).commit();
//    }

    @Override
    public void performVendorSignIn(String name, String phone) {

        preferenceConfiguration.writeUsername(name);
        preferenceConfiguration.writeUserphone(phone);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new VendorFragment()).commit();
    }

//    @Override
//    public void performCustomerSignUp() {
//        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new SignUpFragment()).addToBackStack(null).commit();
//    }

    @Override
    public void performCustomerSignIn(String name, String phone) {
        preferenceConfiguration.writeUsername(name);
        preferenceConfiguration.writeUserphone(phone);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, new ComplainFragment()).commit();
    }
}
