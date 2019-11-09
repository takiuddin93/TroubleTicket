package takiuddin93.com.troubleticket;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API {

    // Customer complain post/store in database
    @GET("complainspost.php")
    Call<ComplainsPost> performComplainsPost(@Query("name") String Name, @Query("phone") String Phone, @Query("subject") String Subject, @Query("message") String Message);

    // Customer / Vendor signup store in database
    @GET("usersignup.php")
    Call<Users> performUserSignUp(@Query("name") String Name, @Query("phone") String Phone, @Query("email") String Email, @Query("role") String Role, @Query("password") String Password);

    // Customer / Vendor signsignup store in database
    @GET("usersignin.php")
    Call<Users> performUserSignIn(@Query("phone") String Phone, @Query("password") String Password);

    // Admin Token store in database
    @GET("admintoken.php")
    Call<Users> performAdminToken(@Query("phone") String Phone, @Query("password") String Password, @Query("token") String Token);

    // Vendor activity complains pull in json format for recycler
    @GET("complains.json")
    Call<List<Complaints>> getComplaints();

    // Vendor post feedback in database
    @GET("vendorfeedback.php")
    Call<FeedbackPost> performFeedbackPost(@Query("vendor_id") String VendorID, @Query("complain_id") String ComplainID, @Query("feedback") String Feedback, @Query("resolved_state") String ResolvedState);

    // Admin activity complains pull in json format for recycler
    @GET("unresolved.json")
    Call<List<Unresolved>> getUnresolved();

}
