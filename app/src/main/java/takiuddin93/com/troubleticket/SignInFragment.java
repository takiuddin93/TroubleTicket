package takiuddin93.com.troubleticket;

import android.app.Activity;
import androidx.fragment.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignInFragment extends Fragment {

    EditText userPhone, userPassword;
    AutoCompleteTextView userName;
    ImageView arrowDropdown;
    Button signin;
    TextView newAccount;

    OnSignInFormActivityListener onSignInFormActivityListener;

    public interface OnSignInFormActivityListener {
        public void performSignUp();
        public void performAdminSignIn(String name, String phone);
        public void performVendorSignIn(String name, String phone);
        public void performCustomerSignIn(String name, String phone);

    }

    public SignInFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_in, container, false);

        userPhone = (EditText) view.findViewById(R.id.userPhone);
        userName = (AutoCompleteTextView) view.findViewById(R.id.userName);
        arrowDropdown = (ImageView) view.findViewById(R.id.arrowDropdown);
        userPassword = (EditText) view.findViewById(R.id.userPassword);
        signin = (Button) view.findViewById(R.id.signin);
        newAccount = (TextView) view.findViewById(R.id.newAccount);

        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performSignIn();
            }
        });

        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onSignInFormActivityListener.performSignUp();
            }
        });

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, usernames);
//        userName.setAdapter(adapter);
//
//        userName.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
//                inputMethodManager.hideSoftInputFromWindow(userName.getWindowToken(), 0);
//                userName.showDropDown();
//            }
//        });

        // Inflate the layout for this fragment
        return view;
    }

    public void onButtonPressed(Uri uri) {

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Activity activity = (Activity) context;
        onSignInFormActivityListener = (OnSignInFormActivityListener) activity;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private void performSignIn() {
//        final String username = userName.getText().toString();
        final String phone = userPhone.getText().toString();
        final String password = userPassword.getText().toString();

        if (phone.isEmpty() || password.isEmpty()) {
            Toast.makeText(getActivity(), "Please Fill Up The Fields", Toast.LENGTH_LONG).show();
        } else {

            Call<Users> call = MainActivity.api.performUserSignIn(phone, password);

            call.enqueue(new Callback<Users>() {
                @Override
                public void onResponse(Call<Users> call, Response<Users> response) {
                    switch (response.body().getResponse()) {
                        case "ok":
                            userPhone.setText("");
                            userPassword.setText("");
                            MainActivity.preferenceConfiguration.displayToast("Login Successful.");
                            MainActivity.preferenceConfiguration.writeUsername(response.body().getName());
                            MainActivity.preferenceConfiguration.writeRole(response.body().getRole());
                            MainActivity.preferenceConfiguration.writeLoginStatus(true);
                            MainActivity.preferenceConfiguration.writeUserphone(response.body().getPhone());
                            switch (response.body().getRole()) {
                                case "0":
                                    MainActivity.preferenceConfiguration.writeUserphone(response.body().getPhone());
                                    MainActivity.preferenceConfiguration.writePassword(response.body().getPassword());
                                    onSignInFormActivityListener.performAdminSignIn(response.body().getName(), response.body().getPhone());
                                    break;
                                case "1":
                                    onSignInFormActivityListener.performVendorSignIn(response.body().getName(), response.body().getPhone());
                                    break;
                                case "2":
                                    onSignInFormActivityListener.performCustomerSignIn(response.body().getName(), response.body().getPhone());
                                    break;
                            }
                            break;
                        case "failed":
                            MainActivity.preferenceConfiguration.displayToast("Login failed.");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<Users> call, Throwable t) {

                }
            });
        }
    }
}
