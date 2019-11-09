package takiuddin93.com.troubleticket;

import androidx.fragment.app.Fragment;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
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


public class ComplainFragment extends Fragment {

    TextView createComplaint;
    EditText fullName;
    AutoCompleteTextView userSubject;
    ImageView arrowDropdown;
    EditText userPhone;
    EditText userMessage;
    Button complainSubmit;

    public static PreferenceConfiguration preferenceConfiguration;

    public ComplainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_complain, container, false);


        preferenceConfiguration = new PreferenceConfiguration(getActivity());

        createComplaint = (TextView) view.findViewById(R.id.createComplaint);
        fullName = (EditText) view.findViewById(R.id.fullName);
        userSubject = (AutoCompleteTextView) view.findViewById(R.id.userSubject);
        arrowDropdown = (ImageView) view.findViewById(R.id.arrowDropdown);
        userPhone = (EditText) view.findViewById(R.id.userPhone);
        userMessage = (EditText) view.findViewById(R.id.userMessage);
        complainSubmit = (Button) view.findViewById(R.id.complainSubmit);
        userSubject.setThreshold(1);

        complainSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                performComplainsPost();
            }
        });

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.support_simple_spinner_dropdown_item, subjects);
        userSubject.setAdapter(adapter);

        userSubject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputMethodManager.hideSoftInputFromWindow(userSubject.getWindowToken(), 0);
                userSubject.showDropDown();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    private void performComplainsPost() {
        final String name = preferenceConfiguration.readUsername();
        final String phone = preferenceConfiguration.readUserphone();
        final String subject = userSubject.getText().toString();
        final String message = userMessage.getText().toString();

        if (phone.isEmpty() || subject.isEmpty() || message.isEmpty()) {
            Toast.makeText(getActivity(), "Please Fill Up The Fields", Toast.LENGTH_LONG).show();
        } else {
            Call<ComplainsPost> complainsPostCall = MainActivity.api.performComplainsPost(name, phone, subject, message);

            complainsPostCall.enqueue(new Callback<ComplainsPost>() {
                @Override
                public void onResponse(Call<ComplainsPost> complainsPostCall, Response<ComplainsPost> response) {
                    Log.d("ComplaintsPost", "onResponse: " + response.body().getResponse());
                    switch (response.body().getResponse()) {
                        case "ok":
                            userSubject.setText("");
                            userMessage.setText("");
                            MainActivity.preferenceConfiguration.displayToast("Complain Has Been Posted");
                            break;
                        case "error":
                            MainActivity.preferenceConfiguration.displayToast("Complain Cannot Be Posted");
                            break;
                    }
                }

                @Override
                public void onFailure(Call<ComplainsPost> ComplaintsPost, Throwable t) {

                }
            });
        }
    }

    private static final String[] subjects = new String[]{"Facebook", "Youtube", "Slow Internet", "No Internet", "Packet Loss", "Wire Replace" , "Others"};

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

}
