package takiuddin93.com.troubleticket;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {

    EditText fullName, userPhone, userEmail, userPassword, confirmPassword;
    AutoCompleteTextView userName;
    ImageView arrowDropdown;
    Button signup;
    TextView alreadyRegistered;

    OnSignUpFormActivityListener onSignUpFormActivityListener;

    public interface OnSignUpFormActivityListener {
        public void performSignIn();

    }

    public SignUpFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);

        fullName = (EditText) view.findViewById(R.id.fullName);
        userPhone = (EditText) view.findViewById(R.id.userPhone);
        userEmail = (EditText) view.findViewById(R.id.userEmail);
        userName = (AutoCompleteTextView) view.findViewById(R.id.userName);
        arrowDropdown = (ImageView) view.findViewById(R.id.arrowDropdown);
        userPassword = (EditText) view.findViewById(R.id.userPassword);
        signup = (Button) view.findViewById(R.id.signup);
        alreadyRegistered = (TextView) view.findViewById(R.id.alreadyRegistered);

        userName.setThreshold(1);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performRegistration();
            }
        });

        alreadyRegistered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignUpFormActivityListener.performSignIn();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, usernames);
        userName.setAdapter(adapter);

        userName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(userName.getWindowToken(), 0);
                userName.showDropDown();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void performRegistration() {
        String name = fullName.getText().toString();
        String phone = userPhone.getText().toString();
        String email = userEmail.getText().toString();
        String username = userName.getText().toString();
        String password = userPassword.getText().toString();

        if (name.isEmpty() || phone.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please Fill Up The Fields", Toast.LENGTH_LONG).show();
        } else {
            Call<Users> call = MainActivity.api.performUserSignUp(name, phone, email, username, password);

            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    switch (response.body().getResponse()) {
                        case "ok":
                            fullName.setText("");
                            userPhone.setText("");
                            userEmail.setText("");
                            userName.setText("");
                            userPassword.setText("");
                            MainActivity.preferenceConfiguration.displayToast("Registration Successful.");
                            break;
                        case "exists":
                            MainActivity.preferenceConfiguration.displayToast("User Already Exists.");
                            break;
                        case "error":
                            MainActivity.preferenceConfiguration.displayToast("Something Went Wrong.");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }

    private static final String[] usernames = new String[] {"Customer", "Vendor"};

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onSignUpFormActivityListener = (SignUpFragment.OnSignUpFormActivityListener) activity;

    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

}
