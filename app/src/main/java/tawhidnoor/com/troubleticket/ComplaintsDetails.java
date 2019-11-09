package tawhidnoor.com.troubleticket;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ComplaintsDetails extends AppCompatActivity {

    TextView subject, phone, message;
    EditText feedback;
    Button resolve;

    Typeface subjectFonts, phoneFonts, messageFonts, resolvedFonts;

//    OnVendorFeedbackListener onVendorFeedbackListener;
//
//    public interface OnVendorFeedbackListener {
//        public void vendorFeedback(String feedback);
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complaints_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setupActionBar();

        subject = (TextView) findViewById(R.id.subject);
        phone = (TextView) findViewById(R.id.phone);
        message = (TextView) findViewById(R.id.message);
        feedback = (EditText) findViewById(R.id.feedback);
        resolve = (Button) findViewById(R.id.resolve);
        resolve.setBackgroundColor(Color.parseColor("#ff0000"));

        subjectFonts = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-Bold.otf");
        phoneFonts = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-Light.otf");
        messageFonts = Typeface.createFromAsset(this.getAssets(), "fonts/Poppins-Regular.otf");

        Intent pos = getIntent();
        int position = pos.getExtras().getInt("itemPosition");

        String tempSubject = VendorFragment.mComplains.get(position).getSubject().toString();
        subject.setTypeface(subjectFonts);
        subject.setText(Html.fromHtml(tempSubject));
        String tempPhone = VendorFragment.mComplains.get(position).getPhone().toString();
        phone.setTypeface(phoneFonts);
        phone.setText(Html.fromHtml(tempPhone));
        String tempMessage = VendorFragment.mComplains.get(position).getMessage().toString();
        message.setTypeface(messageFonts);
        message.setText(Html.fromHtml(tempMessage));

        resolve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent pos = getIntent();

                String vendorID = pos.getExtras().getString("vendorId");
                String complainID = pos.getExtras().getString("complainId");
                String feedBack = feedback.getText().toString();
                String resolvedState = "1";

                Call<FeedbackPost> call = MainActivity.api.performFeedbackPost(vendorID, complainID, feedBack, resolvedState);
                call.enqueue(new Callback<FeedbackPost>() {
                    @Override
                    public void onResponse(Call<FeedbackPost> call, Response<FeedbackPost> response) {
                        switch (response.body().getResponse()) {
                            case "ok":
                                MainActivity.preferenceConfiguration.displayToast("Complain Resolved Successfull");
                                resolve.setBackgroundColor(Color.parseColor("#008000"));
                                resolve.setText("Resolved");
                                break;
                            case "error":
                                MainActivity.preferenceConfiguration.displayToast("Something Went Wrong.");
                                break;
                        }
                    }

                    @Override
                    public void onFailure(Call<FeedbackPost> call, Throwable t) {

                    }
                });
            }
        });

    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
//            actionBar.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

}
