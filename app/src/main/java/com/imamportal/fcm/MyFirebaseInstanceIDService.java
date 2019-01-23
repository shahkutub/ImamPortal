package com.imamportal.fcm;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.imamportal.utils.AppConstant;
import com.imamportal.utils.PersistData;


/**
 * Created by NanoSoft on 7/24/2017.
 */

public class MyFirebaseInstanceIDService extends FirebaseInstanceIdService {

    Context con;
    @Override
    public void onTokenRefresh() {
        con = this;
        // Get updated InstanceID token.
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

        Log.e("", "Refreshed token: " + refreshedToken);

        if (refreshedToken!=null) {
            PersistData.setStringData(con, AppConstant.fcm_token,refreshedToken);
                    }
        final Intent intent = new Intent("tokenReceiver");
        // You can also include some extra data.
        final LocalBroadcastManager broadcastManager = LocalBroadcastManager.getInstance(this);
        intent.putExtra("token",refreshedToken);
        broadcastManager.sendBroadcast(intent);

        sendRegistrationToServer(refreshedToken);
    }

    private void sendRegistrationToServer(String refreshedToken) {
    }

}
