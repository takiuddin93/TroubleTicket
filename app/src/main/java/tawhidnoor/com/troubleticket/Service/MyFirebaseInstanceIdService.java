package tawhidnoor.com.troubleticket.Service;

import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

import tawhidnoor.com.troubleticket.MainActivity;

public class MyFirebaseInstanceIdService extends FirebaseInstanceIdService {
    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();
        Log.d("FirebaseIDService", "Refreshed token: " + refreshedToken);
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // Instance ID token to your app server.
        sendNewTokenToServer(refreshedToken);
    }

    private void sendNewTokenToServer(String refreshedToken) {
        MainActivity.preferenceConfiguration.writeAdminToken(refreshedToken);
        Log.d("FirebaseIDService", String.valueOf(refreshedToken));
    }
}
