package com.herasiddiqui.sdsuclassregistration;

import android.content.Context;
import android.util.Log;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Created by admin on 3/30/18.
 */

public class VolleyQueue {
    private static final String TAG = "NetworkConnection";
    private static VolleyQueue instanceVC;
    private RequestQueue requestQueue;
    private static Context contextVC;

    private VolleyQueue(Context context) {
        Log.d(TAG,"Inside the Volley constructor");
        contextVC = context;
        requestQueue = getRequestQueue();
        //requestQueue = Volley.newRequestQueue(context.getApplicationContext());
    }

    public static synchronized VolleyQueue getInstance(Context context) {
        Log.d(TAG,"Inside the Volley getInstance");
        if (instanceVC == null) {
            instanceVC = new VolleyQueue(context);
        }
        return instanceVC;
    }

    public RequestQueue getRequestQueue(){
        Log.d(TAG,"Inside the Volley getting RequestQueue");
        if(requestQueue == null) {
            requestQueue = Volley.newRequestQueue(contextVC.getApplicationContext());
        }
        return requestQueue;
    }

    public<T> void addToRequestQueue(Request<T> request) {
        Log.d(TAG,"Inside the Volley,adding to request queue");
        getRequestQueue().add(request);
    }
}
